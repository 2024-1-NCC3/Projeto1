package com.example.comedoria;

public class Categoria {
    private String nome;
    private String Descricao;
    private int caminho;

    public Categoria(String nome, String descricao, int caminho) {
        this.nome = nome;
        Descricao = descricao;
        this.caminho = caminho;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public int getCaminho() {
        return caminho;
    }

    public void setCaminho(int caminho) {
        this.caminho = caminho;
    }
}
