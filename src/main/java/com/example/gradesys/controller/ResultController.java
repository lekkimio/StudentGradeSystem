package com.example.gradesys.controller;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultResponseDto;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<ResultResponseDto> getAllResults(CustomUserDetails userDetails){
        return modelMapper.map(resultService.getAllResults(userDetails), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }

    @GetMapping("/{subjectId}")
    public List<ResultResponseDto> getResultsBySubject(@PathVariable Long subjectId){
        return modelMapper.map(resultService.getResultsBySubject(subjectId), new TypeToken<List<ResultResponseDto>>(){}.getType());
    }


    @PostMapping("/edit")
    public ResultResponseDto editResult(ResultDto resultDto, CustomUserDetails userDetails) throws Status435NoAuthorities, Status437SubjectNotFound, Status434UserNotFound, Status440ManagerNotFound {
        return modelMapper.map(resultService.editResult(resultDto, userDetails), ResultResponseDto.class);
    }

    @DeleteMapping("/{resultId}")
    public void deleteResult(@PathVariable Long resultId, CustomUserDetails userDetails) throws Status439ResultNotFound, Status435NoAuthorities, Status440ManagerNotFound {
        resultService.deleteResult(resultId, userDetails);
    }

    @DeleteMapping("/reset-all")
    public void resetAllResults(CustomUserDetails userDetails) throws Status439ResultNotFound, Status435NoAuthorities, Status440ManagerNotFound {
        resultService.resetAllResults(userDetails);
    }

    @DeleteMapping("/user/{userId}")
    public void resetResultByUser(@PathVariable Long userId, CustomUserDetails userDetails) throws Status439ResultNotFound, Status435NoAuthorities, Status440ManagerNotFound {
        resultService.resetResultsByUser(userId, userDetails);
    }

    @DeleteMapping("/subject/{subjectId}")
    public void resetResultBySubject(@PathVariable Long subjectId, CustomUserDetails userDetails) throws Status439ResultNotFound, Status435NoAuthorities, Status440ManagerNotFound {
        resultService.resetResultsBySubject(subjectId, userDetails);
    }

}
