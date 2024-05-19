package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
    }


    public void SairdoPerfil(View view) {
        Intent intent = new Intent(Perfil.this, Teste.class);
        startActivity(intent);
        finish();
    }
}
