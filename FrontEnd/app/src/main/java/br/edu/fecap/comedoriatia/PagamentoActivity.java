package br.edu.fecap.comedoriatia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PagamentoActivity extends AppCompatActivity {

    private EditText editTextNome, editTextEmail, editTextTelefone, editTextEndereco,
            editTextNumeroCartao, editTextMesVencimento, editTextAnoVencimento, editTextCVV,
            editTextNomeCartao;
    private Spinner spinnerTipoCartao;
    private Button buttonPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        // Inicializar os componentes da interface
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextNumeroCartao = findViewById(R.id.editTextNumeroCartao);
        editTextMesVencimento = findViewById(R.id.editTextMesVencimento);
        editTextAnoVencimento = findViewById(R.id.editTextAnoVencimento);
        editTextCVV = findViewById(R.id.editTextCVV);
        editTextNomeCartao = findViewById(R.id.editTextNomeCartao);
        spinnerTipoCartao = findViewById(R.id.spinnerTipoCartao);
        buttonPagar = findViewById(R.id.buttonPagar);

        // Configurar o listener de clique para o botão
        buttonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processarPagamento();
            }
        });
    }

    // Método para processar o pagamento
    private void processarPagamento() {
        // Aqui você adicionaria a lógica real para processar o pagamento
        // Por enquanto, apenas exibiremos uma mensagem de confirmação

        // Coletar dados do formulário
        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String telefone = editTextTelefone.getText().toString().trim();
        String endereco = editTextEndereco.getText().toString().trim();
        String numeroCartao = editTextNumeroCartao.getText().toString().trim();
        String mesVencimento = editTextMesVencimento.getText().toString().trim();
        String anoVencimento = editTextAnoVencimento.getText().toString().trim();
        String cvv = editTextCVV.getText().toString().trim();
        String nomeCartao = editTextNomeCartao.getText().toString().trim();
        String tipoCartao = spinnerTipoCartao.getSelectedItem().toString();

        // Simulação de processamento do pagamento
        String mensagem = "Pagamento processado com sucesso!\n\n" +
                "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Telefone: " + telefone + "\n" +
                "Endereço: " + endereco + "\n" +
                "Número do Cartão: " + numeroCartao + "\n" +
                "Mês de Vencimento: " + mesVencimento + "\n" +
                "Ano de Vencimento: " + anoVencimento + "\n" +
                "CVV: " + cvv + "\n" +
                "Nome no Cartão: " + nomeCartao + "\n" +
                "Tipo de Cartão: " + tipoCartao;

        // Exibir mensagem de confirmação
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }
}
