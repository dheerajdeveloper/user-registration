package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@RequestMapping("user-registration/location")
@RestController
public class LocationController {

    @Autowired
    LocationService locationService;

    @PostMapping("add")
    public Location addLocation(@RequestBody Location location) {
        return locationService.addLocation(location);
    }

    @GetMapping("getLocationForUser")
    public List<Location> getLocationForUser(@RequestParam("userId") long userId) {
        return locationService.getLocationsForUser(userId);

    }

    @GetMapping("getLatestLocationForUser")
    public Location getLatestLocationForUser(@RequestParam("userId") long userId) {
        return locationService.getLatestLocationForUser(userId);

    }

    @GetMapping("getLocationForUserForLastNMinute")
    public List<Location> getLocationForUserForLastNMinute(@RequestParam("userId") long userId,
                                                           @RequestParam("minute") int minute) {
        return locationService.getLocationForUserForLastNMinute(userId, minute);

    }

    @GetMapping("getLocationForUserForTimeRange")
    public List<Location> getLocationForUserForTimeRange(@RequestParam("userId") long userId,
                                                         @RequestParam("starttime") String starttime,
                                                         @RequestParam("endtime") String endtime) {
        return locationService.getLocationForUserForTimeRange(userId, starttime , endtime);

    }



}
