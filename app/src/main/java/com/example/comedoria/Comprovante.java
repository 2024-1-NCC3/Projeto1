package com.example.comedoria;

import java.util.List;

public class Comprovante {
    private String status;
    private String observacoes;
    private int numeroPedido;
    private String dataRetirada;
    private String horaRetirada;
    private List<Produto> listaProdutos;

    public Comprovante(String status, String observacoes, int numeroPedido, String dataRetirada, String horaRetirada, List<Produto> listaProdutos) {
        this.status = status;
        this.observacoes = observacoes;
        this.numeroPedido = numeroPedido;
        this.dataRetirada = dataRetirada;
        this.horaRetirada = horaRetirada;
        this.listaProdutos = listaProdutos;
    }

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
}
