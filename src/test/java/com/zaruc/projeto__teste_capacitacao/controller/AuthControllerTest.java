package com.zaruc.projeto__teste_capacitacao.controller;

import com.zaruc.projeto__teste_capacitacao.core.controller.AuthController;
import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.RolesRespository;
import com.zaruc.projeto__teste_capacitacao.core.repository.UserRespository;
import com.zaruc.projeto__teste_capacitacao.core.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.management.relation.Role;
import org.junit.jupiter.api.Assertions;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
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
    @Test
    void salvarUsuario() {
        RegisterUserDTO registerUserDTO = new RegisterUserDTO("maria", "maria_sousa", 1L, "maria1230");
        User user = new User();
        user.setId(1L);
        user.setUsername(registerUserDTO.getUsername());
        user.setLogin(registerUserDTO.getLogin());
        user.setSenha(registerUserDTO.getSenha());

        when(userService.createUser(registerUserDTO)).thenReturn(user);
        when(rolesRespository.findByNome("ROLE_USER")).thenReturn(Optional.empty());
        when(rolesRespository.save(any(Roles.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = authController.registraUsuario(registerUserDTO);

        Assertions.assertEquals(user,response.getBody());
    }

}
