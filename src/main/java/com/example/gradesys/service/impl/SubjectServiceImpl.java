package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Result;
import com.example.gradesys.model.Subject;
import com.example.gradesys.model.User;
import com.example.gradesys.repo.ResultRepository;
import com.example.gradesys.repo.SubjectRepository;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ResultRepository resultRepository;


    public Subject createSubject(String subjectName) {
        return subjectRepository.save(Subject.builder().subjectName(subjectName).build());
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id) throws Status437SubjectNotFound {
        return subjectRepository.findById(id).orElseThrow(()->new Status437SubjectNotFound(id));
    }

    public void deleteSubject(Long subjectId) throws Status437SubjectNotFound {

        Subject subject = getSubject(subjectId);
        if (subject != null) {
            resultRepository.deleteAllBySubject_Id(subject.getId());
            subjectRepository.deleteById(subjectId);
            log.info("subject {} deleted by {} ", subject.getSubjectName(),
                    SecurityContextHolder.getContext().getAuthentication().getName());
        }


    }
}
