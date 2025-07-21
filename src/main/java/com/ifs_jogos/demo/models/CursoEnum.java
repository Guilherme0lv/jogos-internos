package com.ifs_jogos.demo.models;

public enum CursoEnum {

    INTEGRADO("INTEGRADO"),
    SUPERIOR("SUPERIOR"),
    TECNICO("TECNICO");

    private final String valor;

    CursoEnum(String valor) {
        this.valor = valor;
    }

    public String paraString() {
        return this.valor;
    }
}
