package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Add_Funcionario extends AppCompatActivity {

    private EditText inputNomeFun, inputSobrenomeFun,

    inputEmailFun, inputSenhaFun,inputConfirmarEmailFun, inputConfirmarSenhaFun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_funcionario);

        inputNomeFun = findViewById(R.id.txtNome);
        inputSobrenomeFun = findViewById(R.id.txtSobrenome);
        inputEmailFun = findViewById(R.id.txtEmail);
        inputConfirmarEmailFun = findViewById(R.id.txtConfirmarEmail);
        inputSenhaFun = findViewById(R.id.txtSenha);
        inputConfirmarSenhaFun = findViewById(R.id.txtConfirmarSenha);

        definirListenerDoEmail();

    }

    public void Cadastrar(View view){
        if(verificarCamposFun()){
            JSONObject dadosCadastro = new JSONObject();
            JSONObject dadosCliente = new JSONObject();

            //define os heades que a solicitação vai precisar


            try {
                dadosCadastro.put("email", inputEmailFun.getText());
                dadosCadastro.put("password", inputSenhaFun.getText());

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

                            dadosSolicitacao.put("primeiro_nome", inputNomeFun.getText());
                            dadosSolicitacao.put("ultimo_nome", inputSobrenomeFun.getText());
                            dadosSolicitacao.put("id_papel", 1);
                            dadosSolicitacao.put("id_user", id);

                            ConectorAPI.conexaoSinglePOST(
                                    "/rest/v1/usuarios",
                                    dadosSolicitacao,
                                    headerCliente,
                                    getApplicationContext(),
                                    new ConectorAPI.VolleySingleCallback() {
                                        @Override
                                        public void onSuccess(JSONObject response) throws JSONException {
                                            Toast.makeText(Add_Funcionario.this, "Erro ao cadastrar. Tente novamente", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        //Não sei o porquê, mas o Volley reconhece a resposta do cadastro como erro

                                        public void onError(VolleyError error) throws JSONException {
                                            relacionarUsuarioPapel(id,headerCliente);
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

    private boolean verificarCamposFun(){
        //Verifica se o campo Nome não está vazio
        if(inputNomeFun.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o Nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo Sobrenome não está vazio
        if(inputSobrenomeFun.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o sobrenome", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo Email não está vazio
        if(inputEmailFun.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se o campo senha não está vazio
        if(inputSenhaFun.getText().toString().trim().equals("")){
            Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Verifica se email e confirmar email são iguais
        if(!inputEmailFun.getText().toString().trim().equals(inputConfirmarEmailFun.getText().toString().trim())){
            Toast.makeText(this, "Os emails não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        //Verifica se senha e confirmar senha são iguais
        if(!inputConfirmarSenhaFun.getText().toString().trim().equals(inputSenhaFun.getText().toString().trim())){
            Toast.makeText(this, "As senhas não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    private void definirListenerDoEmail(){
        inputEmailFun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    inputEmailFun.setError("Email inválido");
                }
            }
        });
        inputConfirmarEmailFun.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    inputConfirmarEmailFun.setError("Email inválido");
                }
            }
        });
    }


    public void CancelarCasdastroFun(View view){
        Intent i = new Intent(getApplicationContext(), perfilAdm.class);
        startActivity(i);
    }

    private void relacionarUsuarioPapel(String id, Map<String, String> headerCliente) throws JSONException {
        JSONObject dadosSolicitacao = new JSONObject();

        dadosSolicitacao.put("id_user", id);
        dadosSolicitacao.put("id_papel", 1);

        ConectorAPI.conexaoSinglePOST(
                "/rest/v1/usuarios_papel",
                dadosSolicitacao,
                headerCliente,
                getApplicationContext(),
                new ConectorAPI.VolleySingleCallback() {
                    @Override
                    public void onSuccess(JSONObject response) throws JSONException {
                        Toast.makeText(Add_Funcionario.this, "Erro ao cadastrar. Tente novamente", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    //Não sei o porquê, mas o Volley reconhece a resposta do cadastro como erro

                    public void onError(VolleyError error) {

                        Toast.makeText(Add_Funcionario.this, "Funcionário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }
}