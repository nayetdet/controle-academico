package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.MatriculaDisciplina;
import br.edu.ifce.maracanau.controleacademico.model.enums.SituacaoMatricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MatriculaDisciplinaRepository extends JpaRepository<MatriculaDisciplina, Long> {

    @Query("""
        SELECT m
        FROM MatriculaDisciplina m
        WHERE (:matriculaAluno IS NULL OR LOWER(m.aluno.matricula) LIKE LOWER(CONCAT('%', :matriculaAluno, '%')))
            AND (:codigoDisciplina IS NULL OR LOWER(m.disciplina.codigo) LIKE LOWER(CONCAT('%', :codigoDisciplina, '%')))
            AND (:dataMatricula IS NULL OR m.dataMatricula = :dataMatricula)
            AND (:situacao IS NULL OR m.situacao = :situacao)
            AND (:notaFinalMinima IS NULL OR m.notaFinal >= :notaFinalMinima)
            AND (:notaFinalMaxima IS NULL OR m.notaFinal <= :notaFinalMaxima)
        """)
    Page<MatriculaDisciplina> search(
            @Param("matriculaAluno") String matriculaAluno,
            @Param("codigoDisciplina") String codigoDisciplina,
            @Param("dataMatricula") LocalDate dataMatricula,
            @Param("situacao") SituacaoMatricula situacao,
            @Param("notaFinalMinima") Double notaFinalMinima,
            @Param("notaFinalMaxima") Double notaFinalMaxima,
            Pageable pageable
    );

    Optional<MatriculaDisciplina> findByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina);
    Boolean existsByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina);
    Boolean existsByAlunoMatriculaAndDisciplinaCodigoAndIdNot(String matriculaAluno, String codigoDisciplina, Long id);
    void deleteByAlunoMatriculaAndDisciplinaCodigo(String matriculaAluno, String codigoDisciplina);

}
