package com.example.comedoria.Class;

import java.util.List;

public class Pedido {
    private String data;
    private String hora;
    private String status;
    private List<String> produtos;
    private double total;
    private String id_pedido;
    private String nomeCliente;
    private int numeroPedido;

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getData() {
        return data;
    }
    public String getHora(){return hora;}

    public String getStatus() {
        return status;
    }

    public List<String> getProdutos() {
        return produtos;
    }

    public String produtosComoString(){
        String retorno = "";
        for(int i =0; i < produtos.size();i++){
            if(i == produtos.size()-1){
                retorno+=produtos.get(i);
            }else{
                retorno+=produtos.get(i) + "\n";
            }
        }
        return retorno;
    }
    public String getId_pedido() {
        return id_pedido;
    }
    public double getTotal() {
        return total;
    }

    public Pedido(String data, List<String> produtos, double total, String status, String id_pedido) {
        this.data = data;
        this.produtos = produtos;
        this.total = total;
        this.status = status;
        this.id_pedido = id_pedido;;
    }
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
}
