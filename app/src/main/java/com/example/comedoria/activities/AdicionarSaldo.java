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

/**
 * Atividade para adicionar saldo à conta de um cliente.
 */
public class AdicionarSaldo extends AppCompatActivity {

    private RecyclerView recyclerSaldo;
    private SearchView searchSaldo;
    private AdapterSaldo adapterSaldo;
    private List<Cliente> listaCliente = new ArrayList<>();
    private List<Cliente> listaClienteAux = new ArrayList<>();
    private String accessToken;

    /**
     * Chamado quando a atividade é criada pela primeira vez.
     *
     * @param savedInstanceState Se a atividade está sendo re-inicializada após ser previamente finalizada, então este Bundle contém os dados que ela forneceu mais recentemente em onSaveInstanceState(Bundle). Caso contrário, está nulo.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    /**
     * Configura a funcionalidade de pesquisa para filtrar a lista de clientes com base na entrada do usuário.
     */
    private void configurarPesquisa() {
        EditText inputPesquisa = searchSaldo.getEditText();
        inputPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Nenhuma ação necessária antes da alteração do texto
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String pesquisa = charSequence.toString();
                listaClienteAux.clear();
                if (pesquisa.isEmpty()) {
                    listaClienteAux.addAll(listaCliente);
                } else {
                    for (Cliente cliente : listaCliente) {
                        // Se a string de pesquisa corresponde ao nome ou ID do cliente, adiciona à lista filtrada
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
                // Nenhuma ação necessária após a alteração do texto
            }
        });
    }

    /**
     * Popula a lista de clientes buscando dados da API.
     */
    private void popularListaClientes() {
        Map<String, String> headers = new HashMap<>();
        // Define os headers necessários para a solicitação da API
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=*,papel(nome_papel)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if (response.length() > 0) {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject objCliente = response.getJSONObject(i);
                                String primeiroNome = objCliente.getString("primeiro_nome");
                                String ultimoNome = objCliente.getString("ultimo_nome");
                                String idUser = objCliente.getString("id_user");
                                listaCliente.add(new Cliente(primeiroNome, ultimoNome, idUser));
                            }
                        }
                        listaClienteAux.addAll(listaCliente);
                        adapterSaldo.notifyDataSetChanged();
                        Log.i("Lista AUX", listaClienteAux.toString());
                    }

                    @Override
                    public void onError(VolleyError error) {
                        // Lidar com o erro
                    }
                }
        );
    }

    /**
     * Inicia a atividade para adicionar saldo a um cliente específico.
     *
     * @param ID   O ID do cliente.
     * @param nome O nome do cliente.
     */
    public void adicionarSaldoParaCliente(String ID, String nome) {
        Intent i = new Intent(this, AdicionarSaldo2.class);
        i.putExtra("IDCliente", ID);
        i.putExtra("accessToken", accessToken);
        i.putExtra("nomeCliente", nome);
        startActivity(i);
    }
}
