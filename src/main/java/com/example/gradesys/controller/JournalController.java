package com.example.gradesys.controller;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status438SubjectExistsException;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.model.dto.ResultResponseDto;
import com.example.gradesys.model.dto.UserResponseDto;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.SubjectService;
import com.example.gradesys.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {

    private final ResultService resultService;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;


    @GetMapping("/subject")
    public List<Subject> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GetMapping("/subject/{id}")
    public String getSubject(@PathVariable Long id) throws Status437SubjectNotFound {
        return subjectService.getSubject(id).getSubjectName();
    }

    @GetMapping("/result")
    public List<ResultResponseDto> getAllResults(){
        return modelMapper.map(resultService.getAllResults(), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }

    @GetMapping("/result/{subjectId}")
    public List<Result> getResultsBySubject(@PathVariable Long subjectId){
        return modelMapper.map(resultService.getResultsBySubject(subjectId), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }

    @GetMapping("/user/result/{userId}")
    public List<Result> getResultsByUser(@PathVariable Long userId){
        return modelMapper.map(resultService.getResultsByUser(userId), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }

    @PostMapping("/create-subject")
    public void createSubject(String subjectName) throws Status437SubjectNotFound, Status434UserNotFound, Status438SubjectExistsException {
        resultService.createResultForAll(subjectName); ;
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId){
        subjectService.deleteSubject(subjectId);
    }

    @PostMapping("/create-result")
    public void createResult(ResultDto resultDto) throws Status434UserNotFound, Status437SubjectNotFound {
        resultService.createResult(resultDto);
    }

    @PostMapping("/delete-result/{resultId}")
    public void deleteResult(@PathVariable Long resultId){
        resultService.deleteResult(resultId);
    }

}
