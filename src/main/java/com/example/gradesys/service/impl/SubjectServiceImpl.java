package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status435NoAuthorities;
import com.example.gradesys.exception.Status437SubjectNotFound;
import com.example.gradesys.model.Subject;
import com.example.gradesys.repo.ResultRepository;
import com.example.gradesys.repo.SubjectRepository;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.service.RoleService;
import com.example.gradesys.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final ResultRepository resultRepository;

    private final RoleService roleService;


    public Subject createSubject(String subjectName, CustomUserDetails userDetails) throws Status435NoAuthorities {

        if (roleService.isAdmin(userDetails.getAuthorities()) || roleService.isManager(userDetails.getAuthorities())) {
            return subjectRepository.save(Subject.builder().subjectName(subjectName).build());
        } else throw new Status435NoAuthorities("create subject");

    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Subject getSubject(Long id) throws Status437SubjectNotFound {
        return subjectRepository.findById(id).orElseThrow(() -> new Status437SubjectNotFound(id));
    }

    public void deleteSubject(Long subjectId, CustomUserDetails userDetails) throws Status437SubjectNotFound, Status435NoAuthorities {

        if (!roleService.isAdmin(userDetails.getAuthorities()) && !roleService.isManager(userDetails.getAuthorities()))
            throw new Status435NoAuthorities("delete subject");

            Subject subject = getSubject(subjectId);
            resultRepository.deleteAllBySubject_Id(subject.getId());
            subjectRepository.deleteById(subjectId);
            log.info("subject {} deleted by {} ", subject.getSubjectName(), userDetails.getUsername());


    }
}
