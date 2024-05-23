package com.example.comedoria.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.comedoria.R;
import com.google.android.material.textfield.TextInputLayout;


public class modificar_produto extends AppCompatActivity {

    private TextView txtNomeProduto;

    private String accessToken,idProduto,imgProduto,nomeProduto;

    private String precoProduto;

    private String quantidadeProduto;

    private TextInputLayout txtPreco, txtQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_produto);

        accessToken = getIntent().getStringExtra("accessToken");
        idProduto = getIntent().getStringExtra("idProduto");
        imgProduto = getIntent().getStringExtra("imgProduto");
        nomeProduto = getIntent().getStringExtra("nomeProduto");
        precoProduto = getIntent().getStringExtra("precoProduto");
        quantidadeProduto = getIntent().getStringExtra("quantidadeProduto");

        txtNomeProduto = findViewById(R.id.txtNomeProdutoModificarProduto);
        txtPreco = findViewById(R.id.txtPreco);
        txtQuantidade = findViewById(R.id.txtQuantidade);

        txtNomeProduto.setText(nomeProduto);
        txtPreco.setHint(precoProduto);
        txtQuantidade.setHint(quantidadeProduto);

    }
}