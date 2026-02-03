package com.example.workouttracker.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private String jwtSecret = "K9f3nQ8pL2vW7tXzRm4uE6sH1bG0yPK9f3nQ8pL2vW7tXzRm4uE6sH1bG0yPK9f3nQ8pL2vW7tXzRm4uE6sH1bG0yP";
    private int jwtExpirationMs = 86400000;

    public String generateJwtToken( String username) {
        return Jwts.builder ()
                .setSubject (username)
                .setIssuedAt (new Date ())
                .setExpiration (new Date ((new Date ()).getTime () + this.jwtExpirationMs))
                .signWith (SignatureAlgorithm.HS512, this.jwtSecret)
                .compact ();
    }

    public String getUserNameFromJwtToken (String token) {
        return Jwts.parser ()
                .setSigningKey (this.jwtSecret)
                .parseClaimsJws( token)
                .getBody ()
                .getSubject ();
    }

    public boolean validateJwtToken (String authToken) {
        try {
            Jwts.parser ().setSigningKey (this.jwtSecret).parseClaimsJws (authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}