package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterPedido;
import com.example.comedoria.Class.Pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PedidosParaRetirar extends Fragment {
    private RecyclerView recyclerPedidosParaRetirar;
    private List<Pedido> pedidosNaoFinalizados = new ArrayList<>();

    AdapterPedido adapterPedido;

    String accessToken = "eyJhbGciOiJIUzI1NiIsImtpZCI6Ilk4N0NObGFTRldpYUkwdWUiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNzE1ODg2MzA0LCJpYXQiOjE3MTU4ODI3MDQsImlzcyI6Imh0dHBzOi8vcXBrY2J4Ym56cWJ2bXhlbmpic3Muc3VwYWJhc2UuY28vYXV0aC92MSIsInN1YiI6IjFjZTczMTE2LTVlMDUtNDY3NC1iMDZlLTBlMDY2NTI0Mjk3YSIsImVtYWlsIjoicGVyZmlsZGF0aWFAZW1haWwuY29tIiwicGhvbmUiOiIiLCJhcHBfbWV0YWRhdGEiOnsicHJvdmlkZXIiOiJlbWFpbCIsInByb3ZpZGVycyI6WyJlbWFpbCJdfSwidXNlcl9tZXRhZGF0YSI6e30sInJvbGUiOiJhdXRoZW50aWNhdGVkIiwiYWFsIjoiYWFsMSIsImFtciI6W3sibWV0aG9kIjoicGFzc3dvcmQiLCJ0aW1lc3RhbXAiOjE3MTU4ODI3MDR9XSwic2Vzc2lvbl9pZCI6IjM4N2I4ZmNjLWI0YTAtNGFiZC1iNTE0LWZkYzYyOThmMWQ3NiIsImlzX2Fub255bW91cyI6ZmFsc2V9.1nFu63e1KNZ3lXF-WzkEPsnDxi6FtrEz-PGo2irPEM4";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_para_retirar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerPedidosParaRetirar = view.findViewById(R.id.recyclerPedidosParaRetirar);
        adapterPedido = new AdapterPedido(pedidosNaoFinalizados,getContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerPedidosParaRetirar.setLayoutManager(layoutManager);
        recyclerPedidosParaRetirar.setHasFixedSize(true);
        recyclerPedidosParaRetirar.setAdapter(adapterPedido);

        buscarPedidosNaoFinalizados();

    }

    private void buscarPedidosNaoFinalizados(){
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?status=eq.Aguardando Pagamento" +
                        "&select=status,observacoes,numero_pedido,id_pedido,data_para_retirada,hora_para_retirada" +
                        ",produtos(nome_produto,preco)" +
                        ",detalhes_pedido(quantidade)",
                headers,
                requireContext(),
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
        Log.i("Lista", pedidosNaoFinalizados.toString());
        for(int i =0;i<response.length();i++){
            Log.i("Reposta",response.toString());

            JSONObject objetoPedido = response.getJSONObject(i);

            String data = objetoPedido.getString("data_para_retirada");
            String hora = objetoPedido.getString("hora_para_retirada");
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

            Pedido pedido = new Pedido(data, hora, listaProdutos, total, status, idPedido);
            pedidosNaoFinalizados.add(pedido);
        }
        adapterPedido.notifyDataSetChanged();
    }
}