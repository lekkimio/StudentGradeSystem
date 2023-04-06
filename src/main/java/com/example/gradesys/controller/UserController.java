package com.example.gradesys.controller;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status440ManagerNotFound;
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
    public List<ManagerResponseDto> getManagerStudents(@PathVariable Long managerId) {
        return modelMapper.map( userService.getManagerStudents(managerId),
                new TypeToken<List<ManagerResponseDto>>(){}.getType());
    }

    @PatchMapping("/manager")
    public void editManager(ManagerDto managerDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {
        userService.editManagerToStudent(managerDto, userDetails);
    }

    @DeleteMapping("/manager/{studentId}")
    public void deleteManager(@PathVariable Long studentId, CustomUserDetails userDetails) throws Status440ManagerNotFound, Status435NoAuthorities {
        userService.deleteManagerToStudent(studentId, userDetails);
    }

    //TODO wrong naming
    @GetMapping("/all")
    public List<UserResponseDto> getAllUsers(CustomUserDetails userDetails) {
        return modelMapper.map(userService.getAllUsers(userDetails), new TypeToken<List<UserResponseDto>>() {}.getType());
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) throws Status434UserNotFound {
        return modelMapper.map(userService.getUserById(id), UserResponseDto.class);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {
        userService.deleteUser(id, userDetails);
    }

    //TODO wrong http-method for update
    @PostMapping("/{userId}")
    public void updateUser(@PathVariable Long userId, UserInfoDto infoDto, CustomUserDetails userDetails) throws Status434UserNotFound, Status435NoAuthorities {
        userService.updateUser(userId, infoDto, userDetails);
    }

}
