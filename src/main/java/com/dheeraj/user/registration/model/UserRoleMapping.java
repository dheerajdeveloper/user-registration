package com.dheeraj.user.registration.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by dheeraj on 13/09/17.
 */

@Entity
@Data
public class UserRoleMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    long roleId;

    long userId;

    public UserRoleMapping(long roleId, long userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}
