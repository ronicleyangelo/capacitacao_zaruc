package com.zaruc.projeto__teste_capacitacao.core.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService implements Serializable {
    private static final long serialVersionUID = 7008375124389347049L;

    public static final long TOKEN_VALIDO = 10 * 60 * 60;
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    @Value("${api.security.toke.secret}")
    private String secreto;

    @Autowired
    UserService userService;

    public String generateToken(UserDetails subject) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", subject.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder().setClaims(claims).setSubject(subject.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDO * 1000))
                .signWith(SignatureAlgorithm.HS512, secreto).compact();
    }

//    public String generateToken(UserDetails userDetails) {
//        UserDetails user =  userDetails;
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
//        return JWT.create().withSubject(userDetails.getUsername())
//                .withIssuedAt(new Date(System.currentTimeMillis()))
//                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDO * 1000))
//                .withClaim("userDetails", claims)
//                .sign(Algorithm.HMAC256(secreto));
//    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secreto).parseClaimsJws(token).getBody();
            String username = claims.getSubject();

            // Verifique se o usuário do token é o mesmo que o usuário fornecido pelo UserDetails
            Boolean isUsernameValid = username.equals(userDetails.getUsername());

            // Verifique se o token expirou
            Boolean isTokenExpired = claims.getExpiration().before(new Date(System.currentTimeMillis()));

            return isUsernameValid && !isTokenExpired;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Se houver qualquer exceção durante a validação, considere o token inválido
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Para obter as claims do token JWT
    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secreto).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            // Ou registre a exceção em um log
            throw new RuntimeException("Erro ao obter as claims do token", e);
        }
    }
    // Para verificar se o token JWT está expirado
    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date(System.currentTimeMillis()));
    }

    // Retorna a expiração do token JWT
    public Date getExpirationDateFromToken(String token) {
        return (Date) getClaimFromToken(token, Claims::getExpiration);
    }
}
