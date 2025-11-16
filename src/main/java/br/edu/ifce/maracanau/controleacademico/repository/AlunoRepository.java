package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Aluno;
import br.edu.ifce.maracanau.controleacademico.model.enums.StatusAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query("""
        SELECT a
        FROM Aluno a
        WHERE (:nome IS NULL OR LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
            AND (:matricula IS NULL OR a.matricula = :matricula)
            AND (:dataNascimentoMinima IS NULL OR a.dataNascimento >= :dataMin)
            AND (:dataNascimentoMaxima IS NULL OR a.dataNascimento <= :dataMax)
            AND (:status IS NULL OR a.status = :status)
        """)
    Page<Aluno> search(
            @Param("nome") String nome,
            @Param("matricula") String matricula,
            @Param("dataNascimentoMinima") LocalDate dataNascimentoMinima,
            @Param("dataNascimentoMaxima") LocalDate dataNascimentoMaxima,
            @Param("status") StatusAluno status,
            Pageable pageable
    );

    Optional<Aluno> findByMatricula(String matricula);
    Boolean existsByMatricula(String matricula);
    void deleteByMatricula(String matricula);

}
