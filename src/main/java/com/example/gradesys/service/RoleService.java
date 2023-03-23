package com.example.gradesys.service;

import com.example.gradesys.model.ERole;
import com.example.gradesys.model.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

public interface RoleService {

    Role getRoleByName(ERole role);

    boolean isAdmin(Collection<? extends GrantedAuthority> authorities);

    boolean isManager(Collection<? extends GrantedAuthority> authorities);

    boolean isStudent(Collection<? extends GrantedAuthority> authorities);

    boolean isAdmin(Set<Role> roles);

    boolean isManager(Set<Role> roles);

    boolean isStudent(Set<Role> roles);
}
