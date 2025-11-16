package br.edu.ifce.maracanau.controleacademico.payload.query;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

public class DisciplinaQuery extends BaseQuery {

    private String nome;
    private String codigo;
    private Integer cargaHorariaMinima;
    private Integer cargaHorariaMaxima;
    private String semestre;

    protected DisciplinaQuery(Integer pageNumber, Integer pageSize, String orderBy) {
        super(
                Map.of(
                "id", "id",
                "codigo", "codigo",
                "cargaHoraria", "cargaHoraria",
                "semestre", "semestre"
                ),
                pageNumber,
                pageSize,
                orderBy
        );
    }

    @Override
    @Schema(
            defaultValue = "id",
            allowableValues = {
                    "id",
                    "codigo",
                    "cargaHoraria",
                    "semestre",
                    "-id",
                    "-codigo",
                    "-cargaHoraria",
                    "-semestre"
            }
    )
    public String getOrderBy() {
        return super.getOrderBy();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getCargaHorariaMinima() {
        return cargaHorariaMinima;
    }

    public void setCargaHorariaMinima(Integer cargaHorariaMinima) {
        this.cargaHorariaMinima = cargaHorariaMinima;
    }

    public Integer getCargaHorariaMaxima() {
        return cargaHorariaMaxima;
    }

    public void setCargaHorariaMaxima(Integer cargaHorariaMaxima) {
        this.cargaHorariaMaxima = cargaHorariaMaxima;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

}
