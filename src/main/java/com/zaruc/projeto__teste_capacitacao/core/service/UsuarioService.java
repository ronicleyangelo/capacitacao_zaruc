package com.zaruc.projeto__teste_capacitacao.core.service;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.UsuarioDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements Serializable {

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> getUsuarioDTOS() {
        List<User> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> new UsuarioDTO(usuario.getUsername(), usuario.getLogin()))
                .collect(Collectors.toList());
        return usuariosDTO;
    }
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void update(UsuarioDTO user, Long id) {
        if(usuarioRepository.existsById(id)) {
            User usuarioExistente = usuarioRepository.findById(id).get();

            usuarioExistente.setUsername(user.getUsername());
            usuarioExistente.setLogin(user.getLogin());

            usuarioRepository.save(usuarioExistente);
        }
    }
}
