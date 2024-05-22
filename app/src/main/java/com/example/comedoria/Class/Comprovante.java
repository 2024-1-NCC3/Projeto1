package com.example.comedoria.Class;

import java.util.List;

public class Comprovante {
    private String status; // Status do pedido (por exemplo: "Confirmado", "Cancelado", etc.)
    private String observacoes; // Observações relacionadas ao pedido
    private int numeroPedido; // Número único do pedido
    private String dataRetirada; // Data prevista para retirada do pedido
    private String horaRetirada; // Hora prevista para retirada do pedido
    private List<Produto> listaProdutos; // Lista de produtos no pedido

    // Construtor da classe Comprovante
    public Comprovante(String status, String observacoes, int numeroPedido, String dataRetirada, String horaRetirada, List<Produto> listaProdutos) {
        this.status = status;
        this.observacoes = observacoes;
        this.numeroPedido = numeroPedido;
        this.dataRetirada = dataRetirada;
        this.horaRetirada = horaRetirada;
        this.listaProdutos = listaProdutos;
    }

    // Método getter para o status do pedido
    public String getStatus() {
        return status;
    }

    // Método getter para as observações do pedido
    public String getObservacoes() {
        return observacoes;
    }

    // Método getter para o número do pedido
    public int getNumeroPedido() {
        return numeroPedido;
    }

    // Método getter para a data de retirada do pedido
    public String getDataRetirada() {
        return dataRetirada;
    }

    // Método getter para a hora de retirada do pedido
    public String getHoraRetirada() {
        return horaRetirada;
    }

    // Método getter para a lista de produtos do pedido
    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }
}
