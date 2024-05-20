package com.example.comedoria.Class;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String idCliente;

    public Cliente(String nome, String sobrenome, String idCliente) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNomeCompleto(){
        if(this.sobrenome == "null" || this.sobrenome == null){
            return nome;
        }
        else{
            return nome + " " +sobrenome;
        }
    }
}
