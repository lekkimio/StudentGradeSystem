package com.example.gradesys.service;

import com.example.gradesys.exception.Status401Unauthorized;
import com.example.gradesys.exception.Status404SubjectNotFound;
import com.example.gradesys.model.Subject;
import com.example.gradesys.security.CustomUserDetails;

import java.util.List;

public interface SubjectService {

    Subject createSubject(String subjectName, CustomUserDetails userDetails) throws Status401Unauthorized;
    List<Subject> getAllSubjects();
    Subject getSubject(Long id) throws Status404SubjectNotFound;
    void deleteSubject(Long subjectId, CustomUserDetails userDetails) throws Status404SubjectNotFound, Status401Unauthorized;

}
