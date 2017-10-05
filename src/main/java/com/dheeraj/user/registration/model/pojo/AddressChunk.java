package com.dheeraj.user.registration.model.pojo;

import com.dheeraj.user.registration.enums.UserStatus;
import lombok.Data;

/**
 * Created by dheeraj on 03/10/17.
 */

@Data
public class AddressChunk implements Cloneable{

    String startTime;
    String endTime;
    String address;
    UserStatus userStatus;

}
