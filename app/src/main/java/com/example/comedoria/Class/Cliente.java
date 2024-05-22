package com.example.comedoria.Class;

public class Cliente {
    private String nome; // Nome do cliente
    private String sobrenome; // Sobrenome do cliente
    private String idCliente; // Identificador único do cliente

    // Construtor da classe Cliente
    public Cliente(String nome, String sobrenome, String idCliente) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idCliente = idCliente;
    }

    // Método getter para o nome do cliente
    public String getNome() {
        return nome;
    }

    // Método getter para o sobrenome do cliente
    public String getSobrenome() {
        return sobrenome;
    }

    // Método getter para o identificador único do cliente
    public String getIdCliente() {
        return idCliente;
    }

    // Método para obter o nome completo do cliente
    public String getNomeCompleto() {
        // Verifica se o sobrenome é nulo ou "null"
        if (this.sobrenome == null || this.sobrenome.equals("null")) {
            return nome; // Se for nulo ou "null", retorna apenas o nome
        } else {
            return nome + " " + sobrenome; // Caso contrário, retorna nome + sobrenome
        }
    }
}
