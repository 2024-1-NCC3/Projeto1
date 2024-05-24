package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterSaldo;
import com.example.comedoria.Class.Cliente;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.google.android.material.search.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdicionarSaldo extends AppCompatActivity {

    private RecyclerView recyclerSaldo;
    private SearchView searchSaldo;
    private AdapterSaldo adapterSaldo;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<Cliente> listaClienteAux = new ArrayList<>();
    private String accessToken;
    protected void onCreate(Bundle savedInstanceState) {
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela, como o token de acesso e o adapter da RecyclerView*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_saldo);

        accessToken = getIntent().getStringExtra("accessToken");
        adapterSaldo = new AdapterSaldo(listaClienteAux, this);

        recyclerSaldo = findViewById(R.id.recyclerSaldo);
        searchSaldo = findViewById(R.id.searchSaldo);

        configurarPesquisa();

        recyclerSaldo.setAdapter(adapterSaldo);
        recyclerSaldo.setLayoutManager(new LinearLayoutManager(this));
        popularListaClientes();

    }

    /**Carrega as informações da barra de pesquisa*/
    private void configurarPesquisa(){

        EditText inputPesquisa = searchSaldo.getEditText();
        inputPesquisa.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         /**Coleta os dados que estão sendo pesquisados para buscar na lista de clientes*/
         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             String pesquisa = charSequence.toString();
             listaClienteAux.clear();
             if(pesquisa.isEmpty()){
                 listaClienteAux.addAll(listaCliente);
             }
             else{
                 for(Cliente cliente : listaCliente){
                     /**Busca se a pesquisa tem o nome ou o ID de algum cliente*/
                     if (cliente.getNomeCompleto().toLowerCase().contains(pesquisa.toLowerCase())
                             || cliente.getIdCliente().toLowerCase().contains(pesquisa.toLowerCase())) {
                         listaClienteAux.add(cliente);
                     }
                 }
             }
             adapterSaldo.notifyDataSetChanged();


         }

         @Override
         public void afterTextChanged(Editable editable) {

         }
         }
         );
    }

    /**Faz uma requisição para popular a lista de clientes*/
    private void popularListaClientes(){
        /**Configura os headers para a requisição*/
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=*,papel(nome_papel)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            for(int i= 0;i <response.length();i++){
                                JSONObject objCliente = response.getJSONObject(i);
                                 String primeiroNome = objCliente.getString("primeiro_nome");
                                 String ultimoNome = objCliente.getString("ultimo_nome");
                                 String idUser = objCliente.getString("id_user");
                                 listaCliente.add(new Cliente(primeiroNome,ultimoNome,idUser ));
                            }
                        }
                        listaClienteAux.addAll(listaCliente);
                        adapterSaldo.notifyDataSetChanged();
                        Log.i("Lista AUX", listaClienteAux.toString()) ;
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }

        );
    }
    /**Vai para a tela de adicionar saldo, levando as informações do cliente*/
    public void adicionarSaldoParaCliente(String ID, String nome){
        Intent i = new Intent(this,AdicionarSaldo2.class);
        i.putExtra("IDCliente", ID);
        i.putExtra("accessToken", accessToken);
        i.putExtra("nomeCliente", nome);
        startActivity(i);
    }
}