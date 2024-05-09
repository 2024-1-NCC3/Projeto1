package com.example.comedoria;

import android.widget.ImageView;

import java.util.List;

public class Produto {

    private String nome;
    private int id;
    private double preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List categorias;

    public List getCategorias() {
        return categorias;
    }

    private String caminhoImg;

    private String ingrediente;

    private boolean selecionado;

    private int quantidade = 1;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public Produto(int id,String nome, double preco, List categorias, String caminhoImg) {
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.ingrediente = ingrediente;
        this.setSelecionado(false);
        this.id = id;
        this.categorias = categorias;
    }

    //Este construtor é para o estoque
    public Produto(String nome, double preco, String caminhoImg, int quantidade) {
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
