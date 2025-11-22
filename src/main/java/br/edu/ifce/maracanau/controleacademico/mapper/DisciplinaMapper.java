package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;

public final class DisciplinaMapper {

    private DisciplinaMapper() {
    }

    public static DisciplinaDTO toDto(Disciplina disciplina) {
        String responsavel = disciplina.getResponsavel() != null ? disciplina.getResponsavel().getLogin() : null;
        return new DisciplinaDTO(
                disciplina.getId(),
                disciplina.getCodigo(),
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                disciplina.getSemestre(),
                responsavel
        );
    }

    public static Disciplina toEntity(DisciplinaDTO request, Usuario responsavel) {
        Disciplina disciplina = new Disciplina();
        disciplina.setResponsavel(responsavel);
        disciplina.setCodigo(request.codigo());
        disciplina.setNome(request.nome());
        disciplina.setCargaHoraria(request.cargaHoraria());
        disciplina.setSemestre(request.semestre());
        return disciplina;
    }

    public static void copyToEntity(DisciplinaDTO request, Disciplina disciplina) {
        disciplina.setCodigo(request.codigo());
        disciplina.setNome(request.nome());
        disciplina.setCargaHoraria(request.cargaHoraria());
        disciplina.setSemestre(request.semestre());
    }

}
