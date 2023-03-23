package com.example.gradesys.repo;

import com.example.gradesys.model.ERole;
import com.example.gradesys.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(ERole name);

}