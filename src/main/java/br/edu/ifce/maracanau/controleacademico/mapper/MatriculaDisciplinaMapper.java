package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.MatriculaDisciplina;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDisciplinaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaDisciplinaRequest;
import org.springframework.stereotype.Component;

@Component
public class MatriculaDisciplinaMapper {

    private final AlunoMapper alunoMapper;
    private final DisciplinaMapper disciplinaMapper;
    private final UsuarioMapper usuarioMapper;

    public MatriculaDisciplinaMapper(AlunoMapper alunoMapper, DisciplinaMapper disciplinaMapper, UsuarioMapper usuarioMapper) {
        this.alunoMapper = alunoMapper;
        this.disciplinaMapper = disciplinaMapper;
        this.usuarioMapper = usuarioMapper;
    }

    public MatriculaDisciplina toModel(MatriculaDisciplinaRequest request, Usuario responsavel) {
        return new MatriculaDisciplina(
                responsavel,
                alunoMapper.toModel(request.matriculaAluno()),
                disciplinaMapper.toModel(request.codigoDisciplina()),
                request.dataMatricula(),
                request.situacao(),
                request.notaFinal()
        );
    }

    public MatriculaDisciplinaDTO toDTO(MatriculaDisciplina matriculaDisciplina) {
        return new MatriculaDisciplinaDTO(
                matriculaDisciplina.getId(),
                alunoMapper.toDTO(matriculaDisciplina.getAluno()),
                disciplinaMapper.toDTO(matriculaDisciplina.getDisciplina()),
                matriculaDisciplina.getDataMatricula(),
                matriculaDisciplina.getSituacao(),
                matriculaDisciplina.getNotaFinal(),
                usuarioMapper.toSimplificadoDTO(matriculaDisciplina.getResponsavel())
        );
    }

}
