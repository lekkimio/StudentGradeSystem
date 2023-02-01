package com.example.gradesys.model.dto;


import lombok.Data;

@Data
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
