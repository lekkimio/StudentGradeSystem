package com.example.gradesys.service;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status436UserExistsException;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.UserRequestDto;

import java.util.List;

public interface UserService {

    User createUser(UserRequestDto authDto) throws Status436UserExistsException, Status437SubjectNotFound, Status434UserNotFound;

    List<User> getAllUsers();

    User getUserById(Long id) throws Status434UserNotFound;
    void deleteUser(Long id) throws Status434UserNotFound;

    List<User> getAllStudents();

    User signup(UserRequestDto authDto) throws Status436UserExistsException;
}
