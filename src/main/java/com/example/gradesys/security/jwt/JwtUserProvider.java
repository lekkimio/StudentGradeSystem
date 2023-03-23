package com.example.gradesys.security.jwt;

import com.example.gradesys.model.Role;
import com.example.gradesys.model.User;
import com.example.gradesys.security.CustomUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserProvider {

    public static CustomUserDetails create(User user){
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> userRoles){
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

}
