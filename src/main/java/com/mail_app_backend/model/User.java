package com.mail_app_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_id", nullable = false,unique = true)
    private String email;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role = "user";
    @Column(name = "profile_pic")
    private String profilePicture;


}
