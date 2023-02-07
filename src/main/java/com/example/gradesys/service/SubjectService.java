package com.example.gradesys.service;

import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Subject;

import java.util.List;

public interface SubjectService {

    Subject createSubject(String subjectName);

    List<Subject> getAllSubjects();

    Subject getSubject(Long id) throws Status437SubjectNotFound;
    void deleteSubject(Long subjectId) throws Status437SubjectNotFound;

}
