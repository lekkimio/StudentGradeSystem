package com.example.gradesys.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;



@Data
@Builder
public class ResultInfo {
    private UserInfoDto user;
    private List<Double> grades;
}