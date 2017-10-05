package com.dheeraj.user.registration.helper;

import com.dheeraj.user.registration.constants.Constants;
import com.dheeraj.user.registration.enums.UserStatus;
import com.dheeraj.user.registration.model.pojo.AddressChunk;
import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.util.DateUtil;
import com.google.maps.model.LatLng;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by dheeraj on 03/10/17.
 */

@Component
public class LocationHelper {


    private HashMap<String, LatLng> ADDRESS_LAT_LNG_MAP = new HashMap<>();

    public List<AddressChunk> getLocationInChunks(List<Location> locationList) {

        List<AddressChunk> addressChunkList = new ArrayList<>();
        Map<String, Integer> addressCountMap = new HashMap<>();
        TreeMap<Integer, String> countAddressMap = new TreeMap<>();
        Integer count = 0;
        DateTime prevDateTime = DateUtil.convertToJodaTime(locationList.get(0).getLocationtime());
        DateTime nextDateTime = prevDateTime.plusSeconds(Constants.CHUNK_SECONDS);

        for (int i = 1; i < locationList.size(); i++) {
            Location location = locationList.get(i);
            String address = location.getAddress();

            DateTime currTime = DateUtil.convertToJodaTime(location.getLocationtime());


            if (currTime.isAfter(nextDateTime)) {

                AddressChunk addressChunk = new AddressChunk();

                addressChunk.setStartTime(DateUtil.convertToString(prevDateTime));
                addressChunk.setEndTime(DateUtil.convertToString(currTime));

                if (CollectionUtils.isEmpty(addressCountMap)) {
                    addressChunk.setUserStatus(UserStatus.UNKNOWN);
                } else {

                    for (Map.Entry<String, Integer> entry : addressCountMap.entrySet()) {
                        countAddressMap.put(entry.getValue(), entry.getKey());
                    }

                    if (countAddressMap.size() > Constants.MAX_LIMIT_CHUNK_ENTRIES) {

                        addressChunk.setUserStatus(UserStatus.MOVING);

                    } else {

                        ArrayList<String> probableAddresses = getProbableAddress(countAddressMap, count);
                        boolean allPointsNear = checkIfAllPointsNear(probableAddresses);
                        if (allPointsNear) {
                            addressChunk.setUserStatus(UserStatus.STATIC);
                            addressChunk.setAddress(countAddressMap.firstEntry().getValue());
                        } else {
                            addressChunk.setUserStatus(UserStatus.MOVING);
                        }

                    }
                }


                addressChunkList.add(addressChunk);


                /**
                 * get all those address for which the count in more than
                 * limit.generally it is more than 80%
                 */


                prevDateTime = currTime;
                nextDateTime = prevDateTime.plusSeconds(Constants.CHUNK_SECONDS);
            } else if (address != null) {
                if (!addressCountMap.containsKey(address)) {
                    addressCountMap.put(address, 0);
                }
                if (!ADDRESS_LAT_LNG_MAP.containsKey(address)) {
                    ADDRESS_LAT_LNG_MAP.put(address, new LatLng(Double.parseDouble(location.getLatitude()),
                            Double.parseDouble(location.getLongitude())));
                }
                count++;
                addressCountMap.put(address, addressCountMap.get(location.getAddress()) + 1);
            }

        }

        return addressChunkList;
    }

    private ArrayList<String> getProbableAddress(TreeMap<Integer, String> countAddressMap, Integer count) {
        int currCount = 0;
        ArrayList<String> addressList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : countAddressMap.entrySet()) {
            currCount += entry.getKey();
            if (((currCount * 100) / count) < Constants.PERCENTAGE_LIMIT) {
                addressList.add(entry.getValue());
            }
        }

        return addressList;
    }

    private boolean checkIfAllPointsNear(ArrayList<String> values) {

        for (int i = 0; i < values.size(); i++) {
            for (int j = i + 1; j < values.size(); j++) {

                LatLng fromLatLng = ADDRESS_LAT_LNG_MAP.get(values.get(i));
                LatLng toLatLng = ADDRESS_LAT_LNG_MAP.get(values.get(j));
                float distance = distFrom(fromLatLng.lat, fromLatLng.lng, toLatLng.lat, toLatLng.lng);
                if (distance > Constants.MAX_CHUNK_DISTANCE_LIMIT_IN_METERS) {
                    return false;
                }
            }
        }

        return true;
    }

    public float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public List<AddressChunk> mergeCommonAddressChunks(List<AddressChunk> addressChunkList) {

        List<AddressChunk> addressChunkMerged = new ArrayList<>();
        AddressChunk previousAddressChunk = null;
        for (AddressChunk currAddressChunk : addressChunkList) {

            if (previousAddressChunk == null) {
                previousAddressChunk = currAddressChunk;
                continue;
            }

            if (!previousAddressChunk.getUserStatus().equals(currAddressChunk.getUserStatus())) {
                AddressChunk newAddressChunk = new AddressChunk();
                newAddressChunk.setStartTime(previousAddressChunk.getStartTime());
                newAddressChunk.setEndTime(currAddressChunk.getStartTime());
                if (previousAddressChunk.getUserStatus().equals(UserStatus.MOVING)) {
                    newAddressChunk.setUserStatus(UserStatus.MOVING);
                } else if (previousAddressChunk.getUserStatus().equals(UserStatus.UNKNOWN)) {
                    newAddressChunk.setUserStatus(UserStatus.UNKNOWN);
                } else {
                    newAddressChunk.setAddress(previousAddressChunk.getAddress());
                    newAddressChunk.setUserStatus(UserStatus.STATIC);
                }
                addressChunkMerged.add(newAddressChunk);
                previousAddressChunk = currAddressChunk;
            } else {
                if (currAddressChunk.getUserStatus().equals(UserStatus.STATIC) &&
                        !currAddressChunk.getAddress().equalsIgnoreCase(previousAddressChunk.getAddress())) {
                    AddressChunk newAddressChunk = new AddressChunk();
                    newAddressChunk.setAddress(previousAddressChunk.getAddress());
                    newAddressChunk.setUserStatus(UserStatus.STATIC);
                    newAddressChunk.setStartTime(previousAddressChunk.getStartTime());
                    newAddressChunk.setEndTime(currAddressChunk.getStartTime());

                    addressChunkMerged.add(newAddressChunk);
                    previousAddressChunk = currAddressChunk;
                }
            }

        }

        return addressChunkMerged;
    }
}
