package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterHistorico;
import com.example.comedoria.Class.Pedido;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Perfil extends AppCompatActivity {

    RecyclerView recyclerHistorico;
    AdapterHistorico adapterHistorico;
    List<Pedido> historico = new ArrayList<>();
    String accessToken, idUsuario;
    TextView txtSaldo, lblData, lblResumo, lblTotal;

    private Spinner spinnerData, spinnerCategoria, spinnerTipo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicialização dos elementos da interface
        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        recyclerHistorico = findViewById(R.id.recyclerHistorico);
        txtSaldo = findViewById(R.id.txtSaldo);
        lblData = findViewById(R.id.lblData);
        lblTotal = findViewById(R.id.lblTotal);

        // Ajuste da largura dos elementos de texto
        TextPaint paintData = lblData.getPaint();
        float tamanhoData = paintData.measureText("00/00/00");
        lblData.setWidth((int) (tamanhoData));

        TextPaint paintResumo = lblTotal.getPaint();
        float tamanhoTotal = paintResumo.measureText("Aguardando ");
        lblTotal.setWidth((int) (tamanhoTotal));

        // Configuração do adaptador e do RecyclerView
        adapterHistorico = new AdapterHistorico(historico, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerHistorico.setLayoutManager(layoutManager);
        recyclerHistorico.setHasFixedSize(true);
        recyclerHistorico.setAdapter(adapterHistorico);

        // Obtenção dos dados do usuário e histórico de pedidos
        pegarDadosUsuario();
        buscarHistorico();
    }

    // Método para obter os dados do usuário
    private void pegarDadosUsuario() {
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=*",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            JSONObject usuario = response.getJSONObject(0);
                            double saldo = usuario.getDouble("saldo");
                            txtSaldo.setText(String.format(Locale.getDefault(), "R$ %.2f", saldo));
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // Tratamento de erro
                    }
                }
        );
    }

    // Método para buscar o histórico de pedidos do usuário
    private void buscarHistorico() {
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?id_usuario=eq." + idUsuario + "&select=status,observacoes," +
                        "numero_pedido,id_pedido,data_para_retirada,hora_para_retirada," +
                        "produtos(nome_produto,preco),detalhes_pedido(quantidade)" +
                        "&order=data_para_retirada.desc",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            interpretarJsonArray(response);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // Tratamento de erro
                    }
                }
        );
    }

    // Método para interpretar o JSONArray de pedidos
    private void interpretarJsonArray(JSONArray response) throws JSONException {
        for(int i = 0; i < response.length(); i++){
            JSONObject objetoPedido = response.getJSONObject(i);
            String data = objetoPedido.getString("data_para_retirada");
            String status = objetoPedido.getString("status");
            String idPedido = objetoPedido.getString("id_pedido");
            JSONArray arrayProdutos = objetoPedido.getJSONArray("produtos");
            JSONArray arrayQuantidade = objetoPedido.getJSONArray("detalhes_pedido");

            List<String> listaProdutos = new ArrayList<>();
            double total = 0;

            for(int j = 0; j < arrayProdutos.length(); j++){
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

    // Método para abrir a tela de detalhes do pedido
    public void irParaPedido(String idPedido) {
        Intent i = new Intent(this, ComprovantePedido.class);
        i.putExtra("idPedido",idPedido);
        i.putExtra("accessToken", accessToken);
        startActivity(i);
    }

    // Método para criar a barra de pesquisa
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pesqui, menu);
        MenuItem menuItem = menu.findItem(R.id.pesqui);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Pesquisar");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Aqui você pode realizar a busca conforme o texto digitado no SearchView
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // Método para sair do perfil
    public void sair(View view) {
        finish();
    }
}
