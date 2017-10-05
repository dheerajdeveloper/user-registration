package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.helper.LocationHelper;
import com.dheeraj.user.registration.model.pojo.AddressChunk;
import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.repository.LocationRepository;
import com.dheeraj.user.registration.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationHelper locationHelper;


    public Location addLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> getLocationsForUser(long userId) {
        return locationRepository.findAllByUserIdOrderByLocationtimeDesc(userId);
    }

    public Location getLatestLocationForUser(long userId) {
        return locationRepository.findFirstByUserIdOrderByLocationtimeDesc(userId);
    }

    public List<Location> getLocationForUserForLastNMinute(long userId, int minute) {
        String date = DateUtil.getTimeFromLastNMinute(minute);

        System.out.println("Date after which to search " + date);


        return locationRepository.findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeDesc(userId, date);
    }


    public List<AddressChunk> getAddressForUserForLastNMinute(long userId, int minute) {
        String date = DateUtil.getTimeFromLastNMinute(minute);

        System.out.println("Date after which to search for address" + date);


        return getAddressChunks(locationRepository.findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeDesc(userId, date));
    }

    public List<Location> getLocationForUserForTimeRange(long userId, String starttime, String endtime) {
        return locationRepository.findAllByUserIdAndLocationtimeIsBetweenOrderByLocationtimeDesc(userId, starttime, endtime);
    }


    public List<AddressChunk> getAddressForUserForTimeRange(long userId, String starttime, String endtime) {
        return getAddressChunks(locationRepository.findAllByUserIdAndLocationtimeIsBetweenOrderByLocationtimeDesc(userId, starttime, endtime));
    }

    public Location getPrevLocation(Long id) {
        return locationRepository.findFirstByIdIsLessThanAndLocationScannedIsNotNullOrderByIdDesc(id);
    }


    public List<Location> getLocationForUserForLastNDay(long userId, int day) {
        String date = DateUtil.getTimeFromLastNDay(day);

        System.out.println("Date after which to search for  getLocationForUserForLastNDay" + date);

        return
                locationRepository.
                        findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeAsc(userId, date);
    }

    public List<AddressChunk> getAddressForUserForLastNDay(long userId, int day) {
        String date = DateUtil.getTimeFromLastNDay(day);

        System.out.println("Date after which to search for  getAddressForUserForLastNDay" + date);

        return getAddressChunks(
                locationRepository.
                        findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeAsc(userId, date));
    }


    private List<AddressChunk> getAddressChunks(List<Location> locationList) {

        if (CollectionUtils.isEmpty(locationList)) {
            return new ArrayList<>();
        }

        List<AddressChunk> addressChunkList = locationHelper.getLocationInChunks(locationList);
        addressChunkList = locationHelper.mergeCommonAddressChunks(addressChunkList);

        if (CollectionUtils.isEmpty(addressChunkList)) {
            addressChunkList = new ArrayList<>();
        }

        return addressChunkList;
    }

}
