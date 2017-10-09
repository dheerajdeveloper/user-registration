package com.dheeraj.user.registration.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by dheeraj on 06/10/17.
 */
@Entity
@Data
public class LocationAddressMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    String lat;

    String lng;

    public LocationAddressMapping() {
    }

    String address;

    public LocationAddressMapping(String lat, String lng, String address) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }
}
