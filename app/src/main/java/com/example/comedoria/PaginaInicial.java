package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;
import static com.example.comedoria.BuildConfig.API_URL;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaginaInicial extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private AdapterCategoria adapter;
    private String accessToken;

    private int[] listaImg = {
            R.drawable.promocao,
            R.drawable.bebidassazonais,
            R.drawable.delivery_capa
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        accessToken = getIntent().getStringExtra("accessToken");

        recycleView = findViewById(R.id.recycleView);

        carregarCategorias();
        adapter = new AdapterCategoria(listaCategorias);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setHasFixedSize(true);

        recycleView.setAdapter(adapter);


    }

    public void log(){
        Intent home = new Intent(PaginaInicial.this, Login.class);
        startActivity(home);
    }

    public void cardapio(){
        Intent cardapio = new Intent();
        startActivity(cardapio);
    }
    public void carrinho(){
        Intent carrinho = new Intent();
        startActivity(carrinho);
    }
    public void usuario(){
        Intent usuario = new Intent();
        startActivity(usuario);
    }

    private void carregarCategorias(){
//        String url = API_URL + "/rest/v1/categoria?select=*";

        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/categoria?select=*",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) throws JSONException {
                if (response.length()>0){
                    for(int i=0; i< response.length();i++){
                        try{
                            JSONObject jsonObj = response.getJSONObject(i);

                            Boolean aparecer = jsonObj.getBoolean("aparecer_na_pg_inicial");
                            String nome = jsonObj.getString("nome_categoria");
                            String descricao = jsonObj.getString("descricao_pg_inicial");
                            String urlImagem = jsonObj.getString("caminho_imagem");

                            Categoria categoria = new Categoria(nome,descricao,urlImagem, aparecer);
                            listaCategorias.add(categoria);


                        }catch (JSONException ex){

                        }
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(PaginaInicial.this, "Erro ao carregar lista", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }

