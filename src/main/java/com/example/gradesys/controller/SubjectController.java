package com.example.gradesys.controller;


import com.example.gradesys.model.Subject;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//TODO rename to 'subjects'
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> createSubject(String subjectName, CustomUserDetails userDetails) {
        return ResponseEntity.ok(subjectService.createSubject(subjectName, userDetails));
    }

    @DeleteMapping("/{subjectId}")
    public ResponseEntity<Object> deleteSubject(@PathVariable Long subjectId, CustomUserDetails userDetails) {
        subjectService.deleteSubject(subjectId, userDetails);
    }

    @GetMapping
    public List<Subject> getAllSubjects(){
        return subjectService.getAllSubjects();
    }


}
