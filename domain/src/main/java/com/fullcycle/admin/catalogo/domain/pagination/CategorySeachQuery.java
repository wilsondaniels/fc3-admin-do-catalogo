package com.fullcycle.admin.catalogo.domain.pagination;

public record CategorySeachQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
