package com.example.comedoria.Class;

public class Estoque {
    int idEstoque, quantidade;

    /**Construtores da classe*/
    public Estoque(int idEstoque, int quantidade) {
        this.idEstoque = idEstoque;
        this.quantidade = quantidade;
    }

    /**Método Getters e Setters dos atributos da classe*/
    public int getIdEstoque() {
        return idEstoque;
    }

    public int getQuantidade() {
        return quantidade;
    }


}
