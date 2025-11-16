package br.edu.ifce.maracanau.controleacademico.security.context;

import br.edu.ifce.maracanau.controleacademico.exception.BaseException;
import br.edu.ifce.maracanau.controleacademico.exception.UsuarioModificationForbiddenException;
import br.edu.ifce.maracanau.controleacademico.exception.UsuarioUnauthorizedException;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.model.enums.PerfilUsuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;

public class SecurityContextProvider {

    private SecurityContextProvider() {}

    public static void assertAuthorization(String expectedLogin, Class<? extends BaseException> exception) {
        Usuario usuario = getContext();
        if (!usuario.getUsername().equals(expectedLogin) && usuario.getPerfil() != PerfilUsuario.ADMIN) {
            try {
                throw exception.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new UsuarioModificationForbiddenException();
            }
        }
    }

    public static Usuario getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof Usuario)) {
            throw new UsuarioUnauthorizedException();
        }

        return (Usuario) authentication.getPrincipal();
    }

}
