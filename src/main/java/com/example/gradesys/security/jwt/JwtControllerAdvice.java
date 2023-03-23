package com.example.gradesys.security.jwt;

import com.example.gradesys.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class JwtControllerAdvice {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;



    @ModelAttribute
    public CustomUserDetails getJwtUser(Authentication authentication, WebRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) return jwtTokenProvider.getUserDetails(token) ;
        else if (authentication == null) {
            return null;
        }else {
            return (CustomUserDetails) authentication.getPrincipal();
        }
    }


}
