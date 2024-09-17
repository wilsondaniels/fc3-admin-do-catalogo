package com.fullcycle.admin.catalogo.domain;

import com.fullcycle.admin.catalogo.domain.validation.ValidationHandler;

public abstract class AgregateRoot<ID extends Identifier> extends Entity<ID> {

    protected AgregateRoot(final ID id) {
        super(id);
    }
}
