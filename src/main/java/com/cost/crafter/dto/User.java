package com.cost.crafter.dto;

import lombok.Data;

@Data
public class User {

    private Integer userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String dateOfBirth;
}
