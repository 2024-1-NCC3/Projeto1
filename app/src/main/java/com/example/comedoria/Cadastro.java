package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    // Declaração dos EditTexts para entrada de dados
    private EditText inputNome, inputSobrenome, inputCpf,
            inputEmail, inputSenha,inputConfirmarEmail, inputConfirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        // Inicialização dos EditTexts com base nos IDs definidos no layout XML
        inputNome = findViewById(R.id.txtNome);
        inputSobrenome = findViewById(R.id.txtSobrenome);
        inputCpf = findViewById(R.id.txtCpf);
        inputEmail = findViewById(R.id.txtEmail);
        inputConfirmarEmail = findViewById(R.id.txtConfirmarEmail);
        inputSenha = findViewById(R.id.txtSenha);
        inputConfirmarSenha = findViewById(R.id.txtConfirmarSenha);

    }

    // Método chamado quando o botão "Cadastrar" é clicado
    public void Cadastrar(View view){
        // Verifica se todos os campos estão preenchidos corretamente
        if(verificarCampos()){
            // Cria um objeto JSON com os dados de cadastro (email e senha)
            JSONObject dadosCadastro = new JSONObject();
            JSONObject dadosCliente = new JSONObject();

            // Define os headers necessários para a solicitação
            Map<String, String> headers = new HashMap<>();
            headers.put("apikey", API_KEY);
            headers.put("Content-Type", "application/json");

            try {
                // Preenche o objeto JSON com os dados de email e senha
                dadosCadastro.put("email", inputEmail.getText());
                dadosCadastro.put("password", inputSenha.getText());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // Faz uma solicitação POST para o servidor para criar uma nova conta de usuário
            ConectorAPI.conexaoSinglePOST(
                    "/auth/v1/signup",
                    dadosCadastro,
                    headers,
                    getApplicationContext(),
                    new ConectorAPI.VolleySingleCallback() {
                        @Override
                        public void onSuccess(JSONObject response) throws JSONException {
                            // Após o sucesso do cadastro, executa esta parte do código

                            // Extrai o ID do usuário da resposta do servidor
                            JSONObject user = response.getJSONObject("user");
                            String id = user.getString("id");

                            // Extrai o token de acesso da resposta do servidor
                            String accessToken = response.getString("access_token");

                            // Prepara os headers para a próxima solicitação
                            Map<String, String> headerCliente = new HashMap<>();
                            headerCliente.put("apikey", API_KEY);
                            headerCliente.put("Authorization", "Bearer " + accessToken);
                            headerCliente.put("Content-Type", "application/json");
                            headerCliente.put("Prefer","return=minimal");

                            // Prepara os dados para vincular o novo usuário à tabela de usuários
                            JSONObject dadosSolicitacao = new JSONObject();
                            dadosSolicitacao.put("primeiro_nome", inputNome.getText());
                            dadosSolicitacao.put("ultimo_nome", inputSobrenome.getText());
                            dadosSolicitacao.put("id_papel", 2);
                            dadosSolicitacao.put("id_user", id);

                            // Faz uma segunda solicitação POST para vincular o novo usuário à tabela de usuários
                            ConectorAPI.conexaoSinglePOST(
                                    "/rest/v1/usuarios",
                                    dadosSolicitacao,
                                    headerCliente,
                                    getApplicationContext(),
                                    new ConectorAPI.VolleySingleCallback() {
                                        @Override
                                        public void onSuccess(JSONObject response) throws JSONException {
                                            // Em caso de sucesso, exibe uma mensagem e finaliza a atividade
                                            Toast.makeText(Cadastro.this, "Erro ao cadastrar. Tente novamente", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        // Em caso de erro, exibe uma mensagem e finaliza a atividade
                                        public void onError(VolleyError error) {
                                            Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                            );
                        }

                        @Override
                        public void onError(VolleyError error) {
                            // Em caso de erro na primeira solicitação, pode ser tratado aqui
                        }
                    });
        }
    }

    // Método para verificar se todos os campos necessários estão preenchidos corretamente
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

    // Método chamado quando o botão de cancelamento é clicado
    public void cancelar(View view){
        // Finaliza a atividade de cadastro
        finish();
    }
}

