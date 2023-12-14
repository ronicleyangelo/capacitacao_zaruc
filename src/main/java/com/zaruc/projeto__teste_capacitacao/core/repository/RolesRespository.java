package com.zaruc.projeto__teste_capacitacao.core.repository;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRespository extends JpaRepository<Roles, Long> {
}
