package com.zaruc.projeto__teste_capacitacao.core.service;

import com.zaruc.projeto__teste_capacitacao.core.domain.Roles;
import com.zaruc.projeto__teste_capacitacao.core.domain.User;
import com.zaruc.projeto__teste_capacitacao.core.dto.RegisterUserDTO;
import com.zaruc.projeto__teste_capacitacao.core.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService  implements UserDetailsService {
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private RolesService rolesService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = (User) userRespository.findByUsername(username);

        if(user == null) {
            throw  new UsernameNotFoundException("Usuário não encontrado");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getSenha(),
                user.isEnabled(),
                true,true,true,
                user.getAuthorities()
        );
    }

    public User createUser(RegisterUserDTO registerUserDTO) {
        Roles roles = rolesService.findById(registerUserDTO.getRoles());
        if(this.userRespository.findByUsername(registerUserDTO.getLogin()) != null) {
            throw  new UsernameNotFoundException("Usuario já cadastrado.");
        }
        roles.equals(roles.getId());
        String senhaEncode = new BCryptPasswordEncoder().encode(registerUserDTO.getSenha());
        User user = new User(registerUserDTO.getLogin(), registerUserDTO.getUsername(),  senhaEncode, roles);
        return this.userRespository.save(user);
    }

//    public User delete(Long id){
//        return userRespository.deleteById(id);
//    }
}
