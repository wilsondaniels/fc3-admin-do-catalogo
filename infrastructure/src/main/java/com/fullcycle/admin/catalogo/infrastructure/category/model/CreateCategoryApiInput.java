package com.fullcycle.admin.catalogo.infrastructure.category.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCategoryApiInput(
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("is_active") Boolean active
) {
}