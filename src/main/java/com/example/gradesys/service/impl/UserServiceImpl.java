package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status436UserExistsException;
import com.example.gradesys.exception.Status440ManagerNotFound;
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



    public User createUser(UserRequestDto authDto) throws Status436UserExistsException {
        if (userRepository.findByUsername(authDto.getUsername()) != null) {
            throw new Status436UserExistsException(authDto.getUsername());
        } else {
            User newUser = User.builder()
                    .firstName(authDto.getFirstName())
                    .lastName(authDto.getLastName())
                    .username(authDto.getUsername())
                    .password(passwordEncoder.encode(authDto.getPassword()))
                    .roles(Set.of(roleService.getRoleByName(ERole.STUDENT)))
                    .createdAt(LocalDateTime.now())
                    .build();


            log.info("New User created {}", newUser.getUsername());

            return userRepository.save(newUser);
        }
    }

    public List<User> getAllUsers(CustomUserDetails userDetails) {
        if (roleService.isStudent(userDetails.getAuthorities())) {
            return Collections.singletonList(userRepository.getReferenceById(userDetails.getId()));
        }
        else return userRepository.findAll();
    }

    public User getUserById(Long id) throws Status434UserNotFound {
        return userRepository.findById(id).orElseThrow(() -> new Status434UserNotFound(id));
    }

    public void deleteUser(Long id, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {

        User userToDelete = getUserById(id);
        if (userToDelete == null) throw new Status434UserNotFound(id);

        if (!userDetails.getId().equals(id) && !roleService.isAdmin(userDetails.getAuthorities())) {
            throw new Status435NoAuthorities("Delete this User");
        }

        userRepository.delete(userToDelete);
        log.info("User {} {} was deleted by {}", userToDelete.getFirstName(), userToDelete.getLastName(),
                userDetails.getUsername());

    }

    public List<User> getAllStudents() {
        return userRepository.findByRoles_NameOrderByIdAsc(ERole.STUDENT);
    }


    private void setManagerToStudent(ManagerDto managerDto) throws Status434UserNotFound, Status435NoAuthorities {
        User manager = getUserById(managerDto.getManagerId());

        if (!roleService.isManager(manager.getRoles())) throw new Status435NoAuthorities("set this user as manager");

        Manager manager1 = Manager.builder().student(getUserById(managerDto.getStudentId())).mentor(manager).build();
        managerRepository.save(manager1);

    }

    public void editManagerToStudent(ManagerDto managerDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {

        if (!roleService.isAdmin(userDetails.getAuthorities()) ) throw new Status435NoAuthorities("edit manager");

        if (existsByStudent_Id(managerDto.getStudentId())) {
            Manager manager = managerRepository.getByStudent_Id(managerDto.getStudentId());
            manager.setMentor(getUserById(managerDto.getManagerId()));
            managerRepository.save(manager);

        } else {
            setManagerToStudent(managerDto);
        }


    }

    public void deleteManagerToStudent(Long studentId, CustomUserDetails userDetails) throws Status435NoAuthorities, Status440ManagerNotFound {

        if (!roleService.isAdmin(userDetails.getAuthorities())) throw new Status435NoAuthorities("delete manager");

        if (!existsByStudent_Id(studentId)) throw new Status440ManagerNotFound("Manager Not Found by studentId: "+studentId);

        managerRepository.delete(managerRepository.getByStudent_Id(studentId));

    }

    public List<Manager> getManagerStudents(Long managerId) {
        return managerRepository.findAllByMentor_Id(managerId);
    }


    public Manager getManagerByStudent(Long studentId) {
        return managerRepository.getByStudent_Id(studentId);
    }

    public void updateUser(Long userId, UserInfoDto infoDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {
        if (!userDetails.getId().equals(userId) && !roleService.isAdmin(userDetails.getAuthorities())) throw new Status435NoAuthorities("update user");

        User userToUpdate = getUserById(userId);

        if (infoDto.getFirstName() != null) {
            userToUpdate.setFirstName(infoDto.getFirstName());
        }
        if (infoDto.getLastName() != null) {
            userToUpdate.setLastName(infoDto.getLastName());
        }

        userRepository.save(userToUpdate);
    }

    public List<User> getAllManagers() {
        return userRepository.findByRoles_NameOrderByIdAsc(ERole.MANAGER);
    }

    public boolean existsByStudent_Id(Long studentId) {
        return managerRepository.existsByStudent_Id(studentId);
    }



}
