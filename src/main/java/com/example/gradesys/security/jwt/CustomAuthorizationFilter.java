package com.example.gradesys.security.jwt;

import com.example.gradesys.exception.Status450JwtException;
import com.example.gradesys.security.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;

    public CustomAuthorizationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (request.getServletPath().equals("/auth/login")  || request.getServletPath().equals("/auth/token/refresh")){
                filterChain.doFilter(request,response);
            }else {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
                    try {
                    String token = authorizationHeader.substring("Bearer ".length());
                        validateToken(token);

                        String username = Jwts.parser().setSigningKey("somesecretstring").parseClaimsJws(token).getBody().getSubject();;
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request,response);
                    }catch (Exception exception){
                        log.error("Error logging in: {}", exception.getMessage());
                        response.setHeader("error", exception.getMessage());
                        response.setStatus(FORBIDDEN.value());
                        Map<String, String> error = new HashMap<>();
                        error.put("error_message", exception.getMessage());
                        response.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(response.getOutputStream(), error);
                    }
                }
                else {
                    filterChain.doFilter(request,response);
                }
            }
    }

    private boolean validateToken(String token) throws Status450JwtException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey("somesecretstring").parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e){
            throw new Status450JwtException();
        }
    }
}
