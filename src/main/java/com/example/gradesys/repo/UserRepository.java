package com.example.gradesys.repo;

import com.example.gradesys.model.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<com.example.gradesys.model.User, Long> {
    User findByUsername(String username);

    @Query(value = "select u from User u where u.role='STUDENT' ")
    List<User> findAllByRole_Student();
}