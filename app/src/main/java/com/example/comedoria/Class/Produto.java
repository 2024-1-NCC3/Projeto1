package com.example.comedoria.Class;

import java.util.List;

public class Produto {

    private String nome;
    private int id;
    private double preco;

    public int getEstoque() {
        return estoque;
    }

    private int estoque;
    private List<String> categoria;

    public List<String> getCategoria() {
        return categoria;
    }

    private String caminhoImg;

    private String ingrediente;

    private boolean selecionado = false;

    private int quantidade = 1;

    private boolean aparecer = true;

    public boolean isAparecer() {
        return aparecer;
    }

    public void setAparecer(boolean aparecer) {
        this.aparecer = aparecer;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Produto(int id,String nome, double preco, List<String> categoria, String caminhoImg, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.ingrediente = ingrediente;
        this.id = id;
        this.categoria = categoria;
        this.estoque = estoque;
    }

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public Produto(String nome, int id, double preco, String caminhoImg) {
        this.nome = nome;
        this.id = id;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
    }

    //Este construtor é para o estoque
    public Produto(int id, String nome, double preco, String caminhoImg, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.ingrediente = ingrediente;
        this.setSelecionado(false);
        this.quantidade = quantidade;
    }

    public boolean isSelecionado() {
        return selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public String getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }
}
