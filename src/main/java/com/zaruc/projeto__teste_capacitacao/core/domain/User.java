package com.zaruc.projeto__teste_capacitacao.core.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String username;
    private String senha;

    private List<GrantedAuthority> authorities;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id")
    private Roles roles;

    public User(String login, String username, String senha, Roles roles) {
        this.login = login;
        this.username= username;
        this.senha = senha;
        this.roles = roles;
    }
    public User(User user) {
        username = user.getUsername();
        senha = user.getPassword();
        authorities = Arrays.stream(user.getRoles().getNome().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
