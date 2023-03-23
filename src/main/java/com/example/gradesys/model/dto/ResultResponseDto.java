package com.example.gradesys.model.dto;

import lombok.Data;

@Data
public class ResultResponseDto {
    private UserInfoDto user;
    private String subject;
    private Double grade;
}