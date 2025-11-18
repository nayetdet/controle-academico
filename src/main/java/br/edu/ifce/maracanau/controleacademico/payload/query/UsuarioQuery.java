package br.edu.ifce.maracanau.controleacademico.payload.query;

public class UsuarioQuery extends BaseQuery {

    public UsuarioQuery(Integer pageNumber, Integer pageSize, String orderBy) {
        super(pageNumber, pageSize, orderBy);
    }

}
