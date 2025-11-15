package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
    Boolean existsByLogin(String login);

}
