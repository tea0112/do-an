package com.thai.doan.security.jwt;

import com.thai.doan.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtils {
  private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private final long JWT_EXPIRATION = 604800000L;

  public String generateJwt(CustomUserDetails customUserDetails) {
    return Jwts.builder()
        .setSubject(customUserDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 60000 * 60 * 24))
        .signWith(key)
        .compact();
  }

  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public boolean verify(String jwt) {
    try {
      Jwts.parserBuilder()
          .setSigningKey(key)
          .build()
          .parseClaimsJws(jwt);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
