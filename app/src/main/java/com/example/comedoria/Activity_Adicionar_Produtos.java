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

/**
 * Activity para adicionar produtos ao sistema.
 */
public class Activity_Adicionar_Produtos extends AppCompatActivity {

    private ActivityAdicionarProdutosBinding bindingAddProduto;
    private EditText inputNomeProduto, inputPreco, inputIngrediente, inputQnt;
    private ImageView imgAddProduto;
    private Spinner spinnerAddProduto;

    private String nomeProduto;
    private double preco;
    private String ingrediente;
    private int quantidade;
    private String categoria;
    private int tipo;

    private Uri nSelectedUri;

    /**
     * Método chamado quando a activity é criada.
     *
     * @param savedInstanceState Bundle contendo o estado salvo da activity.
     */
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
        imgAddProduto = findViewById(R.id.imgAddProduto);

        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.filtro_categoria_add_produto,
                R.layout.color_spinner_dropdown_layout
        );
        arrayAdapter.setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        spinnerAddProduto.setAdapter(arrayAdapter);
    }

    /**
     * Finaliza o cadastro do produto ao ser chamado.
     *
     * @param view A view que acionou este método.
     */
    public void finalizarCadastroProduto(View view) {
        nomeProduto = inputNomeProduto.getText().toString();
        preco = Double.parseDouble(inputPreco.getText().toString());
        ingrediente = inputIngrediente.getText().toString();
        quantidade = Integer.parseInt(inputQnt.getText().toString());
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

    /**
     * Inicia uma intent para carregar uma imagem da galeria.
     *
     * @param view A view que acionou este método.
     */
    public void CarregarImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    /**
     * Chamado quando a activity que foi iniciada com startActivityForResult() retorna o resultado.
     *
     * @param requestCode O código de solicitação original.
     * @param resultCode O código de resultado retornado pela activity.
     * @param data A intent que pode retornar o resultado dos dados.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            nSelectedUri = data.getData();
            imgAddProduto.setImageURI(nSelectedUri);
        }
    }
}
