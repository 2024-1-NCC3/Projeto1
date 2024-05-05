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

public class Produtos extends AppCompatActivity {

    // Cores
    //#FF403832 Marrom
    //#FFEEE1 bege
    //#0F5929 verde escuro
    //#94E986 verde claro
    String accessToken;
    private AdapterProduto adapter1;
    private ArrayAdapter adapter2;
    private RecyclerView recyclerProduto;    
    private Spinner spinnerOrdenar, spinnerCatalogo;

    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Produto> listaFiltrada = new ArrayList<>();
    private List<String> listaCategorias = new ArrayList<>();



    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);



        recyclerProduto = findViewById(R.id.recycleProduto);
        accessToken = getIntent().getStringExtra("accessToken");

        adapter1 = new AdapterProduto(this, listaFiltrada);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);

        recyclerProduto.setAdapter(adapter1);

        spinnerOrdenar = findViewById(R.id.spinner_ordenar);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerOrdenar = findViewById(R.id.spinner_ordenar);
//        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(Produtos.this, item, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listaCategorias);
//
//        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);

//        spinnerOrdenar.setAdapter(arrayAdapter);


        //Spinner spinner = findViewById(R.id.spinner);
        listaCategorias.add("Todos");
        CarregarListaProdutos();

        adapter2 = new ArrayAdapter<>(this, R.layout.color_spinner_layout, listaCategorias);
        adapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);

        spinnerOrdenar.setAdapter(adapter2);

        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                filtrarLista(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        spinnerCatalogo = findViewById(R.id.spinner_categoria);
//        ArrayAdapter arrayAdapterCatalogo = ArrayAdapter.createFromResource(
//                this,
//                R.array.filtro_catalago,
//                R.layout.color_spinner_layout
//        );
//        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
//        spinnerCatalogo.setAdapter(arrayAdapterCatalogo);
//        spinnerCatalogo.setOnItemSelectedListener(this);
    }

    public void irParaOCarrinho(View view){
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
    private void pegarCategorias(List<Produto> lista){
        for(Produto produto: lista){
            for(String categoria: produto.getCategoria()){
                if(!listaCategorias.contains(categoria)){
                    listaCategorias.add(categoria);
                };
            }
        }
    }

    public void filtrarLista(String categoria){
        listaFiltrada.clear();

        if(categoria.equals("Todos")){
            for(Produto produto: listaProdutos){
                listaFiltrada.add(produto);
            }
        }
        else{
            for(Produto produto: listaProdutos){
                //Se a categoria existe na lista de categorias do produto
                if(produto.getCategoria().contains(categoria)){
                    listaFiltrada.add(produto);
                }
            }
        }
        Log.i("Lista filtrada:", listaFiltrada.toString());
        adapter1.setFilteredList(listaFiltrada);

    }

    private void CarregarListaProdutos(){
        Map<String, String> headers = new HashMap<>();
        String endpoint = "";
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*,categoria(nome_categoria)" ,
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            //listaProdutos.clear();

                            for(int i = 0; i< response.length();i++){
                                JSONObject objProduto = response.getJSONObject(i);

                                //String nomeCategoria = ObjCategoria.getString("nome_categoria");

                                JSONArray arrayCategoria = objProduto.getJSONArray("categoria");

                                int id = objProduto.getInt("id_produto");
                                String nomeProduto = objProduto.getString("nome_produto");
                                Double preco = objProduto.getDouble("preco");
                                String caminhoImagem = objProduto.getString("caminho_imagem");
                                List<String> categoriaProduto = new ArrayList<>();

                                if(arrayCategoria.length() >0){
                                    categoriaProduto.clear();
                                    for(int j = 0; j< arrayCategoria.length();j++){
                                        JSONObject cat = arrayCategoria.getJSONObject(j);
                                        categoriaProduto.add(cat.getString("nome_categoria"));
                                    }
                                }

                                listaProdutos.add(new Produto(id,nomeProduto,preco,categoriaProduto,caminhoImagem));

                            }
                        }
                        pegarCategorias(listaProdutos);

                        filtrarLista("Todos");

                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

}