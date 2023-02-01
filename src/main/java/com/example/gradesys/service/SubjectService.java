package com.example.gradesys.service;

import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Subject;
import com.example.gradesys.repo.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;


    public Subject createSubject(String subjectName) {
        return subjectRepository.save(Subject.builder().subjectName(subjectName).build());
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id) throws Status437SubjectNotFound {
        return subjectRepository.findById(id).orElseThrow(()->new Status437SubjectNotFound(id));
    }

    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
