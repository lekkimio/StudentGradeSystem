package com.example.gradesys.service;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.Manager;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ManagerDto;
import com.example.gradesys.model.dto.UserInfoDto;
import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.security.CustomUserDetails;

import java.util.List;

public interface UserService {

    User createUser(UserRequestDto authDto) throws Status409UserExistsException, Status404SubjectNotFound, Status404UserNotFound;

    List<User> getAllUsers(CustomUserDetails userDetails);
    User getUserById(Long id) throws Status404UserNotFound;

    void deleteUser(Long id, CustomUserDetails userDetails) throws Status404UserNotFound, Status401Unauthorized;

    List<User> getAllStudents();

    void editManagerToStudent(ManagerDto managerDto, CustomUserDetails userDetails) throws Status404UserNotFound, Status401Unauthorized;

    void deleteManagerToStudent(Long studentId, CustomUserDetails userDetails) throws Status401Unauthorized, Status404ManagerNotFound;

    List<Manager> getManagerStudents(Long managerId);

    Manager getManagerByStudent(Long studentId) throws Status404ManagerNotFound;

    void updateUser(Long userId, UserInfoDto authDto, CustomUserDetails userDetails) throws Status404UserNotFound, Status401Unauthorized;

    List<User> getAllManagers();

    boolean existsByStudent_Id(Long id);
}
