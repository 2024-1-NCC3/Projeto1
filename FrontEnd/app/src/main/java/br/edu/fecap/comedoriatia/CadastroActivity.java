package br.edu.fecap.comedoriatia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextConfirmarEmail, editTextSenha, editTextConfirmarSenha;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // Inicialize os componentes de UI
        editTextNome = findViewById(R.id.Nome);
        editTextEmail = findViewById(R.id.Email);
        editTextConfirmarEmail = findViewById(R.id.ConfirmarEmail);
        editTextSenha = findViewById(R.id.Senha);
        editTextConfirmarSenha = findViewById(R.id.ConfirmarSenha); // Adicionado
        buttonCadastrar = findViewById(R.id.Cadastrar);

        // Configurar um ouvinte de clique para o botão de cadastro
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {
        // Obter os valores dos campos de entrada
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String confirmarEmail = editTextConfirmarEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString();
        String confirmarSenha = editTextConfirmarSenha.getText().toString(); // Adicionado

        // Validar se os campos estão vazios
        if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(confirmarEmail) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(confirmarSenha)) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar o formato do e-mail
        if (!isValidEmail(email)) {
            Toast.makeText(this, "E-mail inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar se o e-mail e a confirmação de e-mail correspondem
        if (!email.equals(confirmarEmail)) {
            Toast.makeText(this, "Os e-mails não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar se a senha e a confirmação da senha correspondem
        if (!senha.equals(confirmarSenha)) {
            Toast.makeText(this, "As senhas não correspondem", Toast.LENGTH_SHORT).show();
            return;
        }

        // Se todos os campos estiverem preenchidos corretamente, você pode prosseguir com o registro do usuário
        // Aqui você pode adicionar a lógica para registrar o usuário no seu sistema

        // Exemplo: Mostrar uma mensagem de sucesso
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

        // Também é possível iniciar uma nova atividade ou realizar outras operações necessárias após o cadastro
    }

    private boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
