package com.example.gradesys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {

    private Long userId;
    private Long subjectId;
    private Double grade = 0.0;

    public ResultDto(Long userId, Long subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }
}
