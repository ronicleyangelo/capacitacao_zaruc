package com.zaruc.projeto__teste_capacitacao.core.service;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.RolesRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    private RolesRespository rolesRespository;

    public Roles insert(Roles roles) {
        return rolesRespository.save(roles);
    }

    public Roles findById(Long id) {
        return rolesRespository.findById(id).orElse(null);
    }

}
