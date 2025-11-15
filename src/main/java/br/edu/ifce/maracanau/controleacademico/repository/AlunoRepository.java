package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
