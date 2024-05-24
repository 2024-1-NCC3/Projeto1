package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Atividade de cadastro de novos usuários no sistema.
 */
public class Cadastro extends AppCompatActivity {

    private EditText inputNome, inputSobrenome, inputEmail, inputSenha, inputConfirmarEmail, inputConfirmarSenha;

    /**
     * Chamado quando a atividade é criada pela primeira vez.
     *
     * @param savedInstanceState Se a atividade está sendo re-inicializada após ser previamente finalizada, então este Bundle contém os dados que ela forneceu mais recentemente em onSaveInstanceState(Bundle). Caso contrário, está nulo.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inputNome = findViewById(R.id.txtNome);
        inputSobrenome = findViewById(R.id.txtSobrenome);
        inputEmail = findViewById(R.id.txtEmail);
        inputConfirmarEmail = findViewById(R.id.txtConfirmarEmail);
        inputSenha = findViewById(R.id.txtSenha);
        inputConfirmarSenha = findViewById(R.id.txtConfirmarSenha);

        definirListenerDoEmail();
    }

    /**
     * Método chamado quando o usuário clica no botão de cadastrar.
     *
     * @param view A visão que foi clicada.
     */
    public void Cadastrar(View view) {
        if (verificarCampos()) {
            JSONObject dadosCadastro = new JSONObject();
            JSONObject dadosCliente = new JSONObject();
            RequestQueue filaRequest = Volley.newRequestQueue(this);

            try {
                dadosCadastro.put("email", inputEmail.getText().toString());
                dadosCadastro.put("password", inputSenha.getText().toString());

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

                            // Segunda solicitação para linkar o novo user à tabela usuarios
                            JSONObject user = response.getJSONObject("user");
                            String id = user.getString("id");

                            String accessToken = response.getString("access_token");
                            Map<String, String> headerCliente = new HashMap<>();

                            headerCliente.put("apikey", API_KEY);
                            headerCliente.put("Authorization", "Bearer " + accessToken);
                            headerCliente.put("Content-Type", "application/json");
                            headerCliente.put("Prefer", "return=minimal");

                            JSONObject dadosSolicitacao = new JSONObject();

                            dadosSolicitacao.put("primeiro_nome", inputNome.getText().toString());
                            dadosSolicitacao.put("ultimo_nome", inputSobrenome.getText().toString());
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
                                        public void onError(VolleyError error) throws JSONException {
                                            // Não sei o porquê, mas o Volley reconhece a resposta do cadastro como erro
                                            relacionarUsuarioPapel(id, headerCliente);
                                        }
                                    }
                            );

                        }

                        @Override
                        public void onError(VolleyError error) {
                            // Lidar com o erro da solicitação de cadastro
                        }
                    });
        }
    }

    /**
     * Verifica se todos os campos do formulário estão preenchidos corretamente.
     *
     * @return true se todos os campos estiverem válidos, false caso contrário.
     */
    private boolean verificarCampos() {
        // Verifica se o campo Nome não está vazio
        if (inputNome.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Preencha o Nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se o campo Sobrenome não está vazio
        if (inputSobrenome.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Preencha o Sobrenome", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se o campo Email não está vazio
        if (inputEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Preencha o Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se o campo Confirmar Email não está vazio
        if (inputConfirmarEmail.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Confirme o Email", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se o campo Senha não está vazio
        if (inputSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Preencha a Senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se o campo Confirmar Senha não está vazio
        if (inputConfirmarSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Confirme a Senha", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se email e confirmar email são iguais
        if (!inputEmail.getText().toString().trim().equals(inputConfirmarEmail.getText().toString().trim())) {
            Toast.makeText(this, "Os emails não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Verifica se senha e confirmar senha são iguais
        if (!inputSenha.getText().toString().trim().equals(inputConfirmarSenha.getText().toString().trim())) {
            Toast.makeText(this, "As senhas não são iguais", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Relaciona o usuário recém-cadastrado ao seu papel no sistema.
     *
     * @param id O ID do usuário recém-cadastrado.
     * @param headerCliente Os headers necessários para a solicitação.
     * @throws JSONException Se ocorrer um erro ao criar o JSON.
     */
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
                    public void onError(VolleyError error) {
                        Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
        );
    }

    /**
     * Cancela o cadastro e finaliza a atividade.
     *
     * @param view A visão que foi clicada.
     */
    public void cancelar(View view) {
        finish();
    }

    /**
     * Define listeners para validar os campos de email.
     */
    private void definirListenerDoEmail() {
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    inputEmail.setError("Email inválido");
                }
            }
        });
        inputConfirmarEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
                    inputConfirmarEmail.setError("Email inválido");
                }
            }
        });
    }
}
