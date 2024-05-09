package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estoque extends AppCompatActivity {
    private RecyclerView recyclerEstoque;
    String accessToken;
    AdapterEstoque adapterEstoque;
    private List<Produto> listaProdutos = new ArrayList<>();

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        recyclerEstoque = findViewById(R.id.recyclerEstoque);
        accessToken = getIntent().getStringExtra("accessToken");

        acessarListaProdutos();

        adapterEstoque = new AdapterEstoque(this, listaProdutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this){
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerEstoque.setLayoutManager(layoutManager);
        recyclerEstoque.setHasFixedSize(true);

        recyclerEstoque.setAdapter(adapterEstoque);


    }

    private void acessarListaProdutos(){
        Map<String, String> headers = new HashMap<>();
        //define os headers que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*,estoque(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            for(int i = 0; i< response.length();i++){
                                JSONObject jsonObject = response.getJSONObject(i);

                                String nomeProduto = jsonObject.getString("nome_produto");
                                Double preco = jsonObject.getDouble("preco");
                                JSONObject estoque = jsonObject.getJSONObject("estoque");
                                int quantidade = estoque.getInt("quantidade");
                                String caminhoImagem = jsonObject.getString("caminho_imagem");

                                listaProdutos.add(new Produto(nomeProduto,preco,caminhoImagem,quantidade));
                            }
                        }
                        adapterEstoque.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    public void enviar(){
        Map<String, String> headers = new HashMap<>();
        //define os headers que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*,estoque(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            for(int i = 0; i< response.length();i++){
                                JSONObject jsonObject = response.getJSONObject(i);

                                String nomeProduto = jsonObject.getString("nome_produto");
                                Double preco = jsonObject.getDouble("preco");
                                JSONObject estoque = jsonObject.getJSONObject("estoque");
                                int quantidade = estoque.getInt("quantidade");
                                String caminhoImagem = jsonObject.getString("caminho_imagem");

                                listaProdutos.add(new Produto(nomeProduto,preco,caminhoImagem,quantidade));
                            }
                        }
                        adapterEstoque.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }
}