package com.dheeraj.user.registration.util;

import com.dheeraj.user.registration.AddressUpdateTask;
import com.dheeraj.user.registration.model.Location;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by dheeraj on 20/09/17.
 */
public class GoogleMapsHelper {
    private static final Logger log = LoggerFactory.getLogger(GoogleMapsHelper.class);


    private static GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyA71ZYLo0w2OxdjXEgt1gsmtql9Et__6xs")
            .build();


    public static GeocodingResult getGeocodingResult(Location location) {

        LatLng currLoc =
                new LatLng(Double.parseDouble(location.getLatitude()),
                        Double.parseDouble(location.getLongitude()));
        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context,
                    currLoc).await();

            if (results != null && results.length > 0) {
                return results[0];
            }
        } catch (Exception e) {
//            log.info("Exception while fetching address {}", e);
        }

        return null;

    }
}
