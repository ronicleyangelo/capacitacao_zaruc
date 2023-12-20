package com.zaruc.projeto__teste_capacitacao.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService implements Serializable {
    private static final long serialVersionUID = 7008375124389347049L;

    public static final long TOKEN_VALIDO = 10 * 60 * 60;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Value("${api.security.toke.secret}")
    private String secreto;

    @Autowired
    UserService userService;

    public String generateToken(UserDetails userDetails) {
        return JWT.create().withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDO * 1000))
                .sign(Algorithm.HMAC256(secreto));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        Claims claims = (Claims) JWT.create().withPayload(secreto).withPayload(token);
        Boolean isTokenExpired = claims.getExpiration().before(new java.util.Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = (Claims) JWT.create().withPayload(secreto).withPayload(token);
        return claims.getSubject();
    }

//    public String gerarToken(User userName) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secreto);
//            String token = JWT.create()
//                    .withIssuer("auth-api")
//                    .withSubject(userName.getLogin())
//                    .withExpiresAt(tokenTempo())
//                    .sign(algorithm);
//            return token;
//        } catch (JWTCreationException e) {
//            throw new RuntimeException("Error ao gera o token", e);
//        }
//    }
//
//    public String tokenValido(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(secreto);
//            return JWT.require(algorithm)
//                    .withIssuer("auth-api")
//                    .build()
//                    .verify(token)
//                    .getSubject();
//        } catch(JWTCreationException e) {
//            throw  new RuntimeException("Error token inválido");
//        }
//    }

    private Instant tokenTempo() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
