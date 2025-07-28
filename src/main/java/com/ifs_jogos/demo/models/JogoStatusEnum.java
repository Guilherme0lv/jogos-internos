package com.ifs_jogos.demo.models;

public enum JogoStatusEnum {

    FINALIZADO("finalizado"),
    WO("wo"),
    PENDENTE("pendente");

    private final String valor;

    JogoStatusEnum(String valor) {
        this.valor = valor;
    }

    public String paraString() {
        return valor;
    }
}
