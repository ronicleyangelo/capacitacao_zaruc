package com.zaruc.projeto__teste_capacitacao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaruc.projeto__teste_capacitacao.core.controller.UserController;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.UsuarioDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.UsuarioRepository;
import com.zaruc.projeto__teste_capacitacao.core.service.UsuarioService;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    private MockMvc mockMvc;
    private String URL;
    private UsuarioDTO usuarioDTO;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String JSON;

    @BeforeEach
    void setup() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();
        URL = "/users";
        usuarioDTO = new UsuarioDTO("Maria", "samaritana");
        JSON = objectMapper.writeValueAsString(usuarioDTO);
    }

    @Test
    void listarUsuarios() throws Exception {
        when(usuarioService.getUsuarioDTOS()).thenReturn(Collections.singletonList(usuarioDTO));

        mockMvc.perform(get(URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(usuarioService).getUsuarioDTOS();
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void atualizarUsuario() throws Exception {
        Long id = 2L;
        doNothing().when(usuarioService).update(usuarioDTO, id);

        mockMvc.perform(put(URL + "/atualizar-usuario/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(usuarioService).update(usuarioDTO, id);
        verifyNoMoreInteractions(usuarioService);
    }

    @Test
    void casoIdSejaNull() throws Exception {

        mockMvc.perform(put(URL + "/atualizar-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verifyNoInteractions(usuarioService);
    }

    @Test
    void deletarUsuario() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete(URL + "/desativar-usuario/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        verify(usuarioService).delete(id);
        verifyNoMoreInteractions(usuarioService);
    }
    @Test
    void deletarUsuarioIsNull() throws Exception {
        mockMvc.perform(delete(URL + "/desativar-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verifyNoInteractions(usuarioService);
    }
}
