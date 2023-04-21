package com.example.gradesys.service.impl;

import com.example.gradesys.exception.Status401UnauthorizedUser;
import com.example.gradesys.exception.Status404SubjectNotFound;
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


    public Subject createSubject(String subjectName, CustomUserDetails userDetails) {

        if (!roleService.isAdmin(userDetails.getAuthorities())
                && !roleService.isManager(userDetails.getAuthorities())) throw new Status401UnauthorizedUser("create subject");

            Subject subject = subjectRepository.save(Subject.builder().subjectName(subjectName).build());
            log.info("Subject {} created by {}", subject.getSubjectName(), userDetails.getUsername());
            return subject;

    }

    public List<Subject> getAllSubjects() {

        List<Subject> subjects = subjectRepository.findAll();
        log.info("All subjects fetched successfully.");

        return subjects;
    }

    public Subject getSubject(Long id) {

        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new Status404SubjectNotFound(id));
        log.info("Subject {} fetched successfully.", subject.getSubjectName());

        return subject;
    }

    public void deleteSubject(Long subjectId, CustomUserDetails userDetails) {

        if (!roleService.isAdmin(userDetails.getAuthorities()) && !roleService.isManager(userDetails.getAuthorities())) {
            throw new Status401UnauthorizedUser("delete subject");
        }

        Subject subject = getSubject(subjectId);
        resultRepository.deleteAllBySubject_Id(subject.getId());
        subjectRepository.deleteById(subjectId);
        log.info("Subject {} deleted by {} ", subject.getSubjectName(), userDetails.getUsername());
    }

}
