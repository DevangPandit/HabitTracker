package com.ht.HabitTracker.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "VGhpcyBpcyBhIHNlY3JldCBrZXkgY3JlYXRlZCBmb3IgSlM";
    public String generateToken(String username){
        return Jwts.builder().
                setSubject(username).
                setIssuedAt(new Date()).
                setExpiration(new Date(System.currentTimeMillis()+1000*60*60)).
                signWith(SignatureAlgorithm.HS256, SECRET_KEY).
                compact();
    }

    public boolean validateToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

        public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}

