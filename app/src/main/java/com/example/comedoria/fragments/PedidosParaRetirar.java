package com.example.comedoria.fragments;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.content.Intent;
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
import com.example.comedoria.ConfirmarPedido;
import com.example.comedoria.R;
import com.google.gson.Gson;

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
    String accessToken ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accessToken = getActivity().getIntent().getStringExtra("accessToken");
    }

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
        adapterPedido = new AdapterPedido(pedidosNaoFinalizados,this);

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
    private void interpretarJsonArray(JSONArray response) throws JSONException {
        pedidosNaoFinalizados.clear();
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

            Pedido pedido = new Pedido(data, hora, listaProdutos, total, status, idPedido, nome, numeroPedido);
            pedidosNaoFinalizados.add(pedido);
        }
        adapterPedido.notifyDataSetChanged();
    }
    public void irParaConfirmarPedido(Pedido pedido){
        Gson gson = new Gson();
        String jsonString = gson.toJson(pedido);

        Intent i = new Intent(getContext(), ConfirmarPedido.class);
        i.putExtra("pedido", jsonString);
        i.putExtra("accessToken", accessToken);
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        buscarPedidosNaoFinalizados();
    }
}