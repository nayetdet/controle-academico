package br.edu.ifce.maracanau.controleacademico.payload.query;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

public abstract class BaseQuery {

    private Map<String, String> sortableFields = Map.of("id", "id");

    @Min(0)
    private Integer pageNumber;

    @Min(0)
    @Max(50)
    private Integer pageSize;
    private String orderBy;

    protected BaseQuery(Integer pageNumber, Integer pageSize, String orderBy) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    protected BaseQuery(Map<String, String> sortableFields, Integer pageNumber, Integer pageSize, String orderBy) {
        this.sortableFields = sortableFields;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public Pageable getPageable() {
        return PageRequest.of(pageNumber, pageSize, getSort());
    }

    public Sort getSort() {
        Sort.Direction direction = orderBy.startsWith("-")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        String field = orderBy.replaceFirst("^-", "");
        return Sort.by(direction, sortableFields.getOrDefault(field, "id"));
    }

    public Map<String, String> getSortableFields() {
        return sortableFields;
    }

    public void setSortableFields(Map<String, String> sortableFields) {
        this.sortableFields = sortableFields;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

}
