package com.example.comedoria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comedoria.R;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Define o layout da tela de inicialização
        setContentView(R.layout.activity_start);

        // Cria um novo manipulador para a thread principal (UI thread)
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Cria um intent para iniciar a atividade de login
                Intent intent = new Intent(Start.this, Login.class);

                // Inicia a atividade de login após um atraso de 2000 milissegundos (2 segundos)
                startActivity(intent);
            }
        }, 2000); // Tempo de atraso em milissegundos
    }
}
