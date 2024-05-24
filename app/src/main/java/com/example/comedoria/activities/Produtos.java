package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.comedoria.Adapter.AdapterProduto;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.ConfirmarPedido;
import com.example.comedoria.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Produtos extends AppCompatActivity {

    // Cores
    //#FF403832 Marrom
    //#FFEEE1 bege
    //#0F5929 verde escuro
    //#94E986 verde claro
    String accessToken, idUsuario, categoriaSelecionada;
    private AdapterProduto adapterProduto;
    private ArrayAdapter adapter2;
    private RecyclerView recyclerProduto;
    private Spinner spinnerOrdenar, spinnerCategoria;

    FloatingActionButton fabHome;
    private List<Produto> listaProdutos = new ArrayList<>();
    private List<Produto> listaFiltrada = new ArrayList<>();
    private List<String> listaCategorias = new ArrayList<>();



    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela, como o token de acesso*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");
        categoriaSelecionada = getIntent().getStringExtra("CategoriaSelecionada");
        iniciarPag();

    }

    /**Direciona para a tela do carrinho para finalizar o pedido*/
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
            i.putExtra("accessToken", accessToken);
            i.putExtra("idUsuario", idUsuario);
            startActivity(i);
        }
    }
    /**Coleta as categorias dos produtos carregados*/
    private void pegarCategorias(List<Produto> lista){
        for(Produto produto: lista){
            for(String categoria: produto.getCategoria()){
                if(!listaCategorias.contains(categoria)){
                    listaCategorias.add(categoria);
                };
            }
        }
    }

    /**Define métodos de ordenação da lista de produtos, que estão alocados nos spinners de filtro*/
    private void ordernarLista(String tipo){
        switch(tipo){
            case "Ordenar":
                break;
            case "Menor preço":
                Collections.sort(listaFiltrada, new ComparadorPreco());
                break;
            case "Maior preço":
                Collections.sort(listaFiltrada, new ComparadorPreco());
                Collections.reverse(listaFiltrada);
                break;
            case "A-Z":
                Collections.sort(listaFiltrada, new ComparadorNome());
                break;
            case "Z-A":
                Collections.sort(listaFiltrada, new ComparadorNome());
                Collections.reverse(listaFiltrada);
                break;
        }
        adapterProduto.notifyDataSetChanged();
    }
    /**A partir do filtro escolhido, filtra a lista visível de produtos*/
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
        adapterProduto.setFilteredList(listaFiltrada);

    }

    /**Requisição para pegar a lista de produtos do banco de dados*/
    private void CarregarListaProdutos(){
        Map<String, String> headers = new HashMap<>();
        String endpoint = "";
        /**define os headers que a solicitação vai precisar*/
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/produtos?select=*,categoria(nome_categoria),estoque(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        converterJsonArray(response);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    /**Volta para a tela anterior*/
    public void voltarTelaProdutos(View view){
        finish();
    }

    /**Volta para a tela inicial do aplicativo*/
    public void voltarInicio(View view){
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    /**Coleta os dados do JSONArray e adiciona numa lista de produtos*/
    private void converterJsonArray(JSONArray response) throws JSONException {
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

                JSONObject estoque = objProduto.getJSONObject("estoque");
                int quantidadeEstoque = estoque.getInt("quantidade");

                List<String> categoriaProduto = new ArrayList<>();

                if(arrayCategoria.length() >0){
                    categoriaProduto.clear();
                    for(int j = 0; j< arrayCategoria.length();j++){
                        JSONObject cat = arrayCategoria.getJSONObject(j);
                        categoriaProduto.add(cat.getString("nome_categoria"));
                    }
                }

                listaProdutos.add(new Produto(id,nomeProduto,preco,categoriaProduto,caminhoImagem, quantidadeEstoque));

            }
        }
        pegarCategorias(listaProdutos);

        if(categoriaSelecionada == null){
            filtrarLista("Todos");
        }else{
            filtrarLista(categoriaSelecionada);
            spinnerCategoria.setSelection(encontrarCategoria(categoriaSelecionada));
        }


        adapter2.notifyDataSetChanged();
    }

    /**Encontra a categoria selecionada*/
    private int encontrarCategoria(String categoriaProcurada){
        for(int i=0;i<listaCategorias.size();i++){
            if(listaCategorias.get(i).equals(categoriaProcurada)){
                return i;
            }
        }
        return 0;
    }

    /**Função para carregar os elementos da tela de produtos a partir dos filtros*/
    private void iniciarPag(){
        listaCategorias.add("Todos");
        CarregarListaProdutos();
        //Configurações do recyclerView
        recyclerProduto = findViewById(R.id.recycleProduto);
        fabHome = findViewById(R.id.btnFlutuante);
        fabHome.setImageTintList(ColorStateList.valueOf(Color.WHITE));

        adapterProduto = new AdapterProduto(this, listaFiltrada);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this,2);
        recyclerProduto.setLayoutManager(layoutManager1);
        recyclerProduto.setHasFixedSize(true);
        recyclerProduto.setAdapter(adapterProduto);

        // Configuração do Dropdown de ordenação
        spinnerOrdenar = findViewById(R.id.spinner_data);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.filtro_ordenar,
                R.layout.produtos_spinner_layout
        );
        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        spinnerOrdenar.setAdapter(arrayAdapter);
        spinnerOrdenar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                ordernarLista(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**Configuração do Dropdown de filtragem*/
        spinnerCategoria = findViewById(R.id.spinner_categoria);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaCategorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter2 = new ArrayAdapter<>(this, R.layout.produtos_spinner_layout, listaCategorias);

        adapter2.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        spinnerCategoria.setAdapter(adapter2);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                filtrarLista(item);
                ordernarLista(spinnerOrdenar.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**Comparadores para fazer a ordenação dos produtos*/
    class ComparadorPreco implements Comparator<Produto>{

        @Override
        public int compare(Produto produto, Produto t1) {
            return Double.compare(produto.getPreco(),t1.getPreco());
        }
    }
    class ComparadorNome implements Comparator<Produto>{
        @Override
        public int compare(Produto produto, Produto t1) {
            return produto.getNome().compareTo(t1.getNome());
        }
    }

    /**Função de abrir os ingredientes do produto ao clicar na sua foto*/
    public void abrirIngredientes(String nome, String caminhoImg, String ingredientes){
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = inflater.inflate(R.layout.sample, null);
        ImageView imageView = view.findViewById(R.id.dialog_imageview);

        Glide.with(this)
                .load(caminhoImg)
                .into(imageView);

        TextView txtIngredientes = view.findViewById(R.id.txtIngredientes);
        TextView txtTitulo = view.findViewById(R.id.txtTitulo);

        txtTitulo.setText(nome);
        txtIngredientes.setText(ingredientes);

        builder.setView(view);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).show();
    }



}