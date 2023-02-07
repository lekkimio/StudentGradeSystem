package com.example.gradesys.controller;

import com.example.gradesys.exception.Status434UserNotFound;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.exception.Status438SubjectExistsException;
import com.example.gradesys.exception.Status439ResultNotFound;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultInfo;
import com.example.gradesys.model.dto.ResultResponseDto;
import com.example.gradesys.service.ResultService;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

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
    public List<ResultResponseDto> getResultsBySubject(@PathVariable Long subjectId){
        return modelMapper.map(resultService.getResultsBySubject(subjectId), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }

    @GetMapping("/user/result/")
    public List<ResultInfo> getUserResult(){
        return modelMapper.map(resultService.getUserResult(), new TypeToken<List<ResultInfo>>(){}.getType());
    }

    @PostMapping("/create-subject")
    public void createSubject(String subjectName) throws Status437SubjectNotFound, Status434UserNotFound, Status438SubjectExistsException {
        resultService.createResultForAllStudents(subjectName);
    }

    @DeleteMapping("/delete-subject/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId) throws Status437SubjectNotFound {
        subjectService.deleteSubject(subjectId);
    }

    @PostMapping("/edit")
    public void editResult(ResultDto resultDto){
        resultService.editResult(resultDto);
    }

    @PostMapping("/delete-result/{resultId}")
    public void deleteResult(@PathVariable Long resultId) throws Status439ResultNotFound {
        resultService.deleteResult(resultId);
    }

}
