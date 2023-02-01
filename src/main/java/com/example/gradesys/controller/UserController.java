package com.example.gradesys.controller;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status436UserExistsException;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/create-user")
    public User createUser(UserRequestDto authDto) throws Status436UserExistsException, Status437SubjectNotFound, Status434UserNotFound {
        return userService.createUser(authDto);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() throws Status436UserExistsException {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) throws Status436UserExistsException, Status434UserNotFound {
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete-user/{id}")
    public void deleteUser(@PathVariable Long id) throws Status436UserExistsException, Status434UserNotFound {
        userService.deleteUser(id);
    }

//    @PostMapping("/update-user")
//    public void updateUser(UserRequestDto authDto) throws Status436UserExistsException {
//        userService.updateUser(authDto);
//    }



}
