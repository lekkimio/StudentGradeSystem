package com.example.gradesys.repo;

import com.example.gradesys.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    Subject findBySubjectName(String subjectName);
}