package com.dheeraj.user.registration.controller;

import com.dheeraj.user.registration.model.RolePrivilegeMapping;
import com.dheeraj.user.registration.model.User;
import com.dheeraj.user.registration.model.UserRoleMapping;
import com.dheeraj.user.registration.model.pojo.RolePrivilegeRequest;
import com.dheeraj.user.registration.model.pojo.UserPrivilegeRequest;
import com.dheeraj.user.registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by dheeraj on 13/09/17.
 */

@RequestMapping("user-registration/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("add")
    public User addUser(@RequestBody  User user) {
        return userService.addUser(user);
    }

    @GetMapping("getUserByEmailId")
    public User getUserByEmailId(@RequestParam("emailId") String emailId) {
        return userService.getUserByEmailId(emailId);
    }

    @PostMapping("addRoleForUser")
    public UserRoleMapping addRoleForUser(@RequestBody UserPrivilegeRequest rolePrivilegeRequest) {

        return userService.addRoleForUser(rolePrivilegeRequest);
    }

    @GetMapping("getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
