package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.model.Privilege;
import com.dheeraj.user.registration.model.Role;
import com.dheeraj.user.registration.model.RolePrivilegeMapping;
import com.dheeraj.user.registration.model.pojo.RolePrivilegeRequest;
import com.dheeraj.user.registration.repository.PrivilegeRepository;
import com.dheeraj.user.registration.repository.RolePrivilegeMappingRepository;
import com.dheeraj.user.registration.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    RolePrivilegeMappingRepository rolePrivilegeMappingRepository;

    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public RolePrivilegeMapping addPrivilegeForRole(RolePrivilegeRequest rolePrivilegeRequest) {
        Role role = roleRepository.findFirstByName(rolePrivilegeRequest.getRoleName());

        Privilege privilege = privilegeRepository.findFirstByName(rolePrivilegeRequest.getPrivilegeName());

        if (role != null && privilege != null) {
            return rolePrivilegeMappingRepository.save(new RolePrivilegeMapping(role.getId(), privilege.getId()));
        }
        return null;
    }
}
