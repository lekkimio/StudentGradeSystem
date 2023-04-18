package com.example.gradesys.controller;

import com.example.gradesys.exception.Status404UserNotFound;
import com.example.gradesys.model.dto.ManagerDto;
import com.example.gradesys.model.dto.ManagerResponseDto;
import com.example.gradesys.model.dto.UserInfoDto;
import com.example.gradesys.model.dto.UserResponseDto;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//TODO rename to 'users'
@RequestMapping("/user")
//TODO why is it here, but dont using?
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<List<ManagerResponseDto>> getManagerStudents(@PathVariable Long managerId) {
        return ResponseEntity.ok(modelMapper.map(
                userService.getManagerStudents(managerId),
                new TypeToken<List<ManagerResponseDto>>() {}.getType())
        );
    }

    @PatchMapping("/manager")
    public ResponseEntity<Object> editManager(ManagerDto managerDto, CustomUserDetails userDetails) {
        userService.editManagerToStudent(managerDto, userDetails);
    }

    @DeleteMapping("/manager/{studentId}")
    public ResponseEntity<Object> deleteManager(@PathVariable Long studentId, CustomUserDetails userDetails) {
        userService.deleteManagerToStudent(studentId, userDetails);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(CustomUserDetails userDetails) {

        return ResponseEntity.ok(modelMapper.map(userService.getAllUsers(userDetails), new TypeToken<List<UserResponseDto>>() {
        }.getType()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) throws Status404UserNotFound {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserResponseDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id, CustomUserDetails userDetails)  {
        userService.deleteUser(id, userDetails);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, UserInfoDto infoDto, CustomUserDetails userDetails)  {
        userService.updateUser(userId, infoDto, userDetails);
    }

}
