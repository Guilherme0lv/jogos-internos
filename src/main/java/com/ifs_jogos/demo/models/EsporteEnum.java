package com.ifs_jogos.demo.models;

public enum EsporteEnum {

    INTEGRADO("INTEGRADO"),
    SUPERIOR("SUPERIOR"),
    TECNICO("TECNICO");

    private final String valor;

    EsporteEnum(String valor) {
        this.valor = valor;
    }

    public String paraString() {
        return this.valor;
    }
}
