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
public class RolePrivilegeMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    long roleId;

    long privilegeId;

    public RolePrivilegeMapping(long roleId, long privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }
}
