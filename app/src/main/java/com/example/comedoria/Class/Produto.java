package com.example.comedoria.Class;

import java.util.List;

public class Produto {

    private int id; // ID único do produto
    private String nome; // Nome do produto
    private double preco; // Preço do produto
    private List<String> categoria; // Lista de categorias do produto
    private String caminhoImg; // Caminho da imagem do produto
    private String ingrediente; // Ingrediente do produto (pode ser nulo)
    private boolean selecionado = false; // Indica se o produto está selecionado
    private int quantidade = 1; // Quantidade do produto (padrão: 1)
    private boolean aparecer = true; // Indica se o produto deve aparecer (padrão: true)

    // Construtor da classe Produto
    public Produto(int id, String nome, double preco, List<String> categoria, String caminhoImg) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.caminhoImg = caminhoImg;
    }

    // Construtor alternativo da classe Produto (utilizado para o estoque)
    public Produto(int id, String nome, double preco, String caminhoImg, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.quantidade = quantidade;
    }

    // Método getter para o ID do produto
    public int getId() {
        return id;
    }

    // Método setter para o ID do produto
    public void setId(int id) {
        this.id = id;
    }

    // Método getter para o nome do produto
    public String getNome() {
        return nome;
    }

    // Método setter para o nome do produto
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Método getter para o preço do produto
    public double getPreco() {
        return preco;
    }

    // Método setter para o preço do produto
    public void setPreco(double preco) {
        this.preco = preco;
    }

    // Método getter para a lista de categorias do produto
    public List<String> getCategoria() {
        return categoria;
    }

    // Método getter para o caminho da imagem do produto
    public String getCaminhoImg() {
        return caminhoImg;
    }

    // Método setter para o caminho da imagem do produto
    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }

    // Método getter para o ingrediente do produto
    public String getIngrediente() {
        return ingrediente;
    }

    // Método setter para o ingrediente do produto
    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    // Método getter para indicar se o produto está selecionado
    public boolean isSelecionado() {
        return selecionado;
    }

    // Método setter para indicar se o produto está selecionado
    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }

    // Método getter para a quantidade do produto
    public int getQuantidade() {
        return quantidade;
    }

    // Método setter para a quantidade do produto
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Método getter para indicar se o produto deve aparecer
    public boolean isAparecer() {
        return aparecer;
    }

    // Método setter para indicar se o produto deve aparecer
    public void setAparecer(boolean aparecer) {
        this.aparecer = aparecer;
    }
}
