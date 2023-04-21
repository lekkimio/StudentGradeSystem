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
        Result result = resultRepository.save(Result.builder()
                .subject(subjectService.getSubject(resultDto.getSubjectId()))
                .user(userService.getUserById(resultDto.getUserId()))
                .grade(resultDto.getGrade()).build());
        log.info("Result created: {}", result);
        return result;
    }

    public Result editResult(ResultDto resultDto, CustomUserDetails userDetails)  {

        log.info("Attempting to edit result for subject {} and user {}", resultDto.getSubjectId(), resultDto.getUserId());

        if (roleService.isStudent(userDetails.getAuthorities())) {
            log.warn("Unauthorized access to edit result by student {}", userDetails.getUsername());
            throw new Status401UnauthorizedUser("edit result.");
        }

        Manager manager = null;
        if (userService.existsByStudent_Id(resultDto.getUserId())) {
            manager = userService.getManagerByStudent(resultDto.getUserId());
        }

        if (!roleService.isAdmin(userDetails.getAuthorities()) &&
                (!roleService.isManager(userDetails.getAuthorities()) || !manager.getMentor().getId().equals(userDetails.getId()))) {
            log.warn("Unauthorized access to edit result by user {}", userDetails.getUsername());
            throw new Status401UnauthorizedUser("edit result.");
        }

        Optional<Result> result = resultRepository.findResultBySubject_IdAndUser_Id(resultDto.getSubjectId(), resultDto.getUserId());

        if (result.isPresent()){

            Result resultToUpdate = result.get();

            Double oldGrade = resultToUpdate.getGrade();
            resultToUpdate.setGrade(resultDto.getGrade());

            log.info("User {} changed {}'s results from {} to {}",
                    userDetails.getUsername(), resultToUpdate.getUser().getUsername(), oldGrade, resultToUpdate.getGrade());

            return resultRepository.save(resultToUpdate);

        } else {
            log.info("Creating new result for subject {} and user {}", resultDto.getSubjectId(), resultDto.getUserId());
            return createResult(resultDto);
        }
    }

    public List<Result> getResultsBySubject(Long subjectId) {
        log.info("Getting results for subject with id {}", subjectId);
        return resultRepository.findAllBySubject_IdOrderByUser_Id(subjectId);
    }

    public void deleteResult(Long resultId, CustomUserDetails userDetails)  {
        Result result = resultRepository.findById(resultId).orElseThrow(() -> new Status404ResultNotFound(resultId));
        Manager manager = userService.getManagerByStudent(result.getUser().getId());
        User mentor = manager.getMentor();

        if (!roleService.isAdmin(userDetails.getAuthorities())
                && (mentor == null || !userDetails.getId().equals(mentor.getId())))
            throw new Status401UnauthorizedUser("delete result");

        log.info("User {} deleted result with id {} for user {} in subject {}",
                userDetails.getUsername(), resultId, result.getUser().getUsername(), result.getSubject().getSubjectName());

        resultRepository.delete(result);
    }

    public void resetResultsByUser(Long userId, CustomUserDetails userDetails)  {
        List<Result> results = resultRepository.findAllByUser_Id(userId);

        for (Result result : results) {
            log.info("Deleting result with id {}", result.getId());
            deleteResult(result.getId(), userDetails);
        }

        log.info("Reset results for user with id {} complete", userId);
    }

    public void resetResultsBySubject(Long subjectId, CustomUserDetails userDetails)  {
        log.info("Resetting results for subject with {}", subjectId);
        List<Result> results = resultRepository.findAllBySubject_IdOrderByUser_Id(subjectId);

        for (Result result : results) {
            deleteResult(result.getId(), userDetails);
            log.info("Deleted result with id {} for user with id {} in subject with  {}", result.getId(), result.getUser().getId(), result.getSubject().getId());
        }
    }

    public void resetAllResults(CustomUserDetails userDetails) {
        List<Result> results = resultRepository.findAll();

        for (Result result : results) {
            log.info("Deleting result with ID {}", result.getId());
            deleteResult(result.getId(), userDetails);
        }
    }

    public List<Double> getAllUserResultToEachSubject(Long userId) {
        log.info("Getting all results for user with id {}", userId);

        List<Subject> subjects = subjectService.getAllSubjects();

        List<Double> grades = new ArrayList<>();

        for (Subject subject : subjects) {
            Optional<Result> result = resultRepository.findResultBySubject_IdAndUser_Id(subject.getId(), userId);
            if (result.isPresent()) {
                Double grade = result.get().getGrade();
                log.debug("Found result for user {} in subject {}: {}", userId, subject.getSubjectName(), grade);
                grades.add(grade);
            } else {
                log.debug("No result found for user {} in subject {}", userId, subject.getSubjectName());
                grades.add(0.0);
            }
        }

        log.info("Finished getting all results for user with id {}", userId);

        return grades;
    }

    public List<Result> getAllResults(CustomUserDetails userDetails) {
        if (roleService.isStudent(userDetails.getAuthorities())) {
            log.info("Retrieving all results for user with id: {}", userDetails.getId());
            return resultRepository.findAllByUser_Id(userDetails.getId());
        }
        else {
            log.info("Retrieving all results");
            return resultRepository.findAll();
        }
    }

    public List<ResultInfo> getUserResult() {
        List<User> students = userService.getAllStudents();
        List<ResultInfo> resultInfoList = new ArrayList<>();

        for (User student : students) {
            List<Double> grades = getAllUserResultToEachSubject(student.getId());
            log.info("Grades for student {} {}: {}", student.getFirstName(), student.getLastName(), grades);
            resultInfoList.add(ResultInfo.builder()
                    .user(new UserInfoDto(student.getFirstName(), student.getLastName()))
                    .grades(grades).build());
        }

        return resultInfoList;
    }
}
