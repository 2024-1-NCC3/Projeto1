package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comedoria.databinding.ActivityAdicionarProdutosBinding;

public class Activity_Adicionar_Produtos extends AppCompatActivity {

    // Declarando os componentes de UI
    private ActivityAdicionarProdutosBinding bindingAddProduto;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;
    private ImageView imgAddProduto;
    private Spinner spinnerAddProduto;

    // Declarando variáveis para armazenar os dados do produto
    String nomeProduto;
    double preco;
    String ingrediente;
    int quantidade;
    String categoria;
    int tipo;

    // Declarando uma variável para armazenar a URI da imagem selecionada
    Uri nSelectedUri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflando e configurando o layout usando ViewBinding
        bindingAddProduto = ActivityAdicionarProdutosBinding.inflate(getLayoutInflater());
        setContentView(bindingAddProduto.getRoot());

        // Inicializando os componentes de UI
        inputNomeProduto = findViewById(R.id.textInputNomeProduto);
        inputPreco = findViewById(R.id.textInputPreco);
        inputIngrediente = findViewById(R.id.textInputDingredientes);
        inputQnt = findViewById(R.id.textInputQnt);
        spinnerAddProduto = findViewById(R.id.spinnerAddProduto);
        imgAddProduto = (ImageView) findViewById(R.id.imgAddProduto);

        // Configurando o Spinner com as opções de categoria
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.filtro_categoria_add_produto,
                R.layout.color_spinner_dropdown_layout
        );
        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        spinnerAddProduto.setAdapter(arrayAdapter);
    }

    // Método chamado quando o usuário clica no botão para finalizar o cadastro do produto
    public void finalizarCadastroProduto(View view) {
        // Capturando os dados inseridos pelo usuário
        nomeProduto = inputNomeProduto.getText().toString();
        preco = Double.parseDouble(inputPreco.getText().toString());
        ingrediente = inputIngrediente.getText().toString();
        quantidade = Integer.parseInt(inputQnt.getText().toString());

        // Capturando a categoria selecionada no Spinner
        tipo = spinnerAddProduto.getSelectedItemPosition();
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

        // Armazenando os dados do produto em um arquivo de preferências
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

    // Método chamado quando o usuário clica no botão para carregar uma imagem
    public void CarregarImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent , 0);
    }

    // Método chamado após o usuário selecionar uma imagem da galeria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            // Capturando a URI da imagem selecionada e exibindo-a no ImageView
            nSelectedUri = data.getData();
            imgAddProduto.setImageURI(nSelectedUri);
        }
    }
}
