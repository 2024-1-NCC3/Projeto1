package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComprovantePedido extends AppCompatActivity {

    String numPedido;
    TextView textData, tituloPedido, statusPedido;
    String accessToken,idPedido;
    Comprovante comprovante;
    RecyclerView recyclerResumo;
    AdapterResumoPedido adapterResumoPedido;
    List<Produto> produtos = new ArrayList<>();

    int hora, minuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_pedido);

        accessToken = getIntent().getStringExtra("accessToken");
        idPedido = getIntent().getStringExtra("idPedido");

        buscarPedido(idPedido);
        textData = findViewById(R.id.textData);
        tituloPedido = findViewById(R.id.tituloNumPedido);
        statusPedido = findViewById(R.id.statusPedido);

        adapterResumoPedido = new AdapterResumoPedido(produtos, this);
        recyclerResumo = findViewById(R.id.recycleResumo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerResumo.setLayoutManager(layoutManager);
        recyclerResumo.setHasFixedSize(true);

        recyclerResumo.setAdapter(adapterResumoPedido);
    }
  
        numPedido = getIntent().getStringExtra("numPedido");

        tituloPedido.setText(numPedido);
    public void buscarPedido(String idPedido){
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?id_pedido=eq." + idPedido + "&select=*,produtos(nome_produto,preco),detalhes_pedido(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length()>0){
                            JSONObject resposta = response.getJSONObject(0);

                            String status = resposta.getString("status");
                            String observacoes = resposta.getString("observacoes");
                            int numeroPedido = resposta.getInt("numero_pedido");
                            String dataRetirada = resposta.getString("data_para_retirada");
                            String horaRetirada = resposta.getString("hora_para_retirada");

                            JSONArray produtos = resposta.getJSONArray("produtos");
                            JSONArray detalhesPedido = resposta.getJSONArray("detalhes_pedido");
                            List<Produto> listaProdutos = new ArrayList<>();

                            for(int i =0; i< produtos.length();i++){
                                JSONObject item = produtos.getJSONObject(i);
                                JSONObject objetoDetalhes = detalhesPedido.getJSONObject(i);

                                double preco = item.getDouble("preco");
                                String nomeProd = item.getString("nome_produto");
                                int quantidade = objetoDetalhes.getInt("quantidade");

                                listaProdutos.add(new Produto(nomeProd,preco,quantidade));
                            }

                            comprovante = new Comprovante(status,observacoes,numeroPedido,dataRetirada,horaRetirada,listaProdutos);
                            Log.i("Supabase", comprovante.toString());

                            tituloPedido.setText("Pedido nº " +comprovante.getNumeroPedido());
                            statusPedido.setText(comprovante.getStatus());

                            textData.setText("Retirar as: " + comprovante.getDataRetirada() + " " +comprovante.getHoraRetirada());

                            adapterResumoPedido.setListaProduto(comprovante.getListaProdutos());
                            adapterResumoPedido.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }
        );
    }
    private void interpretarJsonArray(JSONArray response){

    }
    public void atualizarLista(List<Produto> listaAtualizada){
        this.produtos = listaAtualizada;
    }





}