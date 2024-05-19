package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
}