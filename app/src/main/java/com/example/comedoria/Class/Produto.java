package com.example.comedoria.Class;

import java.util.List;

public class Produto {
    // Campos
    private String nome; // Nome do produto
    private int id; // ID do produto
    private double preco; // Preço do produto
    private int estoque; // Quantidade em estoque do produto
    private List<String> categoria; // Lista de categorias do produto
    private String caminhoImg; // Caminho da imagem do produto
    private String ingrediente; // Ingredientes do produto
    private boolean selecionado = false; // Indica se o produto está selecionado
    private int quantidade = 1; // Quantidade do produto
    private int idEstoque; // ID do estoque do produto
    private boolean aparecer = true; // Indica se o produto deve aparecer

    // Construtores
    public Produto(int id, String nome, double preco, List<String> categoria, String caminhoImg, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.caminhoImg = caminhoImg;
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

    public Produto(int id, String nome, double preco, String caminhoImg, int quantidade, int idEstoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.quantidade = quantidade;
        this.idEstoque = idEstoque;
    }

    // Métodos Get e Set
    public int getIdEstoque() {
        return idEstoque;
    }

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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public List<String> getCategoria() {
        return categoria;
    }

    public String getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }
}
