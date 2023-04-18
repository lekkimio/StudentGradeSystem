package com.example.gradesys.service;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.security.CustomUserDetails;

import java.util.List;

public interface ResultService {

    Result createResult(ResultDto resultDto) throws Status404UserNotFound, Status404SubjectNotFound;

    Result editResult(ResultDto resultDto, CustomUserDetails userDetails) throws Status401Unauthorized, Status404SubjectNotFound, Status404UserNotFound, Status404ManagerNotFound;

    List<Result> getAllResults(CustomUserDetails userDetails);

    List<Result> getResultsBySubject(Long subjectId);

    void deleteResult(Long resultId, CustomUserDetails userDetails) throws Status404ResultNotFound, Status401Unauthorized, Status404ManagerNotFound;

    void resetResultsByUser(Long userId, CustomUserDetails userDetails) throws Status404ResultNotFound, Status401Unauthorized, Status404ManagerNotFound;

    void resetResultsBySubject(Long subjectId, CustomUserDetails userDetails) throws Status404ResultNotFound, Status401Unauthorized, Status404ManagerNotFound;

    void resetAllResults(CustomUserDetails userDetails) throws Status404ResultNotFound, Status401Unauthorized, Status404ManagerNotFound;

    List<Double> getAllUserResultToEachSubject(Long userId);

    List<ResultInfo> getUserResult();

}
