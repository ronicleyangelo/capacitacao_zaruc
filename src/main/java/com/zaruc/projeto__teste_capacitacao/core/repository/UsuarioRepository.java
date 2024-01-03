package com.zaruc.projeto__teste_capacitacao.core.repository;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM User obj WHERE  upper(obj.username) like upper(CONCAT('%',:usuario,'%')) ")
    User findByUsuario(@Param("usuario") String usuario);

}
