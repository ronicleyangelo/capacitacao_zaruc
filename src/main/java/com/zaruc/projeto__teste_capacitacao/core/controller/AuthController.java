package com.zaruc.projeto__teste_capacitacao.core.controller;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.AuthDTO;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.service.RolesService;
import com.zaruc.projeto__teste_capacitacao.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<String> getUsers() {
        String users = "Welcome";
        return ResponseEntity.ok().body(users);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO authDTO) {
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/registrar-usuario", method = RequestMethod.POST)
    public ResponseEntity<?> registraUsuario(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        User user = userService.createUser(registerUserDTO);
        return  ResponseEntity.ok().body(user);
    }
}
