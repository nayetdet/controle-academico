package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.Disciplina;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.DisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.DisciplinaUpdateRequest;
import br.edu.ifce.maracanau.controleacademico.repository.DisciplinaRepository;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaMapper {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaMapper(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public Disciplina toModel(String codigo) {
        return disciplinaRepository.findByCodigo(codigo).orElse(null);
    }

    public Disciplina toModel(DisciplinaRequest request, Usuario responsavel) {
        return new Disciplina(
                responsavel,
                request.codigo(),
                request.nome(),
                request.cargaHoraria(),
                request.semestre()
        );
    }

    public DisciplinaDTO toDTO(Disciplina disciplina) {
        return new DisciplinaDTO(
                disciplina.getId(),
                disciplina.getCodigo(),
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                disciplina.getSemestre()
        );
    }

    public void update(Disciplina disciplina, DisciplinaUpdateRequest request) {
        disciplina.setNome(request.nome());
        disciplina.setCargaHoraria(request.cargaHoraria());
        disciplina.setSemestre(request.semestre());
    }

}
