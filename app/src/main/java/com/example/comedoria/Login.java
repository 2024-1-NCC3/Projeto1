package com.example.comedoria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText txtInput, txtSenha;
    RequestQueue filaRequest;
    private static final String API_KEY = BuildConfig.API_KEY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Atribuir os inputs para login
        txtInput = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
    }
    public void cadastro(View view){
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
    }
    public void Logar(View view) throws JSONException {
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Content-Type", "application/json");

        JSONObject dadosDeSolicitacao = new JSONObject();
        //Adiciona os campos= input e senha ao Json, e define seus valores

        dadosDeSolicitacao.put("email", txtInput.getText());
        dadosDeSolicitacao.put("password", txtSenha.getText());

        ConectorAPI.conexaoSinglePOST(
                "/auth/v1/token?grant_type=password",
                dadosDeSolicitacao,
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleySingleCallback() {
            @Override
            public void onSuccess(JSONObject response) throws JSONException {
                //Verificar se quem logou é cliente ou funcionário
                Map<String, String> headers = new HashMap<>();

                String acessToken = response.getString("access_token");

                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + acessToken);



                ConectorAPI.conexaoArrayGET(
                        "/rest/v1/usuarios?select=*",
                        headers, getApplicationContext(),
                        new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            JSONObject resposta = response.getJSONObject(0);
                            int papel = resposta.getInt("id_papel");

                            //se for cliente, vai para a página Inicial
                            if( papel == 2){
                                Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, PaginaInicial.class);
                                intent.putExtra("accessToken", acessToken);
                                startActivity(intent);
                            }else{
                                //Todo: Conectar a página de funcionário
                                Toast.makeText(Login.this, "Ir para a página de func", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(Login.this, PaginaInicial.class);
                            }
                        }
                    }
                    @Override
                    public void onError(VolleyError error) {

                    }
                });


            }

            @Override
            public void onError(VolleyError error) {
                //se a resposta for um erro, irá apresentar um Toast com o erro
                String body = null;
                try {
                    body = new String(error.networkResponse.data, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                JSONObject data = null;
                try {
                    data = new JSONObject(body);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String message = data.optString("error_description");
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
