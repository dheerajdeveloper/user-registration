package com.dheeraj.user.registration.service;

import com.dheeraj.user.registration.model.*;
import com.dheeraj.user.registration.model.pojo.UserPrivilegeRequest;
import com.dheeraj.user.registration.repository.RoleRepository;
import com.dheeraj.user.registration.repository.UserRepository;
import com.dheeraj.user.registration.repository.UserRoleMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleMappingRepository userRoleMappingRepository;


    public User addUser(User user) {
        return userRepository.save(user);

    }

    public User getUserByEmailId(String emailId) {

        return userRepository.findFirstByEmail(emailId);
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public UserRoleMapping addRoleForUser(UserPrivilegeRequest userPrivilegeRequest) {

        Role role = roleRepository.findFirstByName(userPrivilegeRequest.getRoleName());

        User user = userRepository.findFirstByEmail(userPrivilegeRequest.getUserEmailId());

        if (role != null && user != null) {
            return userRoleMappingRepository.save(new UserRoleMapping(role.getId() , user.getId()));
        }
        return null;
    }
}
