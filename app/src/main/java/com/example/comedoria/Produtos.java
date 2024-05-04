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
    private List<String> listaCategorias = new ArrayList<>();


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

        //acessarListaProdutos();
        filtrarPorCategoria("Todos");

        adapter1 = new AdapterProduto(this, listaProdutos);

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

        List<String> cate = new ArrayList<>();
        cate.add("Marmitas");
        cate.add("Ofertas");
        cate.add("");

        Log.i("ArrayNaMão", cate.toString());

        adapter2 = new ArrayAdapter<>(this, R.layout.color_spinner_layout, listaCategorias);
        adapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);

        spinnerOrdenar.setAdapter(adapter2);
        filtrarPorCategoria("Todos");
        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                filtrarPorCategoria(item);
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

    private void pegarCategorias(List<Produto> produtos){

        for(Produto produto: produtos){
            for(int i = 0; i<produto.getCategorias().size();i++){
                listaCategorias.add(produto.getCategorias().get(i).toString());
            }
        }
    }

    private void filtrarPorCategoria(String categoria){
        Map<String, String> headers = new HashMap<>();
        String endpoint = "";
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        if(categoria.equals("Todos")){
            endpoint = "/rest/v1/categoria?select=nome_categoria,produtos(id_produto,nome_produto,preco,caminho_imagem)";

        }
        else{
            endpoint = "/rest/v1/categoria?nome_categoria=eq."+categoria+"&select=nome_categoria,produtos(id_produto,nome_produto,preco,caminho_imagem)";
        }

        ConectorAPI.conexaoArrayGET(
                endpoint ,
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        Log.i("Supabase",response.toString());
                        if(response.length() > 0){
                            listaProdutos.clear();

                            for(int i = 0; i< response.length();i++){
                                JSONObject ObjCategoria = response.getJSONObject(i);
                                String nomeCategoria = ObjCategoria.getString("nome_categoria");

                                JSONArray arrayProdutos = ObjCategoria.getJSONArray("produtos");

                                if(arrayProdutos.length() >0){

                                    for(int j = 0; j< arrayProdutos.length();j++){
                                        JSONObject prod = arrayProdutos.getJSONObject(j);

                                        int id = prod.getInt("id_produto");
                                        String nomeProduto = prod.getString("nome_produto");
                                        Double preco = prod.getDouble("preco");
                                        String caminhoImagem = prod.getString("caminho_imagem");

                                        boolean idExiste = false;

                                        for(Produto produto: listaProdutos){
                                            if(produto.getId() == id){
                                                idExiste = true;
                                            }
                                        }

                                        if(!idExiste){
                                            listaProdutos.add(new Produto(nomeProduto,id,preco,caminhoImagem));
                                        }
                                    }
                                }

                                if(categoria.equals("Todos") && !listaCategorias.contains(nomeCategoria)){
                                    listaCategorias.add(nomeCategoria);
                                }
                            }
                        }
                        if( !listaCategorias.contains("Todos")){
                            listaCategorias.add("Todos");
                        }

                        Log.i("Supabase", listaCategorias.toString());
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

}