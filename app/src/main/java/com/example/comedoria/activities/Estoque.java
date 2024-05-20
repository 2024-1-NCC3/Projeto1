package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.comedoria.Adapter.AdapterEstoque;
import com.example.comedoria.BuildConfig;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

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

    private static final String API_URL = BuildConfig.API_URL;

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

    public void atualizarEstoque(View view) throws JSONException {
            Map<String, String> headers = new HashMap<>();
            headers.put("apikey", API_KEY);
            headers.put("Authorization", "Bearer " + accessToken);
            headers.put("Content-Type", "application/json");
            headers.put("Prefer", "minimal");

            JSONObject body = montarRequisicao();
            JSONArray req = new JSONArray();

            req.put(body);

            //url para logar com senha
            String url = API_URL + "/rest/v1/estoque?some_column=eq.someValue";

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.POST,
                    url,
                    req,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length()>0){
                                for(int i=0; i< response.length();i++){
                                    try{
                                        JSONObject jsonObj = response.getJSONObject(i);
                                        //Se o pedido ter uma resposta, verifica se teve sucesso

                                        Boolean sucesso = jsonObj.getBoolean("sucesso");
                                        String msg = jsonObj.getString("msg");

                                        //Manda a mensagem de retorno, pra indicar o status
                                        Toast.makeText(Estoque.this, msg, Toast.LENGTH_SHORT).show();
                                        //Se estever tudo certo, passa para a próxima página
                                        if(sucesso){
                                            Toast.makeText(Estoque.this, msg, Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException ex){

                                    }
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders(){
                    return headers;
                }
            };

        // Adicionar a solicitação à fila de solicitações
        RequestQueue filaRequest = Volley.newRequestQueue(Estoque.this);
        filaRequest.add(request);
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
                                int id = jsonObject.getInt("id_produto");

                                listaProdutos.add(new Produto(id,nomeProduto,preco,caminhoImagem,quantidade));
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

    private JSONObject montarRequisicao()throws JSONException{
        JSONArray listReq = new JSONArray();

        for(Produto produto: listaProdutos){
            JSONObject prod = new JSONObject();
            prod.put("id_produto", produto.getId());
            prod.put("preco", produto.getPreco());
            prod.put("quantidade", produto.getQuantidade());

            listReq.put(prod);
        }

        JSONObject requisicao = new JSONObject();
        requisicao.put("id_usuario", 50);
        requisicao.put("modificado_por", "Vitor");
        requisicao.put("produtos",listReq);

        Log.i("Lista atualizada", requisicao.toString());
        return requisicao;
    }

}