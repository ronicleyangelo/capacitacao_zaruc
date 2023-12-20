package com.zaruc.projeto__teste_capacitacao.core.security;

import com.zaruc.projeto__teste_capacitacao.core.repository.UserRespository;
import com.zaruc.projeto__teste_capacitacao.core.service.TokenService;
import com.zaruc.projeto__teste_capacitacao.core.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    @Autowired
    private TokenService tokenService;

    @Autowired
    UserRespository userRepository;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization ");
        String username = null;
        String token = null;

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
            try {
                username = tokenService.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                log.error("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                log.error("JWT Token has expired", e);
            }
        } else {
            log.warn("Bearer String not found in token");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (tokenService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String tokenHeader = request.getHeader("Authorization");
//        String username = null;
//        String token = null;
//        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
//            token = tokenHeader.substring(7);
//            try {
//                username = tokenService.getUsernameFromToken(token);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (ExpiredJwtException e) {
//                System.out.println("JWT Token has expired");
//            }
//        } else {
//            System.out.println("Bearer String not found in token");
//        }
//        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = userService.loadUserByUsername(username);
//            if (tokenService.validateToken(token, userDetails)) {
//                UsernamePasswordAuthenticationToken
//                        authenticationToken = new UsernamePasswordAuthenticationToken(
//                        userDetails, null,
//                        userDetails.getAuthorities());
//                authenticationToken.setDetails(new
//                        WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }

//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        var token = this.recoverToken(request);
//        if (token != null) {
//            try {
//                var subject = tokenService.tokenValido(token);
//                UserDetails user = userRepository.findByUsername(subject);
//
//                if (user != null) {
//                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            } catch (Exception e) {
//                // Log ou tratamento adequado para token inválido
//                logger.error("Erro ao processar token", e);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private String recoverToken(HttpServletRequest request) {
//        var authenticationHeader = request.getHeader("Authorization");
//
//        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
//            String token = authenticationHeader.replace("Bearer ", "");
//            System.out.println("Token recuperado: " + token);
//            return token;
//        }
//
//        return null;
//    }


//    private String recoverToken(HttpServletRequest request) {
//        var authetication = request.getHeader("Authorization");
//        if(Objects.isNull(authetication)) { return null; }
//        return authetication.replace("Bearer ", "");
//    }
}
