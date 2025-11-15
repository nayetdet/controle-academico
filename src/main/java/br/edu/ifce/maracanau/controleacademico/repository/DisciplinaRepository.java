package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}
