package com.zaruc.projeto__teste_capacitacao.core.repository;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRespository extends JpaRepository<Roles, Long> {

    @Query(
            "SELECT obj FROM Roles obj WHERE obj.nome =:roleUser"
    )
    Optional findByNome(String roleUser);
}
