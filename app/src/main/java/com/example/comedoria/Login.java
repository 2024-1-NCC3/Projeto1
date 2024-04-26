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

public class Login extends AppCompatActivity {
    EditText txtInput, txtSenha;
    RequestQueue filaRequest;
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
        String url = "https://lt3dcj-3000.csb.app/login";
        try {
            //Cria o arquivo Json
            JSONObject dadosDeSolicitacao = new JSONObject();
            //Adiciona os campos= input e senha ao Json, e define seus valores

            dadosDeSolicitacao.put("input", txtInput.getText());
            dadosDeSolicitacao.put("senha", txtSenha.getText());

            JSONArray solicitacao = new JSONArray();
            solicitacao.put(dadosDeSolicitacao);

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.POST,
                    url,
                    solicitacao,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length()>0){
                                for(int i=0; i< response.length();i++){
                                    try{
                                        JSONObject jsonObj = response.getJSONObject(i);
                                        //Se o pedido ter uma resposta, verifica se teve sucesso
                                        Boolean sucesso = jsonObj.getBoolean("auth");
                                        String msg = jsonObj.getString("msg");

                                        //Manda a mensagem de retorno, pra indicar o status
                                        Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                                        //Se estever tudo certo, passa para a próxima página
                                        if(sucesso){
                                            Intent intent = new Intent(Login.this, Cadastro.class);
                                            startActivity(intent);
                                        }
                                    }catch (JSONException ex){

                                    }
                                }

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            RequestQueue filaRequest = Volley.newRequestQueue(Login.this);
            filaRequest.add(request);
        }catch (JSONException ex){

        }



    }
}
