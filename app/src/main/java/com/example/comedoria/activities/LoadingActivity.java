package com.example.comedoria.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comedoria.R;

/**
 * Activity para exibir uma tela de carregamento enquanto os dados estão sendo carregados.
 */
public class LoadingActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Inicialização da barra de progresso
        progressBar = findViewById(R.id.ProgressBar);
        progressBar.setVisibility(View.VISIBLE); // Torna a barra de progresso visível

        // PostDelayed é usado para atrasar a mudança de atividade em 2 segundos
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Após 2 segundos, torna a barra de progresso invisível
                progressBar.setVisibility(View.GONE);
                // Cria uma intenção para iniciar a atividade Produtos
                Intent intent = new Intent(LoadingActivity.this, Produtos.class);
                // Inicia a atividade Produtos
                startActivity(intent);
                // Finaliza a atividade de carregamento para evitar que o usuário volte para ela
                finish();
            }
        }, 2000); // 2000 milissegundos (2 segundos)
    }
}
