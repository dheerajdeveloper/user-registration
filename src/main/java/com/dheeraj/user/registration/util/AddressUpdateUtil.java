package com.dheeraj.user.registration.util;

import com.dheeraj.user.registration.model.Location;

/**
 * Created by dheeraj on 20/09/17.
 */
public class AddressUpdateUtil {
    public static Long getDurationAtLocation(Location prevLocation, Location location) {

        Long totalDuration = prevLocation.getDurationAtCurrentLocation();

        totalDuration += DateUtil.getTimeDifferenceInSeconds(prevLocation.getLocationtime(), location.getLocationtime());

        return totalDuration;
    }
}
