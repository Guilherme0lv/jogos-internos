package com.ifs_jogos.demo.models;

public enum FaseEnum {

    GRUPO("GRUPO"),
    OITAVASFINAL("OITAVASFINAL"),
    QUARTASFINAL("QUARTASFINAL"),
    SEMIFINAL("SEMIFINAL"),
    FINAL("FINAL");

    private final String valor;

    FaseEnum(String valor) {
        this.valor = valor;
    }

    public String paraString() {
        return valor;
    }
}
