package br.edu.ifce.maracanau.controleacademico.model;

import br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(
        name = "usuarios",
        indexes = {
                @Index(name = "idx_login", columnList = "login", unique = true)
        }
)
public class Usuario extends BaseModel implements UserDetails {

    public Usuario() {}

    public Usuario(Usuario responsavel, String login, String senha, PerfilUsuario perfil) {
        super(responsavel);
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Usuario(Long id, Usuario responsavel, String login, String senha, PerfilUsuario perfil) {
        super(id, responsavel);
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfil;

    public List<String> getRoles() {
        return getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority secretarioAuthority = new SimpleGrantedAuthority("ROLE_SECRETARIO");
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return perfil == PerfilUsuario.SECRETARIO
                ? List.of(secretarioAuthority)
                : List.of(secretarioAuthority, adminAuthority);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }

}
