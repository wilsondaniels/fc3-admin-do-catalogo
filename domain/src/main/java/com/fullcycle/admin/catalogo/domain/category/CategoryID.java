package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class CategoryID extends Identifier {

    private final String value;

    private CategoryID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static CategoryID unique() {
        return from(UUID.randomUUID());
    }

    public static CategoryID from(final String anId) {
        return new CategoryID(anId);
    }

    public static CategoryID from(final UUID anId) {
        return from(anId.toString().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CategoryID that = (CategoryID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
