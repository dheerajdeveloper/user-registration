package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.repository.LocationRepository;
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
        return locationRepository.findAllByUserId(userId);
    }
}
