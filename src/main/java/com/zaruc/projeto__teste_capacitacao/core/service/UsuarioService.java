package com.zaruc.projeto__teste_capacitacao.core.service;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService implements Serializable {

    UsuarioRepository usuarioRepository;

    public User findByUsuario(String usuario) {
       return usuarioRepository.findByUsuario(usuario);
    }
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void update(User user) {
        usuarioRepository.save(user);
    }
}
