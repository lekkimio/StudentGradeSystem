package com.example.gradesys.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.gradesys.model.User} entity
 */
@Data
public class UserResponseDto implements Serializable {
    private String firstName;
    private String lastName;
}