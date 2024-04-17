package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Produtos extends AppCompatActivity {

    private RecyclerView recyclerProduto;

    private List<Produto> listaProdutos = new ArrayList<>();

    private int[] listImgProduto = {
            R.drawable.imgstrogonofffrango


    };

    private String[] listaIngrediente = {
            "Frango, Creme de leite, arroz, alho, sal, ketchup, mostarda, molho ingles",
            "Farinha, ovo, leite, chocolate, açucar"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        recyclerProduto = findViewById(R.id.recycleProduto);

        ListarProduto();

        AdapterProduto adapter1 = new AdapterProduto(listaProdutos);

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);

        recyclerProduto.setAdapter(adapter1);





    }

    private void ListarProduto(){
        Produto produto = new Produto("Marmita Strogonoff de Frango com Batatas",14.50, listaIngrediente[0], listImgProduto[0]);
        listaProdutos.add(produto);
    }

}