package com.example.comedoria.fragments;

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
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PedidosRetirados extends Fragment {
    private RecyclerView recyclerPedidosRetirados;
    private List<Pedido> pedidosRetirados = new ArrayList<>();

    AdapterPedido adapterPedido;
    String accessToken;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessToken = getActivity().getIntent().getStringExtra("accessToken");
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pedidos_retirados, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela, como o token de acesso e o adapter da RecyclerView*/
        super.onViewCreated(view, savedInstanceState);
        recyclerPedidosRetirados = view.findViewById(R.id.recyclerRetirados);
        adapterPedido = new AdapterPedido(pedidosRetirados,this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerPedidosRetirados.setLayoutManager(layoutManager);
        recyclerPedidosRetirados.setHasFixedSize(true);
        recyclerPedidosRetirados.setAdapter(adapterPedido);

        buscarPedidosNaoFinalizados();

    }

    /**Requisição para buscar pedidos com status de já retirado*/
    private void buscarPedidosNaoFinalizados(){
        Map<String, String> headers = new HashMap<>();
        /**define os headers que a solicitação vai precisar*/
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?status=eq.Retirado" +
                        "&select=status,observacoes,numero_pedido,id_pedido,data_para_retirada,hora_para_retirada,numero_pedido" +
                        ",produtos(nome_produto,preco)" +
                        ",detalhes_pedido(quantidade)," +
                        "usuarios(primeiro_nome,ultimo_nome)" +
                        "&order=data_para_retirada.asc,hora_para_retirada.asc",
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

    /**Transforma o JSONArray em algo que o aplicativo entenda, como uma lista*/
    private void interpretarJsonArray(JSONArray response) throws JSONException {
        Log.i("Lista", pedidosRetirados.toString());
        for(int i =0;i<response.length();i++){
            Log.i("Reposta",response.toString());

            JSONObject objetoPedido = response.getJSONObject(i);

            String data = objetoPedido.getString("data_para_retirada");
            String hora = objetoPedido.getString("hora_para_retirada");
            String status = objetoPedido.getString("status");
            String idPedido = objetoPedido.getString("id_pedido");
            JSONArray arrayProdutos = objetoPedido.getJSONArray("produtos");
            JSONArray arrayQuantidade = objetoPedido.getJSONArray("detalhes_pedido");

            JSONObject usuario = objetoPedido.getJSONObject("usuarios");
            String primeiroNome = usuario.getString("primeiro_nome");
            String ultimoNome = usuario.getString("ultimo_nome");

            int numeroPedido = objetoPedido.getInt("numero_pedido");

            String nome = "";
            if(ultimoNome == null || ultimoNome == "null"){
                nome = primeiroNome;
            }else{
                nome = primeiroNome + " " + ultimoNome;
            }

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

            Pedido pedido = new Pedido(data, hora, listaProdutos, total, status, idPedido,nome, numeroPedido);
            pedidosRetirados.add(pedido);
        }
        adapterPedido.notifyDataSetChanged();
    }
}