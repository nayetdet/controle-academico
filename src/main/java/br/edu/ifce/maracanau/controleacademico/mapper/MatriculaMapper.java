package br.edu.ifce.maracanau.controleacademico.mapper;

import br.edu.ifce.maracanau.controleacademico.model.Matricula;
import br.edu.ifce.maracanau.controleacademico.model.Usuario;
import br.edu.ifce.maracanau.controleacademico.payload.dto.MatriculaDTO;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaRequest;
import br.edu.ifce.maracanau.controleacademico.payload.request.MatriculaUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MatriculaMapper {

    private final AlunoMapper alunoMapper;
    private final DisciplinaMapper disciplinaMapper;
    private final UsuarioMapper usuarioMapper;

    public MatriculaMapper(AlunoMapper alunoMapper, DisciplinaMapper disciplinaMapper, UsuarioMapper usuarioMapper) {
        this.alunoMapper = alunoMapper;
        this.disciplinaMapper = disciplinaMapper;
        this.usuarioMapper = usuarioMapper;
    }

    public Matricula toModel(MatriculaRequest request, Usuario responsavel) {
        return new Matricula(
                responsavel,
                alunoMapper.toModel(request.matriculaAluno()),
                disciplinaMapper.toModel(request.codigoDisciplina()),
                request.dataMatricula(),
                request.situacao(),
                request.notaFinal()
        );
    }

    public MatriculaDTO toDTO(Matricula matricula) {
        return new MatriculaDTO(
                matricula.getId(),
                alunoMapper.toDTO(matricula.getAluno()),
                disciplinaMapper.toDTO(matricula.getDisciplina()),
                matricula.getDataMatricula(),
                matricula.getSituacao(),
                matricula.getNotaFinal(),
                usuarioMapper.toSimplificadoDTO(matricula.getResponsavel())
        );
    }

    public void update(Matricula matricula, MatriculaUpdateRequest request) {
        matricula.setDataMatricula(request.dataMatricula());
        matricula.setSituacao(request.situacao());
        matricula.setNotaFinal(request.notaFinal());
    }

}
