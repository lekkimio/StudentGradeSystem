package com.example.gradesys.controller;


import com.example.gradesys.model.Subject;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> createSubject(String subjectName, CustomUserDetails userDetails) {
        return ResponseEntity.ok(subjectService.createSubject(subjectName, userDetails));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Object> deleteSubject(@PathVariable Long subjectId, CustomUserDetails userDetails) {
        subjectService.deleteSubject(subjectId, userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects(){
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }


}
