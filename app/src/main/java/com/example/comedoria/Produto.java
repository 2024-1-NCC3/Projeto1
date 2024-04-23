package com.example.comedoria;

import android.widget.ImageView;

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

    private int caminhoImg;

    private String ingrediente;

    private boolean selecionado;

    private int quantidade = 1;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto(int id,String nome, double preco, String ingrediente, int caminhoImg) {
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.ingrediente = ingrediente;
        this.setSelecionado(false);
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

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
        this.ingrediente = ingrediente;
    }

    public int getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(int caminhoImg) {
        this.caminhoImg = caminhoImg;
    }
}
