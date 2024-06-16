package com.olisaude.testedevback.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.olisaude.testedevback.model.UserModel;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String generateToken(UserModel user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      String token = JWT
        .create()
        .withIssuer("OliHealth")
        .withSubject(user.getLogin())
        .withExpiresAt(genExpirationDate())
        .sign(algorithm);
      return token;
    } catch (Exception e) {
      throw new RuntimeException("Error generating token!");
    }
  }

  public String validateToken(String token) {
    try {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("OliHealth")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getSubject();
    } catch (JWTVerificationException e) {
        return null;
    }
}

  private Instant genExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  public String getLoginFromToken(String token) {
    throw new UnsupportedOperationException(
      "Unimplemented method 'getLoginFromToken'"
    );
  }
}
