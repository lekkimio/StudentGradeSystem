package com.example.gradesys.controller;


import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Subject;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectController {

    private final SubjectService subjectService;


    @PostMapping
    public Subject createSubject(String subjectName, CustomUserDetails userDetails) throws Status435NoAuthorities {
        return subjectService.createSubject(subjectName, userDetails);
    }

    @DeleteMapping("/{subjectId}")
    public void deleteSubject(@PathVariable Long subjectId, CustomUserDetails userDetails) throws Status437SubjectNotFound, Status435NoAuthorities {
        subjectService.deleteSubject(subjectId, userDetails);
    }

    @GetMapping
    public List<Subject> getAllSubjects(){
        return subjectService.getAllSubjects();
    }


}
