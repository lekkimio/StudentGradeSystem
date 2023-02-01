package com.example.gradesys.security.jwt;

import com.example.gradesys.model.Role;
import com.example.gradesys.model.User;
import com.example.gradesys.security.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class JwtUser {

    public static UserDetails create(User user){
        return new UserDetails(user.getId(), user.getUsername(), user.getPassword(), mapToGrantedAuthorities(user.getRole())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRoles){
        return Collections.singletonList(new SimpleGrantedAuthority(userRoles.name()));
    }

}
