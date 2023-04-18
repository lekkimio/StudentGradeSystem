package com.example.gradesys.controller;

import com.example.gradesys.exception.*;
import com.example.gradesys.model.dto.ResultDto;
import com.example.gradesys.model.dto.ResultResponseDto;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//TODO rename to 'results'
@RequestMapping("/result")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ResultResponseDto>> getAllResults(CustomUserDetails userDetails){
        return ResponseEntity.ok(modelMapper.map(resultService.getAllResults(userDetails), new TypeToken<List<ResultResponseDto>>(){}.getType()));
    }

    @GetMapping("/{subjectId}")
    public ResponseEntity<List<ResultResponseDto>> getResultsBySubject(@PathVariable Long subjectId){
        return ResponseEntity.ok(modelMapper.map(resultService.getResultsBySubject(subjectId), new TypeToken<List<ResultResponseDto>>(){}.getType()));
    }
    @PatchMapping("/edit")
    public ResponseEntity<ResultResponseDto> editResult(ResultDto resultDto, CustomUserDetails userDetails) {
        return ResponseEntity.ok(modelMapper.map(resultService.editResult(resultDto, userDetails), ResultResponseDto.class));
    }

    @DeleteMapping("/{resultId}")
    public ResponseEntity<Object> deleteResult(@PathVariable Long resultId, CustomUserDetails userDetails){
        resultService.deleteResult(resultId, userDetails);
    }
    @DeleteMapping
    public ResponseEntity<Object> resetAllResults(CustomUserDetails userDetails) {
        resultService.resetAllResults(userDetails);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Object> resetResultByUser(@PathVariable Long userId, CustomUserDetails userDetails) {
        resultService.resetResultsByUser(userId, userDetails);
    }

    @DeleteMapping("/subject/{subjectId}")
    public ResponseEntity<Object> resetResultBySubject(@PathVariable Long subjectId, CustomUserDetails userDetails){
        resultService.resetResultsBySubject(subjectId, userDetails);
    }

}
