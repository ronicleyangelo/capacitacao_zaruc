package com.zaruc.projeto__teste_capacitacao.core.repository;

import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRespository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.roles " +
            "WHERE u.username = :login")
    User findByUsername(@Param("login") String login);
//    UserDetails findByUsername(String username);
}
