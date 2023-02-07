package com.example.gradesys.security.jwt;

import com.example.gradesys.model.User;
import com.example.gradesys.repo.UserRepository;
import com.example.gradesys.security.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.net.httpserver.Headers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.IOException;
import java.util.Date;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {}", username );
        log.info("Password is: {}", password );
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException {

                   UserDetails principal =
                           (UserDetails) authentication.getPrincipal();
           User user = userRepository.findByUsername(principal.getUsername());


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRole().name());

        Date now = new Date(System.currentTimeMillis());
        String access_token = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 100*60*1000))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, "somesecretstring")
                .compact();
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.AUTHORIZATION,"Bearer " + access_token);
        response.encodeRedirectURL("/home");
        new ObjectMapper().writeValue(response.getOutputStream(), access_token);


    }
}
