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

    User createUser(UserRequestDto authDto) throws Status436UserExistsException, Status437SubjectNotFound, Status434UserNotFound;

    List<User> getAllUsers(CustomUserDetails userDetails);
    User getUserById(Long id) throws Status434UserNotFound;

    void deleteUser(Long id, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities;

    List<User> getAllStudents();

    void editManagerToStudent(ManagerDto managerDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities;

    void deleteManagerToStudent(Long studentId, CustomUserDetails userDetails) throws Status435NoAuthorities, Status440ManagerNotFound;

    List<Manager> getManagerStudents(Long managerId);

    Manager getManagerByStudent(Long studentId) throws Status440ManagerNotFound;

    void updateUser(Long userId, UserInfoDto authDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities;

    List<User> getAllManagers();

    boolean existsByStudent_Id(Long id);
}
