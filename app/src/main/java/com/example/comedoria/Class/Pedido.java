package com.example.comedoria.Class;

import java.util.List;

public class Pedido {
    private String data; // Data do pedido
    private String hora; // Hora do pedido
    private String status; // Status do pedido (por exemplo: "Confirmado", "Cancelado", etc.)
    private List<String> produtos; // Lista de produtos no pedido
    private double total; // Total do pedido
    private String id_pedido; // ID único do pedido
    private String nomeCliente; // Nome do cliente que fez o pedido
    private int numeroPedido; // Número único do pedido

    // Construtor da classe Pedido
    public Pedido(String data, List<String> produtos, double total, String status, String id_pedido) {
        this.data = data;
        this.produtos = produtos;
        this.total = total;
        this.status = status;
        this.id_pedido = id_pedido;
    }

    // Construtor alternativo da classe Pedido
    public Pedido(String data, String hora, List<String> produtos, double total, String status, String id_pedido, String nomeCliente, int numeroPedido) {
        this.data = data;
        this.hora = hora;
        this.produtos = produtos;
        this.total = total;
        this.status = status;
        this.id_pedido = id_pedido;
        this.nomeCliente = nomeCliente;
        this.numeroPedido = numeroPedido;
    }

    // Método getter para a data do pedido
    public String getData() {
        return data;
    }

    // Método getter para a hora do pedido
    public String getHora() {
        return hora;
    }

    // Método getter para o status do pedido
    public String getStatus() {
        return status;
    }

    // Método getter para a lista de produtos do pedido
    public List<String> getProdutos() {
        return produtos;
    }

    // Método para obter a lista de produtos como uma única string formatada
    public String produtosComoString() {
        StringBuilder retorno = new StringBuilder();
        for (int i = 0; i < produtos.size(); i++) {
            retorno.append(produtos.get(i));
            if (i != produtos.size() - 1) {
                retorno.append("\n");
            }
        }
        return retorno.toString();
    }

    // Método getter para o ID do pedido
    public String getId_pedido() {
        return id_pedido;
    }

    // Método getter para o total do pedido
    public double getTotal() {
        return total;
    }

    // Método getter para o nome do cliente
    public String getNomeCliente() {
        return nomeCliente;
    }

    // Método getter para o número do pedido
    public int getNumeroPedido() {
        return numeroPedido;
    }
}
