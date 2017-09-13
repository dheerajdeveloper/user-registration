package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.model.Privilege;
import com.dheeraj.user.registration.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@Service
public class PrivilegeService {

    @Autowired
    PrivilegeRepository privilegeRepository;


    public Privilege addPriviledge(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    public List<Privilege> getAllPriviledges() {

        return privilegeRepository.findAll();
    }
}
