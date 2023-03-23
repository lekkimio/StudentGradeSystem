package com.example.gradesys.security.jwt;

import com.example.gradesys.exception.Status450JwtException;
import com.example.gradesys.model.User;
import com.example.gradesys.security.CustomUserDetails;
import com.example.gradesys.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${secret.key}")
    private String secret;

    public CustomUserDetails getUserDetails(String token) {
        if (token.startsWith("Bearer")) {
            token = token.substring("Bearer ".length());
        }

        String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return userDetailsService.loadUserByUsername(username);

    }

    public String crateToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("roles", user.getRoles());
        Date now = new Date(System.currentTimeMillis());


        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 100*60*1000))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsername(String token) throws Status450JwtException {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return claimsJws.getBody().getSubject();
        }catch (Exception e){
            throw new Status450JwtException();
        }
    }


}
