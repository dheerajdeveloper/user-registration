package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Role;
import com.dheeraj.user.registration.model.RolePrivilegeMapping;
import com.dheeraj.user.registration.model.pojo.RolePrivilegeRequest;
import com.dheeraj.user.registration.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@RequestMapping("user-registration/role")
@RestController
public class RoleController {

    @Autowired
    RoleService roleService;


    @PostMapping("add")
    public Role addRole(@RequestBody Role role) {

        return roleService.addRole(role);
    }

    @PostMapping("addPrivilegeForRole")
    public RolePrivilegeMapping addPrivilegeForRole(@RequestBody RolePrivilegeRequest rolePrivilegeRequest) {

        return roleService.addPrivilegeForRole(rolePrivilegeRequest);
    }

    @GetMapping("getAllRoles")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
}
