package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.comedoria.Adapter.SpinnerAdapterCategoria;
import com.example.comedoria.Class.Categoria;
import com.example.comedoria.databinding.ActivityAdicionarProdutosBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private ActivityAdicionarProdutosBinding bindingAddProduto;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;

    private ImageView imgAddProduto;

    private Spinner spinnerAddProduto;

    String nomeProduto;
    double preco;
    String ingrediente, accessToken;
    int quantidade;
    String categoria;
    int  idCategoria;

    List<Categoria> categorias = new ArrayList<>();
    SpinnerAdapterCategoria adapterSpinner;

    Uri nSelectedUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingAddProduto = ActivityAdicionarProdutosBinding.inflate(getLayoutInflater());
        setContentView(bindingAddProduto.getRoot());

        accessToken = getIntent().getStringExtra("accessToken");
        inputNomeProduto = findViewById(R.id.textInputNomeProduto);
        inputPreco = findViewById(R.id.textInputPreco);
        inputIngrediente = findViewById(R.id.textInputDingredientes);
        inputQnt = findViewById(R.id.textInputQuantidade);
        spinnerAddProduto = findViewById(R.id.spinnerAddProduto);

        idCategoria = -1;

        adapterSpinner = new SpinnerAdapterCategoria(this,categorias);

        spinnerAddProduto.setAdapter(adapterSpinner);
        spinnerAddProduto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idCategoria = categorias.get(i).getIdCategoria();
               }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        popularSpinner();
    }

    public void finalizarCadastroProduto(View view) throws JSONException {

        if(inputNomeProduto.getText().toString().equals("")){
            Toast.makeText(this, "Digite o nome do produto", Toast.LENGTH_SHORT).show();
            return;
        }else{
            nomeProduto = inputNomeProduto.getText().toString();
        }

        if(inputPreco.getText().toString().equals("")){
            Toast.makeText(this, "Digite o preço do produto", Toast.LENGTH_SHORT).show();
            return;
        }else{

            preco = Double.parseDouble(inputPreco.getText().toString());
        }

        if(inputQnt.getText().toString().equals("")){
            quantidade = 0;
        }else{
            quantidade = Integer.parseInt(inputQnt.getText().toString());
        }

        ingrediente = inputIngrediente.getText().toString();

       if(idCategoria == -1){
           Toast.makeText(this, "Selecione uma Categoria", Toast.LENGTH_SHORT).show();
           return;
       }


       adicionarProduto(nomeProduto,preco,ingrediente,quantidade,idCategoria);

        //cria um arquivo para armazenar esse dados
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nomeProduto", nomeProduto);
        editor.putFloat("preço", (float) preco);
        editor.putString("Ingredientes", ingrediente);
        editor.putInt("Quantidade", quantidade);
        editor.putString("categoria", categoria);
        editor.commit();


    }
    public void CarregarImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            nSelectedUri = data.getData();
            imgAddProduto.setImageURI(nSelectedUri);
        }
    }

    private void adicionarProduto(String nome, double preco, String ingredientes, int estoqueInicial, int idCategoria) throws JSONException {
        // primeiro temos que criar um estoque
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");


        JSONObject criarEstoque = new JSONObject();
        criarEstoque.put("quantidade", estoqueInicial);

        JSONArray requisicaoEstoque = new JSONArray();
        requisicaoEstoque.put(criarEstoque);

        ConectorAPI.conexaoArrayPOST(
                "/rest/v1/estoque",
                headers,
                requisicaoEstoque,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        JSONObject resposta = response.getJSONObject(0);

                        int idEstoque = resposta.getInt("id_estoque");

                        JSONObject criarProduto = new JSONObject();
                        criarProduto.put("nome_produto", nome);
                        criarProduto.put("preco", preco);
                        criarProduto.put("id_estoque", idEstoque);
                        criarProduto.put("ingredientes", ingredientes);

                        JSONArray requisicaoProdutos = new JSONArray();
                        requisicaoProdutos.put(criarProduto);

                        ConectorAPI.conexaoArrayPOST(
                                "/rest/v1/produtos",
                                headers,
                                requisicaoProdutos,
                                getApplicationContext(),
                                new ConectorAPI.VolleyArrayCallback() {
                                    @Override
                                    public void onSuccess(JSONArray response) throws JSONException {
                                        JSONObject resposta = response.getJSONObject(0);

                                        int idProduto = resposta.getInt("id_produto");

                                        JSONObject criarCategoriaProduto = new JSONObject();
                                        criarCategoriaProduto.put("id_produto", idProduto);
                                        criarCategoriaProduto.put("id_categoria", idCategoria);

                                        JSONArray requisicaoRelacao = new JSONArray();
                                        requisicaoRelacao.put(criarCategoriaProduto);

                                        ConectorAPI.conexaoArrayPOST(
                                                "/rest/v1/categoria_produto",
                                                headers,
                                                requisicaoRelacao,
                                                getApplicationContext(),
                                                new ConectorAPI.VolleyArrayCallback() {
                                                    @Override
                                                    public void onSuccess(JSONArray response) throws JSONException {
                                                        Toast.makeText(Activity_Adicionar_Produtos.this, "Produto criado com sucesso", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onError(VolleyError error) throws JSONException {

                                                    }
                                                }
                                        );
                                    }

                                    @Override
                                    public void onError(VolleyError error) throws JSONException {
                                    }
                                }
                        );
                    }

                    @Override
                    public void onError(VolleyError error) throws JSONException {
                        if (error.networkResponse != null) {
                            System.out.println("Error Status Code: " + error.networkResponse.statusCode);
                            System.out.println("Error Response Data: " + new String(error.networkResponse.data));
                        } else {
                            error.printStackTrace();
                        }
                    }
                }
        );

    }

    private void popularSpinner(){
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/categoria?select=id_categoria,nome_categoria",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        categorias.add(new Categoria("Categoria", -1));

                        for(int i = 0; i< response.length();i++){
                            JSONObject objCategoria = response.getJSONObject(i);
                            int idCategoria = objCategoria.getInt("id_categoria");
                            String nomeCategoria = objCategoria.getString("nome_categoria");

                            categorias.add(new Categoria(nomeCategoria, idCategoria));
                        }

                        Iterator<Categoria> iterator = categorias.iterator();
                        while (iterator.hasNext()) {
                            Categoria categoria = iterator.next();
                            if (categoria.getNome().equals("Populares") || categoria.getNome().equals("Ofertas")) {
                                iterator.remove();
                            }
                        }

                        adapterSpinner.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) throws JSONException {

                    }
                }

        );
    }

    public void voltarTelaAddProdutos(View view){
        finish();
    }
}





