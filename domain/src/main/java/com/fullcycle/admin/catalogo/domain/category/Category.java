package com.fullcycle.admin.catalogo.domain.category;

import com.fullcycle.admin.catalogo.domain.AgregateRoot;
import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

import java.time.Instant;
import java.util.UUID;

public class Category extends AgregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryID anId,
                     final String anName,
                     final String anDescription,
                     final boolean anActive,
                     final Instant anCreatedAt,
                     final Instant anUpdatedAt,
                     final Instant anDeletedAt) {
        super(anId);
        this.name = anName;
        this.description = anDescription;
        this.active = anActive;
        this.createdAt = anCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = anDeletedAt;
    }

    public static Category newCategory(final String name, final String description, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var anDeletedAt = isActive ? null : now;
        return new Category(id, name, description, isActive, now, now, anDeletedAt);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void validate(final ValidationHandler aHandler) {
        new CategoryValidator(this, aHandler).validate();
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(final String aName, final String aDescription, final boolean isActive) {
        this.name = aName;
        this.description = aDescription;

        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.updatedAt = Instant.now();
        return this;

    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}