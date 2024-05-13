package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private List<Produto> listaProdutos;
    private AdapterAddProduto adapter;
    private RecyclerView recyclerView;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_produtos);

        listaProdutos = new ArrayList<>();
        adapter = new AdapterAddProduto(this, listaProdutos);
        recyclerView = findViewById(R.id.novoProduto);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        inputNomeProduto = findViewById(R.id.inputNomeProduto);
        inputPreco = findViewById(R.id.inputPreco);
        inputIngrediente = findViewById(R.id.inputIngredientes);
        inputQnt = findViewById(R.id.inputQnt);
    }

    public void finalizarCadastroProduto(View view){
        // Aqui você pode adicionar a lógica para finalizar o cadastro do produto, se necessário
        // Por exemplo, salvar os dados no banco de dados ou enviar para um servidor
        Toast.makeText(this, "Cadastro do produto finalizado", Toast.LENGTH_SHORT).show();
    }

    public void adicionarNovoProduto (View view){
        String nomeProduto;
        double preco;
        String ingrediente;
        int quantidade;


         nomeProduto = inputNomeProduto.getText().toString();
         preco = Double.parseDouble(inputPreco.getText().toString());
         ingrediente = inputIngrediente.getText().toString();
       quantidade = Integer.parseInt(inputQnt.getText().toString());


        if (!nomeProduto.isEmpty() && !ingrediente.isEmpty()) {
            Produto produto = new Produto(nomeProduto, quantidade, preco, ingrediente);
            listaProdutos.add(produto);
            inputNomeProduto.setText("");
            inputPreco.setText("");
            inputIngrediente.setText("");
            inputQnt.setText("");
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
        }
    }
}
