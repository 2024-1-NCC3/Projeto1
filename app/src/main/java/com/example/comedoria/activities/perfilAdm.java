package com.example.comedoria.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.comedoria.R;

public class perfilAdm extends AppCompatActivity {

    private ImageView btnEstoque; // Botão para ir para a página de estoque
    private String accessToken; // Token de acesso do usuário administrador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_adm);

        // Inicialização do botão de estoque e obtenção do token de acesso
        btnEstoque = findViewById(R.id.btnEstoque);
        accessToken = getIntent().getStringExtra("accessToken");
    }

    // Método para ir para a página de estoque
    public void IrPaginaEstoque(View view){
        Intent intent = new Intent(perfilAdm.this, Estoque.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    // Método para ir para a página de adicionar saldo
    public void IrAdicionarSaldo(View view){
        Intent intent = new Intent(perfilAdm.this, AdicionarSaldo.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    // Método para ir para a página de pedidos
    public void IrPedidosTia(View view){
        Intent intent = new Intent(perfilAdm.this, PedidosTia.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    // Método para ir para a página de adicionar saldo (repetido por engano)
    public void IrAddSaldo(View view){
        Intent intent = new Intent(perfilAdm.this, AdicionarSaldo.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }
}
