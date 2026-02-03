package com.example.todolistapi.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String email) {
        return Jwts.builder ()
                .setSubject (email)
                .setIssuedAt (new Date ())
                .setExpiration (new Date (System.currentTimeMillis () + 86400000))
                .signWith (SignatureAlgorithm.HS256, SECRET_KEY)
                .compact ();
    }

    public static String extractEmail (String token) {
        return Jwts.parser ()
                .setSigningKey (SECRET_KEY)
                .parseClaimsJws (token)
                .getBody ()
                .getSubject ();
    }
}
