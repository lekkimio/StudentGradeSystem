package com.example.gradesys.controller;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status436UserExistsException;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public User signupProcess(UserRequestDto user) throws Status436UserExistsException, Status437SubjectNotFound, Status434UserNotFound {
        return userService.signup(user);
    }
}
