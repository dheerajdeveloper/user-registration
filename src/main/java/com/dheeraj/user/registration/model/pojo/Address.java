package com.dheeraj.user.registration.model.pojo;

import lombok.Data;

/**
 * Created by dheeraj on 03/10/17.
 */

@Data
public class Address {
    long userId;

    String latitude;

    String longitude;

    String address;

    Long durationAtAddress;

    String placeId;
}
