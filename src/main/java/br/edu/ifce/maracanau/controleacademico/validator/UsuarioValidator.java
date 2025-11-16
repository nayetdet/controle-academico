package br.edu.ifce.maracanau.controleacademico.validator;

import br.edu.ifce.maracanau.controleacademico.exception.UsuarioLoginConflictException;
import br.edu.ifce.maracanau.controleacademico.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validateLogin(String login) {
        if (login != null && usuarioRepository.existsByLogin(login)) {
            throw new UsuarioLoginConflictException();
        }
    }

}
