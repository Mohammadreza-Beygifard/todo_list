package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
// import java.security.KeyPair;
import java.util.Date;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator {
  // private static final KeyPair keyPair =
  // Keys.keyPairFor(SignatureAlgorithm.RS256);
  private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public String generateToken(UserDetails userDetail) {
    String username = userDetail.getUsername();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + SecurityConstants.EXPIRATION_TIME);

    String token =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact();
    System.out.println("New token :");
    System.out.println(token);
    return token;
  }

  public String getUsernameFromJWT(String token) {
    Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      throw new AuthenticationCredentialsNotFoundException(
          "JWT was expired or incorrect", ex.fillInStackTrace());
    }
  }
}
