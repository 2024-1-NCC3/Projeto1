package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class Produtos extends AppCompatActivity {

    // Cores
    //#FF403832 Marrom
    //#FFEEE1 bege
    //#0F5929 verde escuro
    //#94E986 verde claro

    private RecyclerView recyclerProduto, recyclerProduto1;
    private CheckBox cbProduto;

    private List<Produto> listaProdutos = new ArrayList<>();

    private int[] listImgProduto = {
            R.drawable.imgstrogonofffrango

    };

    private String[] listaIngrediente = {
            "Frango, Creme de leite, arroz, alho, sal, ketchup, mostarda, molho ingles",
            "Farinha, ovo, leite, chocolate, açucar"

    };

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        recyclerProduto = findViewById(R.id.recycleProduto);


        ListarProduto();

        AdapterProduto adapter1 = new AdapterProduto(listaProdutos);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);

        recyclerProduto.setAdapter(adapter1);




    }

    private void ListarProduto(){
        Produto produto = new Produto("Marmita Strogonoff de Frango com Batatas",14.5, listaIngrediente[0], listImgProduto[0]);
        listaProdutos.add(produto);

        Produto pedacoBolo = new Produto ("Pedaço de bolo de Bolo Cholate com cobertura chocolate", 10.0, listaIngrediente[1],listImgProduto[0]);
        listaProdutos.add(pedacoBolo);

        Produto paoDeQueijo = new Produto("Porção de pao de queijo", 5.00, listaIngrediente[1], listImgProduto[0]);
        listaProdutos.add(paoDeQueijo);
    }

}