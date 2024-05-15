package com.example.comedoria;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.comedoria.databinding.ActivityAdicionarProdutosBinding;

import java.util.Collections;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private ActivityAdicionarProdutosBinding bindingAddProduto;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;

    private ImageView imgAddProduto;

    private Spinner spinnerAddProduto;

    String nomeProduto;
    double preco;
    String ingrediente;
    int quantidade;
    String categoria;
    int tipo;

    Uri nSelectedUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingAddProduto = ActivityAdicionarProdutosBinding.inflate(getLayoutInflater());
        setContentView(bindingAddProduto.getRoot());


        inputNomeProduto = findViewById(R.id.textInputNomeProduto);
        inputPreco = findViewById(R.id.textInputPreco);
        inputIngrediente = findViewById(R.id.textInputDingredientes);
        inputQnt = findViewById(R.id.textInputQnt);
        spinnerAddProduto = findViewById(R.id.spinnerAddProduto);
        imgAddProduto = (ImageView) findViewById(R.id.imgAddProduto);

    }

    public void finalizarCadastroProduto(View view) {


        nomeProduto = inputNomeProduto.getText().toString();
        preco = Double.parseDouble(inputPreco.getText().toString());
        ingrediente = inputIngrediente.getText().toString();
        quantidade = Integer.parseInt(inputQnt.getText().toString());

        spinnerAddProduto.setSelection(tipo);

        switch (tipo) {
            case 0:
                categoria = "Marmitas";
                break;
            case 1:
                categoria = "Bebidas";
                break;
            case 2:
                categoria = "Salgados";
                break;
        }


        //cria um arquivo para armazenar esse dados
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("nomeProduto", nomeProduto);
        editor.putFloat("preço", (float) preco);
        editor.putString("Ingredientes", ingrediente);
        editor.putInt("Quantidade", quantidade);
        editor.putString("categoria", categoria);
        editor.commit();


    }
    public void CarregarImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            nSelectedUri = data.getData();
            imgAddProduto.setImageURI(nSelectedUri);
        }
    }
}





