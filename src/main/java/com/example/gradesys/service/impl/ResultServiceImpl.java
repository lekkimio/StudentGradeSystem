package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status438SubjectExistsException;
import com.example.gradesys.exception.Status439ResultNotFound;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.model.dto.UserResponseDto;
import com.example.gradesys.repo.ResultRepository;
import com.example.gradesys.repo.SubjectRepository;
import com.example.gradesys.repo.UserRepository;
import com.example.gradesys.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public void createResult(ResultDto resultDto) throws Status434UserNotFound, Status437SubjectNotFound {

        resultRepository.save(Result.builder()
//                .grade(resultDto.grade())
                .subject(subjectRepository.findById(resultDto.getSubjectId())
                        .orElseThrow(()->new Status437SubjectNotFound(resultDto.getSubjectId())))
                .user(userRepository.findById(resultDto.getUserId()).
                        orElseThrow( ()-> new Status434UserNotFound(resultDto.getUserId()) ))
                        .grade(resultDto.getGrade())
                .build());

    }

    public void editResult(ResultDto resultDto){

        Result result = resultRepository.findResultBySubjectAndUser(resultDto.getSubjectId(), resultDto.getUserId());

        if (result != null) {
            result.setGrade(resultDto.getGrade());
            resultRepository.save(result);
            // TODO: 07.02.2023 result edit log


        }
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public List<Result> getResultsBySubject (Long subjectId) {
        return resultRepository.findAllBySubject_Id(subjectId);
    }

    public void deleteResult(Long resultId) throws Status439ResultNotFound {
        Result result = resultRepository.findById(resultId).orElseThrow(()->new Status439ResultNotFound(resultId));
        resultRepository.delete(result);
    }

    public void resetResultByUser(Long userId) {
        List<Result> results = resultRepository.findAllByUser_Id(userId);
        for (Result result : results) {
            result.setGrade(0.0);
            resultRepository.save(result);
        }
    }

    public void resetResultBySubject(Long subjectId) {
        List<Result> results = resultRepository.findAllBySubject_Id(subjectId);
        for (Result result : results) {
            result.setGrade(0.0);
            resultRepository.save(result);
        }
    }

    public void resetAllResult() {
        List<Result> results = resultRepository.findAll();
        for (Result result : results) {
            result.setGrade(0.0);
            resultRepository.save(result);
        }
    }

    public void createResultForAllStudents(String subjectName) throws Status437SubjectNotFound, Status434UserNotFound, Status438SubjectExistsException {
        if (subjectRepository.findBySubjectName(subjectName) != null) {
            throw new Status438SubjectExistsException(subjectName);
        }else {
            List<User> students = userRepository.findAllByRole_Student();

            Subject newSubject = subjectRepository.save(Subject.builder().subjectName(subjectName).build());

            for (User student : students) {
                createResult(new ResultDto(student.getId(), newSubject.getId()));
            }
        }
    }


    public List<Result> getResultsByUser(Long userId) {
        return resultRepository.findAllByUser_Id(userId);
    }


    public void deleteAllUserResults(Long userId) {
        resultRepository.deleteAllByUser_Id(userId);
    }

    public List<Double> getAllGradesByUser(Long userId) {
        return resultRepository.getGradeByUser(userId);
    }

    public List<ResultInfo> getUserResult() {
        List<User> students = userRepository.findAllByRole_Student();

        List<ResultInfo> resultInfoList = new ArrayList<>();

        for (User student : students) {
            resultInfoList.add(ResultInfo.builder()
                    .user(new UserResponseDto(student.getFirstName(), student.getLastName()))
                    .grades(getAllGradesByUser(student.getId()))
                    .build());
        }

        return resultInfoList;
    }
}
