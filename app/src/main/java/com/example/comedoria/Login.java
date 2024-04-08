package com.example.comedoria;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void cadastro(View view){
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
    }

}
