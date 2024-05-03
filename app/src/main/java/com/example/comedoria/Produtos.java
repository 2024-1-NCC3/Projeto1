package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Produtos extends AppCompatActivity{

    // Cores
    //#FF403832 Marrom
    //#FFEEE1 bege
    //#0F5929 verde escuro
    //#94E986 verde claro
    String accessToken;
    private AdapterProduto adapter1;
    private RecyclerView recyclerProduto;    
    private Spinner spinnerOrdenar, spinnerCatalogo;


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


    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        recyclerProduto = findViewById(R.id.recycleProduto);
        accessToken = getIntent().getStringExtra("accessToken");

        acessarListaProdutos();

        adapter1 = new AdapterProduto(this, listaProdutos);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);

        recyclerProduto.setAdapter(adapter1);

        spinnerOrdenar = findViewById(R.id.spinner_ordenar);
                ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                        this,
                        R.array.filtro_ordenar,
                        R.layout.color_spinner_layout
                );
                arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
                spinnerOrdenar.setAdapter(arrayAdapter);
                spinnerOrdenar.setOnItemSelectedListener(this);

        spinnerCatalogo = findViewById(R.id.spinner_categoria);
        ArrayAdapter arrayAdapterCatalogo = ArrayAdapter.createFromResource(
                this,
                R.array.filtro_catalago,
                R.layout.color_spinner_layout
        );
        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        spinnerCatalogo.setAdapter(arrayAdapterCatalogo);
        spinnerCatalogo.setOnItemSelectedListener(this);


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
            Toast.makeText(this, "Nenhum produto selecionado", Toast.LENGTH_SHORT).show();
            return;
        }else{
            Intent i = new Intent(getApplicationContext(), Carrinho.class);
            String produtosComoString = new Gson().toJson(produtosSelecionados);

            i.putExtra("produtosSelecionados", produtosComoString);
            startActivity(i);
        }
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
                        adapter1.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }
}