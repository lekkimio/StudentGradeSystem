package com.example.gradesys.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.example.gradesys.model.Result} entity
 */
@Data
public class ResultResponseDto implements Serializable {
    private UserResponseDto user;
    private String subject;
    private Double grade;
}