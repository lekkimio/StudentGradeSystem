package com.example.gradesys.repo;

import com.example.gradesys.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllBySubject_IdOrderByUser_Id(Long subjectId);

    Optional<Result> findResultBySubject_IdAndUser_Id(Long subjectId, Long userId);

    List<Result> findAllByUser_Id(Long userId);

    @Transactional
    void deleteAllByUser_Id(Long userId);

    @Transactional
    void deleteAllBySubject_Id(Long subjectId);


}