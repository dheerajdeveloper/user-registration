package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(value = "getLocationForUserForLastNMinute" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody  List<Location> getLocationForUserForLastNMinute(@RequestParam("userId") long userId,
                                                           @RequestParam("minute") int minute) {
        List<Location> locationList = locationService.getLocationForUserForLastNMinute(userId, minute);

        return locationList;

    }

    @GetMapping(value = "getLocationForUserForTimeRange" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Location> getLocationForUserForTimeRange(@RequestParam("userId") long userId,
                                                         @RequestParam("starttime") String starttime,
                                                         @RequestParam("endtime") String endtime) {
        List<Location> locationList =  locationService.getLocationForUserForTimeRange(userId, starttime , endtime);

        if(CollectionUtils.isEmpty(locationList)){
            locationList = new ArrayList<>();
        }

        System.out.println("Total number of location models for getLocationForUserForTimeRange " + locationList.size());

        return locationList;

    }



}
