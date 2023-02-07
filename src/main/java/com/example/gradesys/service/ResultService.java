package com.example.gradesys.service;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status438SubjectExistsException;
import com.example.gradesys.exception.Status439ResultNotFound;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface ResultService {

    void createResult(ResultDto resultDto) throws Status434UserNotFound, Status437SubjectNotFound;

    void editResult(ResultDto resultDto);

    List<Result> getAllResults();

    List<Result> getResultsBySubject(Long subjectId);

    void deleteResult(Long resultId) throws Status439ResultNotFound;
    void resetResultByUser(Long userId);

    void resetResultBySubject(Long subjectId);

    void resetAllResult();

    void createResultForAllStudents(String subjectName) throws Status437SubjectNotFound, Status434UserNotFound, Status438SubjectExistsException;

    List<Result> getResultsByUser(Long userId);

    void deleteAllUserResults(Long userId);

    List<Double> getAllGradesByUser(Long userId);

    List<ResultInfo> getUserResult();
}
