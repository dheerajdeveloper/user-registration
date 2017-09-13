package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.Privilege;
import com.dheeraj.user.registration.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@RequestMapping("user-registration/priviledge")
@RestController
public class PriviledgeController {

    @Autowired
    PrivilegeService privilegeService;

    @PostMapping("add")
    public Privilege addPriviledge(@RequestBody Privilege privilege) {
        return privilegeService.addPriviledge(privilege);
    }

    @GetMapping(value = "getAllPriviledges", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Privilege> getAllPriviledges() {
        List<Privilege> val =  privilegeService.getAllPriviledges();
        return val;
    }
}
