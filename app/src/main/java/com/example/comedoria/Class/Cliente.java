package com.example.comedoria.Class;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String idCliente;

    /**Construtores da classe*/
    public Cliente(String nome, String sobrenome, String idCliente) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.idCliente = idCliente;
    }

    /**Método Getters e Setters dos atributos da classe*/
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
