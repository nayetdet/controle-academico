package br.edu.ifce.maracanau.controleacademico.repository;

import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    @Query("""
        SELECT d
        FROM Disciplina d
        WHERE (:nome IS NULL OR LOWER(d.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
            AND (:codigo IS NULL OR LOWER(d.codigo) LIKE LOWER(CONCAT('%', :codigo, '%')))
            AND (:cargaHorariaMinima IS NULL OR d.cargaHoraria >= :cargaMin)
            AND (:cargaHorariaMaxima IS NULL OR d.cargaHoraria <= :cargaMax)
            AND (:semestre IS NULL OR d.semestre = :semestre)
        """)
    Page<Disciplina> search(
            @Param("nome") String nome,
            @Param("codigo") String codigo,
            @Param("cargaHorariaMinima") Integer cargaHorariaMinima,
            @Param("cargaHorariaMaxima") Integer cargaHorariaMaxima,
            @Param("semestre") String semestre,
            Pageable pageable
    );

    Optional<Disciplina> findByCodigo(String codigo);
    Boolean existsByCodigo(String codigo);
    void deleteByCodigo(String codigo);

}
