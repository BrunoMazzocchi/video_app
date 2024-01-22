package com.mazzocchi.video_app.security;

import com.mazzocchi.video_app.config.*;
import com.mazzocchi.video_app.user.service.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.security.*;
import java.util.*;

@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private final CustomUserDetailsService userDetailsService;



    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig, CustomUserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    public String generateAccessToken(String username) {
        return generateToken(username, jwtConfig.getJwtExpirationMs());
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtConfig.getJwtRefreshExpirationInDays());
    }

    private String generateToken(String username, int expirationDays) {
        // Expiration date is the current time + days
        Calendar calendar = Calendar.getInstance(); // gets the current date and time
        calendar.add(Calendar.DAY_OF_YEAR, expirationDays); // adds 30 days to the current date
        Date expirationDate = calendar.getTime(); // converts the calendar instance to a Date instance
        Claims claims = Jwts.claims();

        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getJwtSecret())
                .compact();
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getJwtSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getJwtSecret())
                    .parseClaimsJws(token)
                    .getBody();

            claims.getSubject();
            return claims.getSubject();
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer "
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtConfig.getJwtSecret()).parseClaimsJws(token);

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parserBuilder().setSigningKey(jwtConfig.getJwtSecret()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
