package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
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


/**
 * Esta classe representa a atividade responsável por modificar as informações de um produto.
 * Permite a alteração do preço e da quantidade em estoque de um produto específico.
 */
public class ModificarProduto extends AppCompatActivity {

    // Declaração de variáveis de interface
    private TextView txtNomeProduto;
    private String accessToken,imgProduto,nomeProduto;
    private double precoProduto;
    private int quantidadeProduto,idEstoque,idProduto;
    private TextInputLayout txtPreco, txtQuantidade;
    private TextInputEditText inputPreco, inputQuantidade;
    private CheckBox cbPromocao;
    Button enviarAlteracao;

    /**
     * Método executado quando a atividade é criada.
     * Carrega os dados do produto a ser modificado e configura os elementos da interface.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_produto);

        // Obtenção dos dados do produto a ser modificado
        accessToken = getIntent().getStringExtra("accessToken");
        idProduto = getIntent().getIntExtra("idProduto",0);
        imgProduto = getIntent().getStringExtra("imgProduto");
        nomeProduto = getIntent().getStringExtra("nomeProduto");
        precoProduto = getIntent().getDoubleExtra("precoProduto", 0.0);
        quantidadeProduto = getIntent().getIntExtra("quantidadeProduto", 0);
        idEstoque = getIntent().getIntExtra("idEstoque", 0);

        // Inicialização dos elementos da interface
        txtNomeProduto = findViewById(R.id.txtNomeProdutoModificarProduto);
        txtPreco = findViewById(R.id.txtPreco);
        txtQuantidade = findViewById(R.id.txtQuantidade);
        cbPromocao = findViewById(R.id.cbPromocao);
        inputPreco = findViewById(R.id.inputPreco);
        inputQuantidade = findViewById(R.id.inputQuantidade);
        enviarAlteracao= findViewById(R.id.btnEnviarAlteracao);

        // Configuração dos elementos da interface com os dados do produto
        txtNomeProduto.setText(nomeProduto);
        txtPreco.setHint("Preço: "+String.format(Locale.getDefault(), "R$ %.2f", precoProduto));
        txtQuantidade.setHint("Quantidade: "+quantidadeProduto);
    }

    /**
     * Método chamado quando o botão de enviar alterações é clicado.
     * Realiza as alterações de preço e quantidade e envia as atualizações para o servidor.
     */
    public void enviarAlteracao(View view) throws JSONException {
        int quantidadeEstoque = quantidadeProduto;
        double precoEstoque = precoProduto;

        // Verifica se foram feitas alterações nos campos de quantidade e preço
        if(inputQuantidade.getText().toString().equals("") && inputPreco.getText().toString().equals("")){
            Toast.makeText(this, "Nenhuma alteração feita", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!inputQuantidade.getText().toString().equals("") && inputPreco.getText().toString().equals("")){
            quantidadeEstoque = Integer.parseInt(inputQuantidade.getText().toString());
        }
        if(inputQuantidade.getText().toString().equals("") && !inputPreco.getText().toString().equals("")){
            precoEstoque = Double.parseDouble(inputPreco.getText().toString());
        }
        if(!inputQuantidade.getText().toString().equals("") && !inputPreco.getText().toString().equals("")){
            quantidadeEstoque = Integer.parseInt(inputQuantidade.getText().toString());
            precoEstoque = Double.parseDouble(inputPreco.getText().toString());
        }
        // Chama o método para atualizar as informações do produto
        atualizarProduto(quantidadeEstoque, precoEstoque);
    }

    /**
     * Método responsável por enviar as atualizações de quantidade e preço do produto para o servidor.
     */
    private void atualizarProduto(int quantidadeEstoque, double precoEstoque) throws JSONException {
        // Criação dos headers necessários para a requisição
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Content-Type", "application/json");
        headers.put("Prefer", "return=representation");

        // Verifica se houve alteração na quantidade em estoque
        if(quantidadeEstoque != quantidadeProduto){
            // Realiza a requisição para atualizar a quantidade em estoque
            ConectorAPI.conexaoArrayPATCH(
                    "/rest/v1/estoque?id_estoque=eq."+idEstoque,
                    headers,
                    gerarJSONQuantidade(quantidadeEstoque),
                    getApplicationContext(),
                    new ConectorAPI.VolleyArrayCallback() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {
                            Toast.makeText(getApplicationContext(), "Quantidade alterada", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(ModificarProduto.this, "Erro ao modificar quantidade," +
                                    " tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

        // Verifica se houve alteração no preço do produto
        if(precoEstoque != precoProduto){
            // Realiza a requisição para atualizar o preço do produto
            ConectorAPI.conexaoArrayPATCH(
                    "/rest/v1/produtos?id_produto=eq."+idProduto,
                    headers,
                    gerarJSONPreco(precoEstoque),
                    getApplicationContext(),
                    new ConectorAPI.VolleyArrayCallback() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {
                            Toast.makeText(getApplicationContext(), "Preço alterado", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Toast.makeText(ModificarProduto.this, "Erro ao modificar preço," +
                                    " tente novamente", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }

    /**
     * Método responsável por gerar um objeto JSON contendo a quantidade do produto.
     */
    private JSONArray gerarJSONQuantidade(int quantidadeProduto) throws JSONException {
        JSONObject produtoEstoque = new JSONObject();
        produtoEstoque.put("quantidade",quantidadeProduto);

        JSONArray retorno = new JSONArray();
        retorno.put(produtoEstoque);

        return retorno;
    }

    /**
     * Método responsável por gerar um objeto JSON contendo o preço do produto.
     */
    private JSONArray gerarJSONPreco(double precoProduto) throws JSONException {
        JSONObject produtoEstoque = new JSONObject();
        produtoEstoque.put("preco",precoProduto);

        JSONArray retorno = new JSONArray();
        retorno.put(produtoEstoque);

        return retorno;
    }
}
