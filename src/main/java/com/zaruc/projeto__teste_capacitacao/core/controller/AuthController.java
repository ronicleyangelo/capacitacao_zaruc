package com.zaruc.projeto__teste_capacitacao.core.controller;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.AuthDTO;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.service.TokenService;
import com.zaruc.projeto__teste_capacitacao.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<String> getUsers() {
        String users = "Welcome";
        return ResponseEntity.ok().body(users);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO authDTO) throws Exception{

       try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getLogin(), authDTO.getSenha()));
       } catch (DisabledException e) {
           throw new Exception("USER_DISABLED", e);
       }  catch (BadCredentialsException e) {
           throw new Exception("INVALID_CREDENTIALS", e);
       }
       UserDetails userDetails = userDetailsService.loadUserByUsername(authDTO.getLogin());
       String jwtToken = tokenService.generateToken(userDetails);
       return ResponseEntity.ok(new JwtResponse(jwtToken));
    }

    @RequestMapping(value = "/registrar-usuario", method = RequestMethod.POST)
    public ResponseEntity<?> registraUsuario(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        User user = userService.createUser(registerUserDTO);
        return  ResponseEntity.ok().body(user);
    }
}
