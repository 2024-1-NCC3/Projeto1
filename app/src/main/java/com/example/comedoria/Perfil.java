package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class erfil extends AppCompatActivity {

    RecyclerView recyclerHistorico;
    AdapterHistorico adapterHistorico;
    List<Pedido> historico = new ArrayList<>();
    String accessToken, idUsuario;
    TextView txtSaldo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        recyclerHistorico = findViewById(R.id.recyclerHistorico);
        txtSaldo = findViewById(R.id.txtSaldo);
        adapterHistorico = new AdapterHistorico(historico, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerHistorico.setLayoutManager(layoutManager);
        recyclerHistorico.setHasFixedSize(true);
        recyclerHistorico.setAdapter(adapterHistorico);

        pegarDadosUsuario();
        buscarHistorico();


    }

    private void pegarDadosUsuario() {
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=*",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length()>0){
                            JSONObject usuario = response.getJSONObject(0);
                            double saldo = usuario.getDouble("saldo");

                            txtSaldo.setText(String.format(Locale.getDefault(), "R$ %.2f", saldo));
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }
        );
    }


    private void buscarHistorico(){
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?id_usuario=eq." + idUsuario + "&select=status,observacoes," +
                        "numero_pedido,id_pedido,data_para_retirada,hora_para_retirada,produtos(nome_produto,preco),detalhes_pedido(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length()>0){
                            interpretarJsonArray(response);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }
        );
    }

    private void interpretarJsonArray(JSONArray response) throws JSONException {
        for(int i =0;i<response.length();i++){
            JSONObject objetoPedido = response.getJSONObject(i);
            String data = objetoPedido.getString("data_para_retirada");
            String status = objetoPedido.getString("status");
            String idPedido = objetoPedido.getString("id_pedido");
            JSONArray arrayProdutos = objetoPedido.getJSONArray("produtos");
            JSONArray arrayQuantidade = objetoPedido.getJSONArray("detalhes_pedido");

            List<String> listaProdutos = new ArrayList<>();
            double total = 0;

            for(int j =0; j< arrayProdutos.length();j++){
                JSONObject produto = arrayProdutos.getJSONObject(j);
                JSONObject detalhes = arrayQuantidade.getJSONObject(j);

                String nomeProduto = produto.getString("nome_produto");

                double precoProduto = produto.getDouble("preco");
                int quantidade = detalhes.getInt("quantidade");

                total += precoProduto * quantidade;
                listaProdutos.add(nomeProduto);
            }

            Pedido pedido = new Pedido(data, listaProdutos, total, status, idPedido);
            historico.add(pedido);
        }
        adapterHistorico.notifyDataSetChanged();
    }

    public void irParaPedido(String idPedido){
        Intent i = new Intent(this, ComprovantePedido.class);
        i.putExtra("idPedido",idPedido);
        i.putExtra("accessToken", accessToken);
        startActivity(i);
    }
}