package com.zaruc.projeto__teste_capacitacao.core.repository;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRespository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    UserDetails findByUsername(String login);
}
