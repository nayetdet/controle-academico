package br.edu.ifce.maracanau.controleacademico.payload.query.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record ApplicationPageable(
        Integer pageNumber,
        Integer pageSize,
        Long totalElements
) {

    public <T> ApplicationPageable(Page<T> page) {
        this(page.getPageable().getPageNumber(), page.getPageable().getPageSize(), page.getTotalElements());
    }

    public <T> ApplicationPageable(Pageable pageable, Long totalElements) {
        this(pageable.getPageNumber(), pageable.getPageSize(), totalElements);
    }

}
