package com.example.gradesys.service.impl;

import com.example.gradesys.model.ERole;
import com.example.gradesys.model.Role;
import com.example.gradesys.repo.RoleRepository;
import com.example.gradesys.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(ERole role) {
        return roleRepository.findByName(role) ;
    }

    public boolean isAdmin(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority(ERole.ADMIN.name()));
    }

    public boolean isManager(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority(ERole.MANAGER.name()));
    }

    public boolean isStudent(Collection<? extends GrantedAuthority> authorities) {
        return authorities.contains(new SimpleGrantedAuthority(ERole.STUDENT.name()));
    }

    public boolean isAdmin(Set<Role> roles) {
        return roles.contains(getRoleByName(ERole.ADMIN));
    }

    public boolean isManager(Set<Role> roles) {
        return roles.contains(getRoleByName(ERole.MANAGER));
    }

    public boolean isStudent(Set<Role> roles) {
        return roles.contains(getRoleByName(ERole.STUDENT));
    }
}
