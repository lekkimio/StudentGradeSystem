package com.example.gradesys.repo;

import com.example.gradesys.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager, Long> {


    Manager getByStudent_Id(Long studentId);


    List<Manager> findAllByMentor_Id(Long mentorId);


    boolean existsByStudent_Id(Long studentId);



}