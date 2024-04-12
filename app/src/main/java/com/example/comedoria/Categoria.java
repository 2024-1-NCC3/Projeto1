package com.example.comedoria;

public class Categoria {
    private String nome;
    private String Descricao;
    private String caminho;

    public Categoria(String nome, String descricao, String caminho) {
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

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
