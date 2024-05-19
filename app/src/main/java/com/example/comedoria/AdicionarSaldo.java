package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
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
    private String accessToken = "eyJhbGciOiJIUzI1NiIsImtpZCI6Ilk4N0NObGFTRldpYUkwdWUiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiJhdXRoZW50aWNhdGVkIiwiZXhwIjoxNzE2MTQ4Njk3LCJpYXQiOjE3MTYxNDUwOTcsImlzcyI6Imh0dHBzOi8vcXBrY2J4Ym56cWJ2bXhlbmpic3Muc3VwYWJhc2UuY28vYXV0aC92MSIsInN1YiI6IjFjZTczMTE2LTVlMDUtNDY3NC1iMDZlLTBlMDY2NTI0Mjk3YSIsImVtYWlsIjoicGVyZmlsZGF0aWFAZW1haWwuY29tIiwicGhvbmUiOiIiLCJhcHBfbWV0YWRhdGEiOnsicHJvdmlkZXIiOiJlbWFpbCIsInByb3ZpZGVycyI6WyJlbWFpbCJdfSwidXNlcl9tZXRhZGF0YSI6e30sInJvbGUiOiJhdXRoZW50aWNhdGVkIiwiYWFsIjoiYWFsMSIsImFtciI6W3sibWV0aG9kIjoicGFzc3dvcmQiLCJ0aW1lc3RhbXAiOjE3MTYxNDUwOTd9XSwic2Vzc2lvbl9pZCI6IjBiMjU5YmNkLTlkNWQtNDY1MC1hZTg0LWY0YWYxZjRkNWY4MSIsImlzX2Fub255bW91cyI6ZmFsc2V9.jGKYZwzmSd2nuPcfGTJQAQZUV73If1M8wF2ZLkZRWM4";    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_saldo);

        adapterSaldo = new AdapterSaldo(listaClienteAux, this);


        recyclerSaldo = findViewById(R.id.recyclerSaldo);
        searchSaldo = findViewById(R.id.searchSaldo);

        configurarPesquisa();

        recyclerSaldo.setAdapter(adapterSaldo);
        popularListaClientes();
        recyclerSaldo.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configurarPesquisa(){

        EditText inputPesquisa = searchSaldo.getEditText();
        inputPesquisa.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

         }

         @Override
         public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             String pesquisa = charSequence.toString();
             listaClienteAux.clear();
             if(pesquisa.isEmpty()){
                 listaClienteAux.addAll(listaCliente);
             }
             else{
                 for(Cliente cliente : listaCliente){
                     //Se a pesquisa tem o Nome ou id de alguém
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

    private void popularListaClientes(){
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
    public void adicionarSaldoParaCliente(String ID, String nome){
        Intent i = new Intent(this,AdicionarSaldo2.class);
        i.putExtra("IDCliente", ID);
        i.putExtra("accessToken", accessToken);
        i.putExtra("nomeCliente", nome);
        startActivity(i);
    }
}