package com.example.gradesys.service.impl;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.ERole;
import com.example.gradesys.model.Manager;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ManagerDto;
import com.example.gradesys.model.dto.UserInfoDto;
import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.repo.ManagerRepository;
import com.example.gradesys.repo.UserRepository;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.RoleService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    public User createUser(UserRequestDto authDto){
        if (userRepository.findByUsername(authDto.getUsername()) != null) {
            throw new Status409UserExistsException(authDto.getUsername());
        } else {
            User newUser = User.builder()
                    .firstName(authDto.getFirstName())
                    .lastName(authDto.getLastName())
                    .username(authDto.getUsername())
                    .password(passwordEncoder.encode(authDto.getPassword()))
                    .roles(Set.of(roleService.getRoleByName(ERole.STUDENT)))
                    .createdAt(LocalDateTime.now())
                    .build();

            log.info("Creating new user: username={}, firstName={}, lastName={}", newUser.getUsername(), newUser.getFirstName(), newUser.getLastName());

            newUser = userRepository.save(newUser);

            log.info("New User created: id={}, username={}", newUser.getId(), newUser.getUsername());

            return newUser;
        }
    }

    public List<User> getAllUsers(CustomUserDetails userDetails) {
        if (roleService.isStudent(userDetails.getAuthorities())) {
            User user = userRepository.getReferenceById(userDetails.getId());
            log.info("Retrieved user {} with id {}", user.getUsername(), user.getId());
            return Collections.singletonList(user);
        } else {
            List<User> users = userRepository.findAll();
            log.info("Retrieved {} users from the database", users.size());
            return users;
        }
    }

    public User getUserById(Long id) throws Status404UserNotFound {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found", id);
            return new Status404UserNotFound(id);
        });
    }

    public void deleteUser(Long id, CustomUserDetails userDetails)  {

        User userToDelete = getUserById(id);
        if (userToDelete == null) throw new Status404UserNotFound(id);

        if (!userDetails.getId().equals(id) && !roleService.isAdmin(userDetails.getAuthorities())) {
            throw new Status401UnauthorizedUser("Delete this User");
        }

        userRepository.delete(userToDelete);

        log.info("User {} {} with id {} was deleted by {}", userToDelete.getFirstName(), userToDelete.getLastName(),
                userToDelete.getId(), userDetails.getUsername());

    }

    public List<User> getAllStudents() {
        List<User> students = userRepository.findByRoles_NameOrderByIdAsc(ERole.STUDENT);
        log.info("Retrieved {} students", students.size());
        return students;
    }

    private void setManagerToStudent(ManagerDto managerDto) {
        User mentor = getUserById(managerDto.getManagerId());

        if (!roleService.isManager(mentor.getRoles())) {
            throw new Status401UnauthorizedUser("set this user as manager");
        }

        User student = getUserById(managerDto.getStudentId());
        Manager manager = Manager.builder().student(student).mentor(mentor).build();
        managerRepository.save(manager);

        log.info("User {} {} was set as manager for user {} {}", mentor.getFirstName(), mentor.getLastName(),
                student.getFirstName(), student.getLastName());
    }

    public void editManagerToStudent(ManagerDto managerDto, CustomUserDetails userDetails)  {

        log.info("Editing manager to student with details: {}", managerDto);

        if (!roleService.isAdmin(userDetails.getAuthorities())) {
            throw new Status401UnauthorizedUser("edit manager");
        }

        if (existsByStudent_Id(managerDto.getStudentId())) {
            Manager manager = managerRepository.getByStudent_Id(managerDto.getStudentId());
            manager.setMentor(getUserById(managerDto.getManagerId()));
            managerRepository.save(manager);
            log.info("Updated existing manager with details: {}", manager);

        } else {
            setManagerToStudent(managerDto);
            log.info("Created new manager with details: {}", managerDto);
        }
    }

    public void deleteManagerToStudent(Long studentId, CustomUserDetails userDetails)  {
        log.info("Trying to delete manager for student with id {}", studentId);

        if (!roleService.isAdmin(userDetails.getAuthorities())) {
            log.warn("Unauthorized attempt to delete manager for student with id {}", studentId);
            throw new Status401UnauthorizedUser("delete manager");
        }

        if (!existsByStudent_Id(studentId)) {
            log.warn("Manager not found for student with id {}", studentId);
            throw new Status404ManagerNotFound("Manager Not Found by studentId: " + studentId);
        }

        Manager managerToDelete = managerRepository.getByStudent_Id(studentId);
        managerRepository.delete(managerToDelete);
        log.info("Manager {} deleted for student with id {}", managerToDelete.getMentor().getUsername(), studentId);
    }

    public List<Manager> getManagerStudents(Long managerId) {
        return managerRepository.findAllByMentor_Id(managerId);
    }

    public Manager getManagerByStudent(Long studentId) {
        return managerRepository.getByStudent_Id(studentId);
    }

    public void updateUser(Long userId, UserInfoDto infoDto, CustomUserDetails userDetails)  {
        log.info("Updating user with id {} by {}", userId, userDetails.getUsername());

        if (!userDetails.getId().equals(userId) && !roleService.isAdmin(userDetails.getAuthorities())) throw new Status401UnauthorizedUser("update user");

        User userToUpdate = getUserById(userId);

        if (infoDto.getFirstName() != null) {
            userToUpdate.setFirstName(infoDto.getFirstName());
        }
        if (infoDto.getLastName() != null) {
            userToUpdate.setLastName(infoDto.getLastName());
        }

        userRepository.save(userToUpdate);

        log.info("User with id {} was updated by {}", userId, userDetails.getUsername());
    }


    public List<User> getAllManagers() {
        List<User> managers = userRepository.findByRoles_NameOrderByIdAsc(ERole.MANAGER);
        log.info("Retrieved {} managers.", managers.size());
        return managers;
    }


    public boolean existsByStudent_Id(Long studentId) {
        return managerRepository.existsByStudent_Id(studentId);
    }

}
