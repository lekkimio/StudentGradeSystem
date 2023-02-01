package com.example.gradesys.repo;

import com.example.gradesys.model.Result;
import com.example.gradesys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findAllBySubject_Id(Long subjectId);

    @Query("select r from Result r where  r.subject.id = ?1 and r.user.id = ?2")
    Result findResultBySubjectAndUser(Long subjectId, Long userId);

    List<Result> findAllByUser_Id(Long userId);

    @Query("select r.grade from Result r where r.user = ?1 order by r.subject.id ")
    List<Double> getGradeByUser(User user);
}