package com.example.comedoria.Class;

public class Categoria {
    private String nome;
    private String Descricao;
    private String caminho;
    private Boolean aparecer;

    private int idCategoria;

    public int getIdCategoria() {
        return idCategoria;
    }

    public Categoria(String nome, String descricao, String caminho, boolean aparecer) {
        this.nome = nome;
        Descricao = descricao;
        this.caminho = caminho;
        this.aparecer = aparecer;
    }

    public Categoria(String nome, int idCategoria) {
        this.nome = nome;
        this.idCategoria = idCategoria;
    }

    public Boolean getAparecer() {
        return aparecer;
    }
    public void setAparecer(Boolean aparecer) {
        this.aparecer = aparecer;
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

    @Override
    public String toString() {
        return nome;
    }
}