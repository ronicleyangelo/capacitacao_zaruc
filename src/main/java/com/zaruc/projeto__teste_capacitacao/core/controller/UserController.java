package com.zaruc.projeto__teste_capacitacao.core.controller;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.UsuarioDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.UsuarioRepository;
import com.zaruc.projeto__teste_capacitacao.core.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;
    @RequestMapping(value = "/buscar-usuario", method = RequestMethod.GET)
    ResponseEntity<String> getUsuario(@PathVariable Long id) {
        String users = "Welcome";
        return ResponseEntity.ok().body(users);
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<List<UsuarioDTO>> getUsuario() {
        List<User> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getUsername(), usuario.getLogin()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @RequestMapping(value = "/desativar-usuario/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @RequestMapping(value = "/atualizar-usuario/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> update(@Valid @RequestBody User user, @PathVariable Long id){
        if(usuarioRepository.existsById(id)) {
            User usuarioExistente = usuarioRepository.findById(id).get();

            // Atualiza os campos necessários
            usuarioExistente.setUsername(user.getUsername());
            usuarioExistente.setLogin(user.getLogin());

            // Salva as alterações no banco de dados
            usuarioRepository.save(usuarioExistente);
        }
        return ResponseEntity.noContent().build();
    }
}
