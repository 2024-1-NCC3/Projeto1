package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Produtos extends AppCompatActivity {

    // Cores
    //#FF403832 Marrom
    //#FFEEE1 bege
    //#0F5929 verde escuro
    //#94E986 verde claro

    private AdapterProduto adapter1;
    private RecyclerView recyclerProduto, recyclerProduto1;
    private CheckBox cbProduto;

    private List<Produto> listaProdutos = new ArrayList<>();

    private int[] listImgProduto = {
            R.drawable.imgstrogonofffrango,
            R.drawable.strogonoffimgpq,
            R.drawable.pao_de_queijo

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



        acessarListaProdutos();

        adapter1 = new AdapterProduto(this, listaProdutos);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);

        recyclerProduto.setAdapter(adapter1);
    }

    private void ListarProduto(){
        Produto produto = new Produto(1,"Marmita Strogonoff de Frango com Batatas",14.5, listaIngrediente[0], listImgProduto[1]);
        listaProdutos.add(produto);

        Produto pedacoBolo = new Produto (2,"Pedaço de bolo de Bolo Cholate com cobertura chocolate", 10.0, listaIngrediente[1],listImgProduto[0]);
        listaProdutos.add(pedacoBolo);

        Produto paoDeQueijo = new Produto(3,"Porção de pao de queijo", 5.00, listaIngrediente[1], listImgProduto[2]);
        listaProdutos.add(paoDeQueijo);

        Produto refriLataNormal = new Produto (4,"Refrigerante lata 350ml", 7.99, listaIngrediente[1], listImgProduto[0]);
        listaProdutos.add(refriLataNormal);

    }

    public void irParaOCarrinho(View view){
        listaProdutos = adapter1.getListaProdutos();
        List<Produto> produtosSelecionados = new ArrayList<>();

        for(Produto produto: listaProdutos){
            if(produto.isSelecionado()){
                produtosSelecionados.add(produto);
            }
        }
        if(produtosSelecionados.size() == 0){
            Toast.makeText(this, "Nenhum produto selecionad", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent i = new Intent(getApplicationContext(), Carrinho.class);
            String produtosComoString = new Gson().toJson(produtosSelecionados);

            i.putExtra("produtosSelecionados", produtosComoString);
            startActivity(i);
        }
    }

    private void acessarListaProdutos(){
        String url = "https://lt3dcj-3000.csb.app/produtos";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if (response.length()>0){
                            for(int i=0; i< response.length();i++){
                                try{
                                    JSONObject jsonObj = response.getJSONObject(i);

                                    int id = jsonObj.getInt("id_produto");
                                    String nomeProduto = jsonObj.getString("nome_produto");
                                    double preco = jsonObj.getDouble("preco");

                                    listaProdutos.add(new Produto(id,nomeProduto,preco, listaIngrediente[0], listImgProduto[0]));


                                }catch (JSONException ex){

                                }
                            }
                            adapter1.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue filaRequest = Volley.newRequestQueue(Produtos.this);
        filaRequest.add(request);
    }



}