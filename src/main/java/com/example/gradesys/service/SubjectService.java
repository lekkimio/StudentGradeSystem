package com.example.gradesys.service;

import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Subject;
import com.example.gradesys.security.CustomUserDetails;

import java.util.List;

public interface SubjectService {

    Subject createSubject(String subjectName, CustomUserDetails userDetails) throws Status435NoAuthorities;
    List<Subject> getAllSubjects();
    Subject getSubject(Long id) throws Status437SubjectNotFound;
    void deleteSubject(Long subjectId, CustomUserDetails userDetails) throws Status437SubjectNotFound, Status435NoAuthorities;

}
