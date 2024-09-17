package com.fullcycle.admin.catalogo.application;

import com.fullcycle.admin.catalogo.domain.category.Category;

public class UseCase {
    public Category execute() {
        return Category.newCategory("a", "b", true);
    }
}