package com.example.gradesys.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * A Projection for the {@link com.example.gradesys.model.Result} entity
 */

@Data
@Builder
public class ResultInfo {
    private UserResponseDto user;
    private List<Double> grades;
}