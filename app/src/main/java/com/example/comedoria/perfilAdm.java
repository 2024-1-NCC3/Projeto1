package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class perfilAdm extends AppCompatActivity {

    private ImageView btnEstoque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_adm);

        btnEstoque = findViewById(R.id.btnEstoque);
    }

    public void IrPaginaEstoque(View view){
        Intent intent = new Intent(perfilAdm.this, Estoque.class);
        startActivity(intent);
    }
}