package com.zaruc.projeto__teste_capacitacao.core.controller;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import com.zaruc.projeto__teste_capacitacao.core.service.RolesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping( "/roles")
@CrossOrigin(origins = "*")
public class RolesController {
    @Autowired
    RolesService rolesService;
    @RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
    public ResponseEntity<?> insert(@Valid @RequestBody Roles roles) {
        roles = rolesService.insert(roles);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(roles.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
