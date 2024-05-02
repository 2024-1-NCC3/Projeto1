package com.example.comedoria;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cadastro extends AppCompatActivity {

    private EditText inputNome, inputSobrenome, inputCpf, inputEmail, inputSenha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inputNome = findViewById(R.id.txtNome);
        inputSobrenome = findViewById(R.id.txtSobrenome);
        inputCpf = findViewById(R.id.txtCpf);
        inputEmail = findViewById(R.id.txtEmail);
        inputSenha = findViewById(R.id.txtSenha);
    }

    public void Cadastrar(View view){
        String url = "https://lt3dcj-3000.csb.app/cadastro";
        try {
            //Cria o arquivo Json
            JSONObject dadosCadastro = new JSONObject();
            //Adiciona os campos= input e senha ao Json, e define seus valores

            dadosCadastro.put("primeiro_nome", inputNome.getText());
            dadosCadastro.put("ultimo_nome", inputSobrenome.getText());
            dadosCadastro.put("cpf", inputCpf.getText());
            dadosCadastro.put("email", inputEmail.getText());
            dadosCadastro.put("senha", inputSenha.getText());

            JSONArray cadastro = new JSONArray();
            cadastro.put(dadosCadastro);

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.POST,
                    url,
                    cadastro,
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
                                        Toast.makeText(Cadastro.this, msg, Toast.LENGTH_SHORT).show();
                                        //Se estever tudo certo, passa para a próxima página
                                        if(sucesso){
                                            Intent intent = new Intent(Cadastro.this, Login.class);
                                            Log.i("Cadastrou","Foi");
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
            RequestQueue filaRequest = Volley.newRequestQueue(Cadastro.this);
            filaRequest.add(request);
        }catch (JSONException ex){

        }
    }

    public void cancelar(View view){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
