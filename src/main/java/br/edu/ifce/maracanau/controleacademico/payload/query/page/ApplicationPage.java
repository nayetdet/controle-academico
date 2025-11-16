package br.edu.ifce.maracanau.controleacademico.payload.query.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record ApplicationPage<T>(
        List<T> content,
        ApplicationPageable pageable
) {

    public ApplicationPage(Page<T> page) {
        this(page.getContent(), new ApplicationPageable(page));
    }

    public ApplicationPage(List<T> content, Pageable pageable) {
        this(content, new ApplicationPageable(pageable, (long) content.size()));
    }

}
