package com.example.comedoria.Class;

public class Categoria {
    private String nome; // Nome da categoria
    private String descricao; // Descrição da categoria
    private String caminho; // Caminho para a imagem representativa da categoria
    private Boolean aparecer; // Flag para determinar se a categoria deve aparecer ou não

    // Construtor da classe Categoria
    public Categoria(String nome, String descricao, String caminho, boolean aparecer) {
        this.nome = nome;
        this.descricao = descricao;
        this.caminho = caminho;
        this.aparecer = aparecer;
    }

    // Métodos getter e setter para a flag "aparecer"
    public Boolean getAparecer() {
        return aparecer;
    }

    public void setAparecer(Boolean aparecer) {
        this.aparecer = aparecer;
    }

    // Métodos getter e setter para o nome da categoria
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Métodos getter e setter para a descrição da categoria
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // Métodos getter e setter para o caminho da imagem representativa da categoria
    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
