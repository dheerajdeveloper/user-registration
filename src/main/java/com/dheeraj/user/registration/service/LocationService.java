package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.repository.LocationRepository;
import com.dheeraj.user.registration.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@Service
public class LocationService {

    @Autowired
    LocationRepository locationRepository;


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


        return locationRepository.findAllByUserIdAndLocationtimeIsAfterOrderByLocationtimeDesc(userId , date);
    }

    public List<Location> getLocationForUserForTimeRange(long userId, String starttime, String endtime) {
        return locationRepository.findAllByUserIdAndLocationtimeIsBetweenOrderByLocationtimeDesc(userId , starttime , endtime);
    }
}
