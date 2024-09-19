package com.fullcycle.admin.catalogo.application.category.retrieve.get;

import com.fullcycle.admin.catalogo.domain.category.Category;
import com.fullcycle.admin.catalogo.domain.category.CategoryGateway;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.domain.exception.DomainException;
import com.fullcycle.admin.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryUseCase extends GetCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(String anId) {
        final CategoryID id = CategoryID.from(anId);
        return this.categoryGateway.findById(id)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(id));
    }

    private static Supplier<DomainException> notFound(CategoryID anId) {
        return () -> DomainException.with(
                new Error("Category with ID %s was not found".formatted(anId.getValue()))
        );
    }
}
