package com.zaruc.projeto__teste_capacitacao.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Roles {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles roles)) return false;
        return id.equals(roles.id) && nome.equals(roles.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome);
    }
}
