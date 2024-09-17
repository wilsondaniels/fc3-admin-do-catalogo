package com.fullcycle.admin.catalogo.infraestructure;

import com.fullcycle.admin.catalogo.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");
        System.out.println(new UseCase().execute());
    }
}