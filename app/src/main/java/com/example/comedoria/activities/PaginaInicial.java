package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterCategoria;
import com.example.comedoria.Class.Categoria;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

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
    private String accessToken,idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela, como o token de acesso e o adapter da RecyclerView*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);
        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        recycleView = findViewById(R.id.recycleView);

        carregarCategorias();
        adapter = new AdapterCategoria(listaCategorias, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setHasFixedSize(true);

        recycleView.setAdapter(adapter);


    }

    /**Botão que vai diretamente para o login*/
    public void log(){
        Intent home = new Intent(PaginaInicial.this, Login.class);
        startActivity(home);
    }

    /**Vai para a tela do cardápio e carrega todos os produtos da comedoria*/
    public void cardapio(View view){
        irParaProdutos("Todos");
    }

    /**Vai para a tela do cardápio filtrando pela categoria escolhida*/
    public void irParaProdutos(String categoria){
        Intent cardapio = new Intent(this,Produtos.class);
        cardapio.putExtra("accessToken",accessToken);
        cardapio.putExtra("idUsuario",idUsuario);
        cardapio.putExtra("CategoriaSelecionada",categoria);

        startActivity(cardapio);
    }

    public void carrinho(){
        Intent carrinho = new Intent();
        startActivity(carrinho);
    }

    /**Vai para a tela do perfil e carrega as informações do usuário*/
    public void usuario(View view){
        Intent usuario = new Intent(this,Perfil.class);
        usuario.putExtra("idUsuario",idUsuario);
        usuario.putExtra("accessToken", accessToken);
        startActivity(usuario);
    }

    /**Monta uma requisição para filtrar os produtos a partir da categoria selecionada*/
    private void carregarCategorias(){
        /**Configura os headers para a requisição*/
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/categoria?select=*&order=prioridade",
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
    public String getAccessToken(){
        return accessToken;
    };
    }

