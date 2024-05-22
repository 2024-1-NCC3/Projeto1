package com.example.comedoria.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.comedoria.R;

public class perfilAdm extends AppCompatActivity {

    private ImageView btnEstoque;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_adm);

        btnEstoque = findViewById(R.id.btnEstoque);

        accessToken = getIntent().getStringExtra("accessToken");
    }

    public void IrPaginaEstoque(View view){
        Intent intent = new Intent(perfilAdm.this, Estoque.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    public void IrAdicionarSaldo(View view){
        Intent intent = new Intent(perfilAdm.this, AdicionarSaldo.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    public void IrPedidosTia(View view){
        Intent intent = new Intent(perfilAdm.this, PedidosTia.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }

    public void IrAddSaldo(View view){
        Intent intent = new Intent(perfilAdm.this, AdicionarSaldo.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }
}