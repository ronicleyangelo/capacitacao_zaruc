package com.zaruc.projeto__teste_capacitacao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaruc.projeto__teste_capacitacao.core.controller.AuthController;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.RolesRespository;
import com.zaruc.projeto__teste_capacitacao.core.repository.UserRespository;
import com.zaruc.projeto__teste_capacitacao.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import javax.management.relation.Role;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private RolesRespository rolesRespository;

    @Mock
    private UserRespository userRespository;
    private RegisterUserDTO registerUserDTO;
    private Role role;
    private MockMvc mockMvc;
    private String url;
    private String json;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup() throws JsonProcessingException {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).alwaysDo(print()).build();
        url = "/auth";
        registerUserDTO = new RegisterUserDTO("maria", "maria_sousa", 1L, "maria1230");
        json = objectMapper.writeValueAsString(registerUserDTO);
    }

    @Test
    void salvarUsuario() throws Exception {

        mockMvc.perform(post(url + "/registrar-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(userService).createUser(registerUserDTO);
        verifyNoMoreInteractions(userService);
    }

    @Test
    void naoDeveEnviarCasoSejaNull() throws Exception {
        when(userService.createUser(any(RegisterUserDTO.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST));
        mockMvc.perform(post(url + "/registrar-usuario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
        verify(userService).createUser(any(RegisterUserDTO.class));
    }

}
