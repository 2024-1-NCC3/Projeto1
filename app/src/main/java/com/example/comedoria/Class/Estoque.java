package com.example.comedoria.Class;

public class Estoque {
    int idEstoque, quantidade;

    public int getIdEstoque() {
        return idEstoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Estoque(int idEstoque, int quantidade) {
        this.idEstoque = idEstoque;
        this.quantidade = quantidade;
    }
}
