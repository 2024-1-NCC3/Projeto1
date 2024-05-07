package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
    private RecyclerView recyclerView;
    String accessToken;
    AdapterEstoque adapterEstoque;
    private List<Produto> listaProdutos = new ArrayList<>();

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        recyclerView = findViewById(R.id.recyclerEstoque);
        accessToken = getIntent().getStringExtra("accessToken");

        acessarListaProdutos();

        adapterEstoque = new AdapterEstoque(this, listaProdutos);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setHasFixedSize(true);
    }

    private void acessarListaProdutos(){
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*,categoria(nome_categoria)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            for(int i = 0; i< response.length();i++){
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = jsonObject.getInt("id_produto");
                                String nomeProduto = jsonObject.getString("nome_produto");
                                Double preco = jsonObject.getDouble("preco");
                                String caminhoImagem = jsonObject.getString("caminho_imagem");

                                //Pegas as categorias e armazena em uma lista;

                                JSONArray arrayCategorias = jsonObject.getJSONArray("categoria");

                                List<String> categorias = new ArrayList<>();

                                if(arrayCategorias.length() >0){
                                    for(int j = 0; j<arrayCategorias.length();j++){
                                        JSONObject cat = arrayCategorias.getJSONObject(j);
                                        categorias.add(cat.getString("nome_categoria"));
                                    }
                                }

                                listaProdutos.add(new Produto(id,nomeProduto,preco,categorias,caminhoImagem));
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