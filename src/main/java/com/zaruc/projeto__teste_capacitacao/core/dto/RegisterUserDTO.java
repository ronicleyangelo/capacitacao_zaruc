package com.zaruc.projeto__teste_capacitacao.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterUserDTO {
    private String login;
    private String username;
    private Long roles;
    private String senha;

    public RegisterUserDTO(String login, String username, Long roles, String senha) {
        this.login = login;
        this.username = username;
        this.roles = roles;
        this.senha = senha;
    }
}
