package com.example.gradesys.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerResponseDto{

    private UserInfoDto mentor;
    private UserInfoDto student;
}