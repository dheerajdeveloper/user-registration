package com.dheeraj.user.registration;

import com.dheeraj.user.registration.model.Location;
import com.dheeraj.user.registration.model.LocationAddressMapping;
import com.dheeraj.user.registration.repository.LocationAddressMappingRepository;
import com.dheeraj.user.registration.repository.LocationRepository;
import com.dheeraj.user.registration.service.LocationService;
import com.dheeraj.user.registration.util.AddressUpdateUtil;
import com.dheeraj.user.registration.util.GoogleMapsHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class AddressUpdateTask {

    private static final Logger log = LoggerFactory.getLogger(AddressUpdateTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationService locationService;

    @Autowired
    LocationAddressMappingRepository locationAddressMappingRepository;

//    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void updateAddress() throws Exception {

        Location prevLocation = null;

        List<Location> locationList
                = locationRepository.findAllByLocationScannedIsNullOrderByLocationtime();

        if (CollectionUtils.isEmpty(locationList)) {
            return;
        }

        for (Location location : locationList) {
            if (prevLocation == null) {
                prevLocation = locationService.getPrevLocation(location.getId());
            }

            updateLocationAddress(location);

            /*
            if previous location is null , then we will treat current location
            as the starting location
             */
            if (prevLocation == null) {


                location.setAddressSequence(1l);
                location.setDurationAtCurrentLocation(0l);


            } else {
                /*
                if prev location is not null then we will update the current location based on the
                prev location object
                 */

                // if place id match then it is same location
                if (prevLocation.getAddress() != null &&
                        location.getAddress() != null &&
                        prevLocation.getAddress().equalsIgnoreCase(location.getAddress())) {
                    location.setAddressSequence(prevLocation.getAddressSequence());
                    location.setDurationAtCurrentLocation(AddressUpdateUtil.getDurationAtLocation(prevLocation, location));
                } else {
                    //it is a new location

                    location.setAddressSequence(prevLocation.getAddressSequence() + 1);
                    location.setDurationAtCurrentLocation(0l);
                    // whatever is the difference is the time spent at older location
                    prevLocation.setDurationAtCurrentLocation(AddressUpdateUtil.getDurationAtLocation(prevLocation, location));
                    prevLocation = locationRepository.save(prevLocation);

                }


            }

            location.setLocationScanned(1);
            try {
                locationRepository.save(location);
            } catch (Exception e) {
                location.setAddress(getAsciiString(location.getAddress()));
                locationRepository.save(location);

            }
            prevLocation = location;

        }

        log.info("Successfully process all locations {}", locationList.size());


    }

    private String getAsciiString(String address) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < address.length(); i++) {
            if (address.charAt(i) < 128) {
                sb.append(address.charAt(i));
            }
        }

        return sb.toString();
    }

    private void updateLocationAddress(Location location) {

        LocationAddressMapping locationAddressMapping =
                locationAddressMappingRepository.findFirstByLatAndLng(location.getLatitude(),
                        location.getLongitude());

        if (locationAddressMapping != null && locationAddressMapping.getAddress() != null) {
            location.setAddress(locationAddressMapping.getAddress());
            return;
        }
        GeocodingResult geocodingResult = GoogleMapsHelper.getGeocodingResult(location);
        if (geocodingResult != null) {
            location.setAddress(geocodingResult.formattedAddress.trim());
        }

        LocationAddressMapping locationAddressMapping1 = new LocationAddressMapping(location.getLatitude(),
                location.getLongitude(),
                location.getAddress());

        try {
            locationAddressMappingRepository.save(locationAddressMapping1);
        } catch (Exception e) {
            locationAddressMapping1.setAddress(getAsciiString(locationAddressMapping1.getAddress()));
            locationAddressMappingRepository.save(locationAddressMapping1);

        }

    }
}
