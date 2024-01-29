package com.zaruc.projeto__teste_capacitacao.core.controller;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.UsuarioDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.UsuarioRepository;
import com.zaruc.projeto__teste_capacitacao.core.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UsuarioService usuarioService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/buscar-usuario", method = RequestMethod.GET)
    ResponseEntity<String> getUsuario(@PathVariable Long id) {
        String users = "Welcome";
        return ResponseEntity.ok().body(users);
    }
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<UsuarioDTO>> getUsuario() {
        List<UsuarioDTO> usuarioDTOList = usuarioService.getUsuarioDTOS();
        return ResponseEntity.ok(usuarioDTOList);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/desativar-usuario/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/atualizar-usuario/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@Valid @RequestBody UsuarioDTO user, @PathVariable Long id){
        usuarioService.update(user, id);
        return ResponseEntity.noContent().build();
    }
}
