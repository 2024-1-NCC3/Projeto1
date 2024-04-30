package com.example.comedoria;

import android.content.Intent;
import android.os.Bundle;
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
    private static final String API_URL = BuildConfig.API_URL;
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
    public void Logar(View view){
        String url = API_URL + "/auth/v1/token?grant_type=password";

        try {

            //Cria o arquivo Json
            JSONObject dadosDeSolicitacao = new JSONObject();
            //Adiciona os campos= input e senha ao Json, e define seus valores

            dadosDeSolicitacao.put("email", txtInput.getText());
            dadosDeSolicitacao.put("password", txtSenha.getText());

            JSONArray solicitacao = new JSONArray();
            solicitacao.put(dadosDeSolicitacao);

//            Intent care = new Intent(Login.this, LoadingActivity.class);
//            startActivity(care);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    dadosDeSolicitacao,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // O supabase já verifica se o email foi confirmado

                            try {
                                Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                String acessToken = response.getString("access_token");
                                Intent intent = new Intent(Login.this, PaginaInicial.class);

                                intent.putExtra("accessToken", acessToken);
                                startActivity(intent);


                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
            }){
                @Override
                // define os headers necessários para enviar a solicitação
                // no caso, a chave da API e o tipo de conteúdo
                public Map<String, String> getHeaders() {
                    // Set API key in request headers
                    Map<String, String> headers = new HashMap<>();
                    headers.put("apikey", API_KEY);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            RequestQueue filaRequest = Volley.newRequestQueue(Login.this);
            filaRequest.add(jsonObjectRequest);
        }catch (JSONException ex){

        }
    }
}
