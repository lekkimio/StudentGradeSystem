package com.example.gradesys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDto {

    private Long userId;
    private Long subjectId;
    private Double grade;

}
