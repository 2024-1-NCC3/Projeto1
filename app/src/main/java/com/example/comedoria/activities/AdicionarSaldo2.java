package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdicionarSaldo2 extends AppCompatActivity {
    String idCliente, nomeCliente;
    String accessToken;
    TextView txtNomeCliente, txtIDCliente;
    TextInputEditText inputSaldo;
    AlertDialog avisoAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_saldo2);

        idCliente = getIntent().getStringExtra("IDCliente");
        accessToken = getIntent().getStringExtra("accessToken");
        nomeCliente = getIntent().getStringExtra("nomeCliente");

        txtNomeCliente = findViewById(R.id.txtNomeClienteSaldo);
        txtIDCliente = findViewById(R.id.txtIdClienteSaldo);
        inputSaldo = findViewById(R.id.inputSaldo);

        txtNomeCliente.setText("Cliente: " + nomeCliente);
        txtIDCliente.setText("ID Cliente: " + idCliente);
    }

    public void confirmarEscolha(View view){
        if(inputSaldo.getText().toString() == ""){
            Toast.makeText(this, "Insira um valor para adicionar", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("ATENÇÃO!!!");
            builder.setMessage("Confirme se deseja adicionar " +
                    "\nR$: " + inputSaldo.getText().toString() +
                    "\nCliente: " + nomeCliente);
            builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adicionarSaldo();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            avisoAdicionar = builder.create();
            avisoAdicionar.show();
        }


    }
    private void adicionarSaldo(){
        //Consulta o saldo anterior

        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/usuarios?select=saldo&id_user=eq." + idCliente,
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length()>0){
                            double saldoAnterior = response.getJSONObject(0).getDouble("saldo");
                            atualizarSaldoUsuario(saldoAnterior);
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }

        );

    }
    private void atualizarSaldoUsuario(double saldoAnterior) throws JSONException {

        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");



        ConectorAPI.conexaoArrayPATCH(
                "/rest/v1/usuarios?id_user=eq." + idCliente,
                headers,
                gerarJSONArraySaldo(saldoAnterior),
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        Toast.makeText(AdicionarSaldo2.this, "Saldo adicionado", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(AdicionarSaldo2.this, "Não foi possível adicionar," +
                                " tente novamente", Toast.LENGTH_SHORT).show();
                    }
                }


        );
    }

    private JSONArray gerarJSONArraySaldo(double saldoAnterior) throws JSONException {
        JSONObject saldo = new JSONObject();
        double saldoParaAdicionar = Double.parseDouble(inputSaldo.getText().toString());
        saldo.put("saldo",saldoAnterior + saldoParaAdicionar);

        JSONArray retorno = new JSONArray();
        retorno.put(saldo);

        return retorno;
    }

    public void voltarTelaAddSaldo(View view){
        finish();
    }

    public void voltarInicio(View view){
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

}