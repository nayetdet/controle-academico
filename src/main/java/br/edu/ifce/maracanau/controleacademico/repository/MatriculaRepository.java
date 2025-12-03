package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByAlunoIdAndDisciplinaId(Long alunoId, Long disciplinaId);

    boolean existsByAlunoIdAndDisciplinaIdAndIdNot(Long alunoId, Long disciplinaId, Long id);

}
