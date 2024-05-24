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

/**
 * Activity para a página inicial que exibe categorias.
 */
public class PaginaInicial extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Categoria> listaCategorias = new ArrayList<>();
    private AdapterCategoria adapter;
    private String accessToken, idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);

        // Recupera o token de acesso e o ID do usuário dos extras do intent
        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        // Inicializa RecyclerView e Adapter
        recyclerView = findViewById(R.id.recycleView);
        adapter = new AdapterCategoria(listaCategorias, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        // Carrega as categorias da API
        carregarCategorias();
    }

    /**
     * Método para navegar para a página de lista de produtos com a categoria especificada.
     *
     * @param categoria A categoria para filtrar os produtos.
     */
    public void irParaProdutos(String categoria) {
        Intent cardapio = new Intent(this, Produtos.class);
        cardapio.putExtra("accessToken", accessToken);
        cardapio.putExtra("idUsuario", idUsuario);
        cardapio.putExtra("CategoriaSelecionada", categoria);
        startActivity(cardapio);
    }

    /**
     * Busca e exibe a lista de categorias da API.
     */
    private void carregarCategorias() {
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
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObj = response.getJSONObject(i);
                                Boolean aparecer = jsonObj.getBoolean("aparecer_na_pg_inicial");
                                String nome = jsonObj.getString("nome_categoria");
                                String descricao = jsonObj.getString("descricao_pg_inicial");
                                String urlImagem = jsonObj.getString("caminho_imagem");

                                Categoria categoria = new Categoria(nome, descricao, urlImagem, aparecer);
                                listaCategorias.add(categoria);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(PaginaInicial.this, "Nenhuma categoria encontrada", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(PaginaInicial.this, "Erro ao carregar categorias", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Outros métodos...
}
