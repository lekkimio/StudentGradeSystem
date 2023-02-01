package com.example.gradesys.service;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status438SubjectExistsException;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.repo.ResultRepository;
import com.example.gradesys.repo.SubjectRepository;
import com.example.gradesys.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public void createResult(ResultDto resultDto) throws Status434UserNotFound, Status437SubjectNotFound {

        Result result = resultRepository.findResultBySubjectAndUser(resultDto.subjectId(), resultDto.userId());

        if (result != null) {
            result.setGrade(resultDto.grade());
            resultRepository.save(result);

        }else {

        resultRepository.save(Result.builder()
                .grade(resultDto.grade())
                .subject(subjectRepository.findById(resultDto.subjectId())
                        .orElseThrow(()->new Status437SubjectNotFound(resultDto.subjectId())))
                .user(userRepository.findById(resultDto.userId()).
                        orElseThrow( ()-> new Status434UserNotFound(resultDto.userId()) ))
                .build());

        }
    }

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public List<Result> getResultsBySubject (Long subjectId) {
        return resultRepository.findAllBySubject_Id(subjectId);
    }

    public void deleteResult(Long resultId) {
        resultRepository.deleteById(resultId);
    }

    public void createResultForAll(String subjectName) throws Status437SubjectNotFound, Status434UserNotFound, Status438SubjectExistsException {
        if (subjectRepository.findBySubjectName(subjectName) != null) {
            throw new Status438SubjectExistsException(subjectName);
        }else {
            List<User> students = userRepository.findAllByRole_Student();

            Subject newSubject = subjectRepository.save(Subject.builder().subjectName(subjectName).build());

            for (User student : students) {
                createResult(new ResultDto(student.getId(), newSubject.getId(), 0.0));
            }
        }
    }

    public List<Result> getResultsByUser(Long userId) {
         return resultRepository.findAllByUser_Id(userId);
    }

    public List<Double> getAllGradesByUser(User student) {
        return resultRepository.getGradeByUser(student);
    }
}
