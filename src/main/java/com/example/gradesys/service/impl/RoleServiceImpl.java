package com.example.gradesys.service.impl;

import com.example.gradesys.model.ERole;
import com.example.gradesys.model.Role;
import com.example.gradesys.repo.RoleRepository;
import com.example.gradesys.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(ERole role) {
        log.info("Getting role by name: {}", role);
        return roleRepository.findByName(role);
    }

    public boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        log.info("Checking if user has admin role");
        return authorities.contains(new SimpleGrantedAuthority(ERole.ADMIN.name()));
    }

    public boolean isManager(Collection<? extends GrantedAuthority> authorities) {
        log.info("Checking if user has manager role");
        return authorities.contains(new SimpleGrantedAuthority(ERole.MANAGER.name()));
    }

    public boolean isStudent(Collection<? extends GrantedAuthority> authorities) {
        log.info("Checking if user has student role");
        return authorities.contains(new SimpleGrantedAuthority(ERole.STUDENT.name()));
    }

    public boolean isAdmin(Set<Role> roles) {
        log.info("Checking if user has admin role");
        return roles.contains(getRoleByName(ERole.ADMIN));
    }

    public boolean isManager(Set<Role> roles) {
        log.info("Checking if user has manager role");
        return roles.contains(getRoleByName(ERole.MANAGER));
    }

    public boolean isStudent(Set<Role> roles) {
        log.info("Checking if user has student role");
        return roles.contains(getRoleByName(ERole.STUDENT));
    }

}
