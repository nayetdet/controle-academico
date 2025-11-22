package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByMatriculaIgnoreCase(String matricula);

    boolean existsByMatriculaIgnoreCase(String matricula);

    boolean existsByMatriculaIgnoreCaseAndIdNot(String matricula, Long id);

}
