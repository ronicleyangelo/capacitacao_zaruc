package com.zaruc.projeto__teste_capacitacao.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private String username;
    private String login;

    public UsuarioDTO(String username, String login) {
        this.username = username;
        this.login = login;
    }
}
