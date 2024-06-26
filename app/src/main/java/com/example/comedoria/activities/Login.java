package com.example.comedoria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.comedoria.BuildConfig;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

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
        /**Configura as variáveis que precisam ser trazidas ao iniciar a tela*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Atribuir os inputs para login
        txtInput = findViewById(R.id.txtEmail);
        txtSenha = findViewById(R.id.txtSenha);
        definirListenerDoEmail();
    }

    /**Função que leva para a tela de cadastro*/
    public void cadastro(View view){
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
    }

    /**Função que monta a requisição de logar com o email e senha inseridos*/
    public void Logar(View view) throws JSONException {
        /**Configura os headers para a requisição*/
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Content-Type", "application/json");

        JSONObject dadosDeSolicitacao = new JSONObject();

        /**Adiciona os campos email e senha ao JSON*/
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
                /**Verificar se quem logou é cliente ou funcionário ou cliente*/
                Map<String, String> headers = new HashMap<>();

                String acessToken = response.getString("access_token");
                JSONObject user = response.getJSONObject("user");
                String idUsuario = user.getString("id");

                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + acessToken);

                ConectorAPI.conexaoArrayGET(
                        "/rest/v1/usuarios?select=*&id_user=eq." + idUsuario,
                        headers, getApplicationContext(),
                        new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length() > 0){
                            JSONObject resposta = response.getJSONObject(0);
                            int papel = resposta.getInt("id_papel");

                            /**Se for cliente, vai para a Página Inicial de cliente*/
                            if(papel == 2){
                                Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, PaginaInicial.class);
                                intent.putExtra("accessToken", acessToken);
                                intent.putExtra("idUsuario",idUsuario);
                                startActivity(intent);
                                finish();
                            }/**Se for funcionário, vai para a Página Inicial de funcionário*/
                            else if(papel == 1){
                                Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, perfilAdm.class);
                                intent.putExtra("accessToken", acessToken);
                                startActivity(intent);
                                finish();
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

    /**Verifica se o email está no padrão correto*/
    private void definirListenerDoEmail(){
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    txtInput.setError("Email inválido");
                }
            }
        });
    }
}
