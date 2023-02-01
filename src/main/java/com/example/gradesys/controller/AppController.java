package com.example.gradesys.controller;


import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.model.dto.UserResponseDto;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.SubjectService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final ResultService resultService;
    private final UserService userService;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/students")
    public String getStudents(Model model){
        model.addAttribute("students", userService.getAllStudents());
        return "students";
    }

    @GetMapping("/journal")
    public String getJournal(Model model) {
        List<User> students = userService.getAllStudents();
        model.addAttribute("students", students);
        model.addAttribute("subjects", subjectService.getAllSubjects());

        List<ResultInfo> resultInfoList = new ArrayList<>();

        for (User student : students) {
            resultInfoList.add(ResultInfo.builder()
                    .user(modelMapper.map(student, UserResponseDto.class))
                    .grades(resultService.getAllGradesByUser(student))
                    .build());
        }


        model.addAttribute("resultList",resultInfoList);
        return "journal";
    }


    @GetMapping("/admin")
    public String getStudents(Principal principal){
        return "admin";
    }

}
