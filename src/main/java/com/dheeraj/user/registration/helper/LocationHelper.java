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
        Integer count = 0;
        DateTime prevDateTime = DateUtil.convertToJodaTime(locationList.get(0).getLocationtime());
        DateTime nextDateTime = prevDateTime.plusSeconds(Constants.CHUNK_SECONDS);
        DateTime currTime = null;
        for (int i = 1; i < locationList.size(); i++) {
            Location location = locationList.get(i);
            String address = location.getAddress();

            currTime = DateUtil.convertToJodaTime(location.getLocationtime());


            if (currTime.isAfter(nextDateTime)) {


                addressChunkList.add(addAddressChunk(prevDateTime, currTime, addressCountMap, count));


                /**
                 * get all those address for which the count in more than
                 * limit.generally it is more than 80%
                 */


                prevDateTime = currTime;
                nextDateTime = prevDateTime.plusSeconds(Constants.CHUNK_SECONDS);
                addressCountMap = new HashMap<>();
                count = 0;
            } else if (address != null && !"null".equalsIgnoreCase(address)) {
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
        if (currTime.isAfter(prevDateTime)) {
            addressChunkList.add(addAddressChunk(prevDateTime, currTime, addressCountMap, count));
        }
        return addressChunkList;
    }

    private AddressChunk addAddressChunk(DateTime prevDateTime, DateTime currTime, Map<String, Integer> addressCountMap, Integer count) {
        AddressChunk addressChunk = new AddressChunk();
        TreeMap<Integer, String> countAddressMap = new TreeMap<>(Collections.reverseOrder());

        addressChunk.setStartTime(DateUtil.convertToString(prevDateTime));
        addressChunk.setEndTime(DateUtil.convertToString(currTime));

        if (CollectionUtils.isEmpty(addressCountMap)) {
            addressChunk.setUserStatus(UserStatus.UNKNOWN);
        } else {

            for (Map.Entry<String, Integer> entry : addressCountMap.entrySet()) {
                countAddressMap.put(entry.getValue(), entry.getKey());
            }


            ArrayList<String> probableAddresses = getMajorityAddressList(countAddressMap, count);
            if (probableAddresses.size() > Constants.MAX_LIMIT_CHUNK_ENTRIES) {

                addressChunk.setUserStatus(UserStatus.MOVING);

            } else {

                boolean allPointsNear = checkIfAllPointsNear(probableAddresses);
                if (allPointsNear) {
                    addressChunk.setUserStatus(UserStatus.STATIC);
                    addressChunk.setAddress(countAddressMap.firstEntry().getValue());
                } else {
                    addressChunk.setUserStatus(UserStatus.MOVING);
                }

            }
        }

        return addressChunk;

    }

    private ArrayList<String> getMajorityAddressList(TreeMap<Integer, String> countAddressMap, Integer count) {
        int currCount = 0;
        ArrayList<String> addressList = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : countAddressMap.entrySet()) {
            addressList.add(entry.getValue());

            currCount += entry.getKey();
            if (((currCount * 100) / count) > Constants.MAJORITY_PERCENTAGE_LIMIT) {
                break;
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
        if (CollectionUtils.isEmpty(addressChunkList)) {
            return new ArrayList<>();
        }

        if (addressChunkList.size() == 1) {
            return addressChunkList;
        }

        List<AddressChunk> addressChunkMerged = new ArrayList<>();
        AddressChunk previousAddressChunk = addressChunkList.get(0);
        AddressChunk currAddressChunk = null;
        int size = addressChunkList.size();
        for (int i = 1; i < addressChunkList.size(); i++) {
            currAddressChunk = addressChunkList.get(i);

            if (!previousAddressChunk.getUserStatus().equals(currAddressChunk.getUserStatus())) {
                addressChunkMerged.add(addNewAddressChunk(previousAddressChunk, currAddressChunk));

                previousAddressChunk = currAddressChunk;
            } else if (currAddressChunk.getUserStatus().equals(UserStatus.STATIC) &&
                    !currAddressChunk.getAddress().equalsIgnoreCase(previousAddressChunk.getAddress())) {
                AddressChunk newAddressChunk = new AddressChunk();
                newAddressChunk.setAddress(previousAddressChunk.getAddress());
                newAddressChunk.setUserStatus(UserStatus.STATIC);
                newAddressChunk.setStartTime(previousAddressChunk.getStartTime());
                newAddressChunk.setEndTime(currAddressChunk.getStartTime());

                addressChunkMerged.add(newAddressChunk);
                previousAddressChunk = currAddressChunk;
            } else if (i == size - 1) {
                //handle last address chunk
//                if (CollectionUtils.isEmpty(addressChunkMerged)) {
//                    previousAddressChunk.setEndTime(currAddressChunk.getEndTime());
//                    addressChunkMerged.add(previousAddressChunk);
//
//                } else {
//                    int addressChunkMergedLastIndex = addressChunkMerged.size() - 1;
//                    addressChunkMerged.get(addressChunkMergedLastIndex).setEndTime(currAddressChunk.getEndTime());
//                }
                previousAddressChunk.setEndTime(currAddressChunk.getEndTime());
                addressChunkMerged.add(previousAddressChunk);
            }

        }
        return addressChunkMerged;
    }

    private AddressChunk addNewAddressChunk(AddressChunk previousAddressChunk, AddressChunk currAddressChunk) {
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
        return newAddressChunk;
    }
}
