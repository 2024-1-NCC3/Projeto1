package com.example.comedoria.Class;

import java.util.List;

public class Comprovante {
    private String status;
    private String observacoes;
    private int numeroPedido;
    private String dataRetirada;
    private String horaRetirada;

    private String idPedido;
    private List<Produto> listaProdutos;

    /**Construtores da classe*/
    public Comprovante(String status, String observacoes, int numeroPedido, String dataRetirada, String horaRetirada, List<Produto> listaProdutos, String idPedido) {
        this.status = status;
        this.observacoes = observacoes;
        this.numeroPedido = numeroPedido;
        this.dataRetirada = dataRetirada;
        this.horaRetirada = horaRetirada;
        this.listaProdutos = listaProdutos;
        this.idPedido = idPedido;
    }

    /**Método Getters e Setters dos atributos da classe*/
    public String getStatus() {
        return status;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public String getHoraRetirada() {
        return horaRetirada;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public String getIdPedido() {return idPedido;}
}
