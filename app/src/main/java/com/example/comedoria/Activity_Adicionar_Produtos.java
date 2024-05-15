package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.comedoria.databinding.ActivityAdicionarProdutosBinding;

import java.util.ArrayList;
import java.util.List;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private ActivityAdicionarProdutosBinding bindingAddProduto;

    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingAddProduto = ActivityAdicionarProdutosBinding.inflate(getLayoutInflater());
        setContentView(bindingAddProduto.getRoot());



        /*
        inputNomeProduto = findViewById(R.id.textInputNomeProduto);
        inputPreco = findViewById(R.id.textInputPreco);
        inputIngrediente = findViewById(R.id.textInputDingredientes);
        inputQnt = findViewById(R.id.textInputQnt);

        */

    }

    public void finalizarCadastroProduto() {
        String nomeProduto;
        double preco;
        String ingrediente;
        int quantidade;

        nomeProduto = inputNomeProduto.getText().toString();
        preco = Double.parseDouble(inputPreco.getText().toString());
        ingrediente = inputIngrediente.getText().toString();
        quantidade = Integer.parseInt(inputQnt.getText().toString());

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key, Context.MODE_PRIVATE);



    }


    public void adicionarNovoProduto(View view) {



    }

}
