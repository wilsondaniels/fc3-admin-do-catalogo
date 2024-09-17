package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.pagination.CategorySeachQuery;
import com.fullcycle.admin.catalogo.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteBydId(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    Pagination<Category> findAll(CategorySeachQuery aQuery);
}
