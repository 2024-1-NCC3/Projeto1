package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private List<String> listar = new ArrayList<>();
    private AdapterAddProduto adapterAddProduto;
    private RecyclerView recyclerAddProduto;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produtos);


        adapterAddProduto = new AdapterAddProduto(this, listar);

        inputNomeProduto = findViewById(R.id.inputNomeProduto);
        inputPreco = findViewById(R.id.inputPreco);
        inputIngrediente = findViewById(R.id.inputIngredientes);
        inputQnt = findViewById(R.id.inputQnt);

        iniciarPag();
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



    }


    public void adicionarNovoProduto(View view) {

    }

    private void iniciarPag(){


        //Configurações do recyclerView
        recyclerAddProduto = findViewById(R.id.novoProduto);
        recyclerAddProduto.setLayoutManager(new LinearLayoutManager(this));
        recyclerAddProduto.setAdapter(adapterAddProduto);


    }


}
