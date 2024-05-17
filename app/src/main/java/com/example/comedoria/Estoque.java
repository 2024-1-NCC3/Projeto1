package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

public class Estoque extends AppCompatActivity {
    private RecyclerView recyclerEstoque;
    String accessToken;
    AdapterEstoque adapterEstoque;

    Button btnAdicionarProduto, btnVoltar;

    List<Produto> produtos;
    private List<Produto> listaProdutos = new ArrayList<>();

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque);

        btnAdicionarProduto = findViewById(R.id.btnAdicionar);
        btnVoltar = findViewById(R.id.btnVoltarEstoque);

        recyclerEstoque = findViewById(R.id.recyclerEstoque);
        accessToken = getIntent().getStringExtra("accessToken");

        acessarListaProdutos();

        adapterEstoque = new AdapterEstoque(this, listaProdutos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this){

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
                        Log.i("Lista", listaProdutos.toString());
                    }
                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    recyclerEstoque.addOnScrollListener(new RecyclerView.OnScrollListener()
    {
        @Override
        public void onScrolled(RecyclerView recyclerEstoque, int dx, int dy)
        {
            if (dy > 0 ||dy<0 && btnAdicionarProduto.isShown())
            {
                btnAdicionarProduto.hide();
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerEstoque, int newState)
        {
            if (newState == RecyclerView.SCROLL_STATE_IDLE)
            {
                fab.show();
            }

            super.onScrollStateChanged(recyclerEstoque, newState);
        }
    });
}