package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;
import static com.example.comedoria.BuildConfig.API_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class modificar_produto extends AppCompatActivity {

    private TextView txtNomeProduto;

    private String accessToken,idProduto,imgProduto,nomeProduto;

    private double precoProduto;

    private int quantidadeProduto;

    private TextInputLayout txtPreco, txtQuantidade;

    private TextInputEditText inputPreco, inputQuantidade;

    private CheckBox cbPromocao;

    Button enviarAlteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_produto);

        accessToken = getIntent().getStringExtra("accessToken");
        idProduto = getIntent().getStringExtra("idProduto");
        imgProduto = getIntent().getStringExtra("imgProduto");
        nomeProduto = getIntent().getStringExtra("nomeProduto");
        precoProduto = getIntent().getDoubleExtra("precoProduto", 0.0);
        quantidadeProduto = getIntent().getIntExtra("quantidadeProduto", 0);

        txtNomeProduto = findViewById(R.id.txtNomeProdutoModificarProduto);
        txtPreco = findViewById(R.id.txtPreco);
        txtQuantidade = findViewById(R.id.txtQuantidade);
        cbPromocao = findViewById(R.id.cbPromocao);
        inputPreco = findViewById(R.id.inputPreco);
        inputQuantidade = findViewById(R.id.inputQuantidade);
        enviarAlteracao= findViewById(R.id.btnEnviarAlteracao);

        txtNomeProduto.setText(nomeProduto);
        txtPreco.setHint("Preço: "+String.format(Locale.getDefault(), "R$ %.2f", precoProduto));
        txtQuantidade.setHint("Quantidade: "+quantidadeProduto);

    }

        public void enviarAlteracao(View view) throws JSONException {
            int quantidadeEstoque = Integer.parseInt(inputQuantidade.getText().toString());
            double precoEstoque = Double.parseDouble(inputPreco.getText().toString());
            atualizarProduto(quantidadeEstoque, precoEstoque);
        }

        private void atualizarProduto(int quantidadeEstoque, double precoEstoque) throws JSONException {

            Map<String, String> headers = new HashMap<>();
            //define os heades que a solicitação vai precisar
            headers.put("apikey", API_KEY);
            headers.put("Authorization", "Bearer " + accessToken);
            headers.put("Content-Type", "application/json");
            headers.put("Prefer", "return=minimal");


            ConectorAPI.conexaoArrayPATCH(
                    "/rest/v1/estoque?id_estoque=eq."+idProduto,
                    headers,
                    gerarJSONProduto(quantidadeEstoque, precoEstoque),
                    getApplicationContext(),
                    new ConectorAPI.VolleyArrayCallback() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {
                            Toast.makeText(modificar_produto.this, "Produto alterado", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(modificar_produto.this, "Erro ao modificar produto," +
                                    " tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }

            );
        }

    private JSONArray gerarJSONProduto(int quantidadeProduto, double precoProduto) throws JSONException {
        JSONObject produtoEstoque = new JSONObject();
        produtoEstoque.put("quantidade",quantidadeProduto);
        produtoEstoque.put("preco",precoProduto);

        JSONArray retorno = new JSONArray();
        retorno.put(produtoEstoque);

        return retorno;
    }
}