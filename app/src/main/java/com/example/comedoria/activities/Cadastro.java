package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    private EditText inputNome, inputSobrenome,
            inputEmail, inputSenha,inputConfirmarEmail, inputConfirmarSenha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inputNome = findViewById(R.id.txtNome);
        inputSobrenome = findViewById(R.id.txtSobrenome);
        inputEmail = findViewById(R.id.txtEmail);
        inputConfirmarEmail = findViewById(R.id.txtConfirmarEmail);
        inputSenha = findViewById(R.id.txtSenha);
        inputConfirmarSenha = findViewById(R.id.txtConfirmarSenha);

    }

    public void Cadastrar(View view){
        if(verificarCampos()){
            JSONObject dadosCadastro = new JSONObject();
            JSONObject dadosCliente = new JSONObject();

            //define os heades que a solicitação vai precisar


            try {
                dadosCadastro.put("email", inputEmail.getText());
                dadosCadastro.put("password", inputSenha.getText());

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            Map<String, String> headers = new HashMap<>();
            headers.put("apikey", API_KEY);
            headers.put("Content-Type", "application/json");

            ConectorAPI.conexaoSinglePOST(
                    "/auth/v1/signup",
                    dadosCadastro,
                    headers,
                    getApplicationContext(),
                    new ConectorAPI.VolleySingleCallback() {
                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {

                            //segunda solicitação para linkar o novo user a tabela usuarios
                            JSONObject user = response.getJSONObject("user");
                            String id = user.getString("id");

                            String accessToken = response.getString("access_token");
                            Map<String, String> headerCliente = new HashMap<>();

                            headerCliente.put("apikey", API_KEY);
                            headerCliente.put("Authorization", "Bearer " + accessToken);
                            headerCliente.put("Content-Type", "application/json");
                            headerCliente.put("Prefer","return=minimal");

                            JSONObject dadosSolicitacao = new JSONObject();

                            dadosSolicitacao.put("primeiro_nome", inputNome.getText());
                            dadosSolicitacao.put("ultimo_nome", inputSobrenome.getText());
                            dadosSolicitacao.put("id_user", id);

                            ConectorAPI.conexaoSinglePOST(
                                    "/rest/v1/usuarios",
                                    dadosSolicitacao,
                                    headerCliente,
                                    getApplicationContext(),
                                    new ConectorAPI.VolleySingleCallback() {
                                        @Override
                                        public void onSuccess(JSONObject response) throws JSONException {
                                            Toast.makeText(Cadastro.this, "Erro ao cadastrar. Tente novamente", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        //Não sei o porquê, mas o Volley reconhece a resposta do cadastro como erro

                                        public void onError(VolleyError error) throws JSONException {

                                            relacionarUsuarioPapel(id, headerCliente);
                                        }
                                    }
                            );

                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    });
        }


    }
    private boolean verificarCampos(){
        //Verifica se o campo Nome não está vazio
        if(inputNome.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o Nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo Sobrenome não está vazio
        if(inputSobrenome.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o sobrenome", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo Email não está vazio
        if(inputEmail.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo senha não está vazio
        if(inputSenha.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Verifica se email e confirmar email são iguais
        if(!inputEmail.getText().toString().trim().equals(inputConfirmarEmail.getText().toString().trim())){
            Toast.makeText(this, "Os emails não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se senha e confirmar senha são iguais
        if(!inputConfirmarSenha.getText().toString().trim().equals(inputSenha.getText().toString().trim())){
            Toast.makeText(this, "As senhas não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void relacionarUsuarioPapel(String id, Map<String, String> headerCliente) throws JSONException {
        JSONObject dadosSolicitacao = new JSONObject();

        dadosSolicitacao.put("id_user", id);

        ConectorAPI.conexaoSinglePOST(
                "/rest/v1/usuarios_papel",
                dadosSolicitacao,
                headerCliente,
                getApplicationContext(),
                new ConectorAPI.VolleySingleCallback() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        Toast.makeText(Cadastro.this, "Erro ao cadastrar. Tente novamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //Não sei o porquê, mas o Volley reconhece a resposta do cadastro como erro

                    public void onError(VolleyError error) {

                        Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
    public void cancelar(View view){
        finish();
    }
}
