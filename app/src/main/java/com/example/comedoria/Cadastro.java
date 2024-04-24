package com.example.comedoria;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class Cadastro extends AppCompatActivity {

    private EditText inputNome, inputSobrenome, inputCpf, inputEmail, inputSenha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        inputCpf = findViewById(R.id.txtCpf);
        inputEmail = findViewById(R.id.txtEmail);
        inputSenha = findViewById(R.id.txtSenha);
    }
}
