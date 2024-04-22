package com.example.comedoria;

import android.widget.ImageView;

public class Produto {

    private String nome;

    private double preco;

    private int caminhoImg;

    private String ingrediente;

    private boolean selecionado;
    public Produto(String nome, double preco,String ingrediente, int caminhoImg) {
        this.nome = nome;
        this.preco = preco;
        this.caminhoImg = caminhoImg;
        this.ingrediente = ingrediente;
        this.setSelecionado(false);
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
