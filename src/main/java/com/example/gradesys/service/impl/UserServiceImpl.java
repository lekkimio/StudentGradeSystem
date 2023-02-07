package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status436UserExistsException;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Role;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.UserRequestDto;
import com.example.gradesys.repo.SubjectRepository;
import com.example.gradesys.repo.UserRepository;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ResultService resultService;

    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDto authDto) throws Status436UserExistsException, Status437SubjectNotFound, Status434UserNotFound {
            User newUser = signup(authDto);

            List<Subject> subjects = subjectRepository.findAll();

            for (Subject subject : subjects) {
                resultService.createResult(new ResultDto(newUser.getId(), subject.getId()));
            }

            return newUser;

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws Status434UserNotFound {
        return userRepository.findById(id).orElseThrow(()-> new Status434UserNotFound(id));
    }

    public void deleteUser(Long id) throws Status434UserNotFound {

        User user = getUserById(id);
        if (user != null) {
            resultService.deleteAllUserResults(user.getId());
            userRepository.delete(user);
            log.info("User {} {} was deleted by {}",
                    user.getFirstName(),user.getLastName(), SecurityContextHolder.getContext().getAuthentication().getName());
        }
    }

    public List<User> getAllStudents() {
        return userRepository.findAllByRole_Student();
    }

    public User signup(UserRequestDto authDto) throws Status436UserExistsException {
        if (userRepository.findByUsername(authDto.getUsername()) != null){
            throw new Status436UserExistsException(authDto.getUsername());
        }else {
            return userRepository.save(User.builder()
                    .firstName(authDto.getFirstName())
                    .lastName(authDto.getLastName())
                    .username(authDto.getUsername())
                    .password(passwordEncoder.encode(authDto.getPassword()))
                    .role(Role.STUDENT)
                    .build());
        }
    }
}
