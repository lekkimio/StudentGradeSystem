package com.example.gradesys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Set<RoleDto> roles;
}