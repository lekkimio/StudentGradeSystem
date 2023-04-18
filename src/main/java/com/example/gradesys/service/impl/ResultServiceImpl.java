package com.example.gradesys.service.impl;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.Manager;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.model.dto.UserInfoDto;
import com.example.gradesys.repo.ResultRepository;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.RoleService;
import com.example.gradesys.service.SubjectService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;

    private final SubjectService subjectService;
    private final UserService userService;
    private final RoleService roleService;

    public Result createResult(ResultDto resultDto) {

        return resultRepository.save(Result.builder()
                .subject(subjectService.getSubject(resultDto.getSubjectId()))
                .user(userService.getUserById(resultDto.getUserId()))
                .grade(resultDto.getGrade()).build());
    }

    public Result editResult(ResultDto resultDto, CustomUserDetails userDetails)  {

        if (roleService.isStudent(userDetails.getAuthorities())) throw new Status401UnauthorizedUser("edit result.");

        Manager manager = null;
        if (userService.existsByStudent_Id(resultDto.getUserId())) {
            manager = userService.getManagerByStudent(resultDto.getUserId());
        }

        if (!roleService.isAdmin(userDetails.getAuthorities()) &&
                (!roleService.isManager(userDetails.getAuthorities()) || !manager.getMentor().getId().equals(userDetails.getId())))
            throw new Status401UnauthorizedUser("edit result.");

    Optional<Result> result = resultRepository.findResultBySubject_IdAndUser_Id(resultDto.getSubjectId(), resultDto.getUserId());

    if (result.isPresent()){

        Result resultToUpdate = result.get();

        Double oldGrade = resultToUpdate.getGrade();
        resultToUpdate.setGrade(resultDto.getGrade());
        log.info("User {} changed {}'s results from {} to {}",
                userDetails.getUsername(), resultToUpdate.getUser().getUsername(), oldGrade, resultToUpdate.getGrade());

        return resultRepository.save(resultToUpdate);


    } else return createResult(resultDto);


    }


    public List<Result> getResultsBySubject(Long subjectId) {
        return resultRepository.findAllBySubject_IdOrderByUser_Id(subjectId);
    }

    public void deleteResult(Long resultId, CustomUserDetails userDetails)  {
        Result result = resultRepository.findById(resultId).orElseThrow(() -> new Status404ResultNotFound(resultId));
        Manager manager = userService.getManagerByStudent(result.getUser().getId());
        User mentor = manager.getMentor();

        if (!roleService.isAdmin(userDetails.getAuthorities())
                && (mentor == null || !userDetails.getId().equals(mentor.getId())))
            throw new Status401UnauthorizedUser("delete result");

        resultRepository.delete(result);
    }

    public void resetResultsByUser(Long userId, CustomUserDetails userDetails)  {
        List<Result> results = resultRepository.findAllByUser_Id(userId);
        for (Result result : results) {
            deleteResult(result.getId(), userDetails);
        }
    }

    public void resetResultsBySubject(Long subjectId, CustomUserDetails userDetails)  {
        List<Result> results = resultRepository.findAllBySubject_IdOrderByUser_Id(subjectId);
        for (Result result : results) {
            deleteResult(result.getId(), userDetails);
        }
    }

    public void resetAllResults(CustomUserDetails userDetails) {
        List<Result> results = resultRepository.findAll();
        for (Result result : results) {
            deleteResult(result.getId(), userDetails);
        }
    }

    public List<Double> getAllUserResultToEachSubject(Long userId) {

        List<Subject> subjects = subjectService.getAllSubjects();

        List<Double> grades = new ArrayList<>();

        for (Subject subject : subjects) {
            Optional<Result> result = resultRepository.findResultBySubject_IdAndUser_Id(subject.getId(), userId);
            if (result.isPresent()) {
                grades.add(result.get().getGrade());
            } else grades.add(0.0);
        }

        return grades;

    }

    public List<Result> getAllResults(CustomUserDetails userDetails) {
        if (roleService.isStudent(userDetails.getAuthorities())) {
            return resultRepository.findAllByUser_Id(userDetails.getId());
        }
        else return resultRepository.findAll();
    }

    public List<ResultInfo> getUserResult() {
        List<User> students = userService.getAllStudents();
        List<ResultInfo> resultInfoList = new ArrayList<>();

        for (User student : students) {
            resultInfoList.add(ResultInfo.builder()
                    .user(new UserInfoDto(student.getFirstName(), student.getLastName()))
                    .grades(getAllUserResultToEachSubject(student.getId())).build());
        }

        return resultInfoList;
    }


}
