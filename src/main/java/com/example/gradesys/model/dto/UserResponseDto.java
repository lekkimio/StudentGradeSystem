package com.example.gradesys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.gradesys.model.User} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto implements Serializable {
    private String firstName;
    private String lastName;
}