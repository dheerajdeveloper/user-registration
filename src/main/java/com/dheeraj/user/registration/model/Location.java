package com.dheeraj.user.registration.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by dheeraj on 13/09/17.
 */

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    long userId;

    String latitude;

    String longitude;

    String locationtime;

    String address;

    Long addressSequence;

    Long durationAtCurrentLocation;

    Integer locationScanned;

    String placeId;

    Date updatedOn;

}
