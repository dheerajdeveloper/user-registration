package com.dheeraj.user.registration.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    @Column(length = 60)
    private String password;

    private boolean enabled;

}