package com.example.gradesys.controller;

import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> signupProcess(UserRequestDto user) {
        userService.createUser(user);
        return ResponseEntity.ok().build();
    }


}
