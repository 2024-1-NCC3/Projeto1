package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterConfirmar;
import com.example.comedoria.Class.Pedido;
import com.example.comedoria.Class.Produto;
import com.google.gson.Gson;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Esta classe representa a atividade para confirmar um pedido.
 */
public class ConfirmarPedido extends AppCompatActivity {
    TextView nomeCliente, numeroPedido, totalConfirmar;
    String accessToken;
    Pedido pedido;
    RecyclerView recyclerConfirmar;
    AdapterConfirmar adapterConfirmar;
    View rootView;
    List<Produto> listaProdutos = new ArrayList<>();

    /**
     * Método chamado quando a atividade é criada.
     *
     * @param savedInstanceState Os dados fornecidos para a atividade quando foi reconstruída a partir de um estado anterior, caso exista.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pedido);
        accessToken = getIntent().getStringExtra("accessToken");

        String jsonString = getIntent().getStringExtra("pedido");
        Gson gson = new Gson();
        pedido = gson.fromJson(jsonString, Pedido.class);

        nomeCliente = findViewById(R.id.txtNomeClienteConfirmar);
        numeroPedido = findViewById(R.id.txtNumeroPedidoConfirmar);
        recyclerConfirmar = findViewById(R.id.recyclerConfirmar);
        totalConfirmar = findViewById(R.id.txtTotalGeralConfirmar);
        rootView = findViewById(R.id.layoutConteudo);

        adapterConfirmar = new AdapterConfirmar(listaProdutos);
        recyclerConfirmar.setAdapter(adapterConfirmar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerConfirmar.setLayoutManager(layoutManager);
        recyclerConfirmar.setHasFixedSize(true);

        buscarProdutosDoPedido();

        nomeCliente.setText(pedido.getNomeCliente());
        numeroPedido.setText(String.valueOf(pedido.getNumeroPedido()));
        totalConfirmar.setText(String.format(Locale.getDefault(), "Total: R$ %.2f", pedido.getTotal()));
    }

    /**
     * Método para buscar os produtos do pedido.
     */
    private void buscarProdutosDoPedido() {
        Map<String, String> headers = new HashMap<>();
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);

        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?id_pedido=eq." + pedido.getId_pedido() + "&select=detalhes_pedido(quantidade,produtos(nome_produto,preco))",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        JSONArray detalhesPedido = response.getJSONObject(0).getJSONArray("detalhes_pedido");
                        for (int i = 0; i < detalhesPedido.length(); i++) {
                            JSONObject produto = detalhesPedido.getJSONObject(i);
                            int quantidade = produto.getInt("quantidade");
                            JSONObject det = produto.getJSONObject("produtos");
                            double preco = det.getDouble("preco");
                            String nomeProd = det.getString("nome_produto");

                            Produto itemLista = new Produto(nomeProd, preco, quantidade);
                            listaProdutos.add(itemLista);
                        }
                        adapterConfirmar.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }
        );
    }

    /**
     * Método chamado ao confirmar a retirada do pedido.
     *
     * @param view A vista que ativou este método.
     */
    public void confirmarRetirada(View view) {
        try {
            scanCode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para gerar um JSONArray de confirmação.
     *
     * @return O JSONArray gerado.
     * @throws JSONException Exceção lançada se houver um erro ao criar o JSONArray.
     */
    private JSONArray gerarJSONArrayConfirmacao() throws JSONException {
        JSONObject confirmacao = new JSONObject();
        confirmacao.put("status", "Retirado");

        JSONArray retorno = new JSONArray();
        retorno.put(confirmacao);

        return retorno;
    }

    /**
     * Método para iniciar a leitura do código de barras.
     */
    private void scanCode() throws JSONException {
        ScanOptions options = new ScanOptions();
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    /**
     * Lançador de resultado para iniciar a leitura do código de barras.
     */
    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            String numPedido = String.valueOf(pedido.getId_pedido());
            if (result.getContents().equals(numPedido)) {
                Map<String, String> headers = new HashMap<>();
                headers.put("apikey", API_KEY);
                headers.put("Authorization", "Bearer " + accessToken);
                headers.put("Content-Type", "application/json");
                headers.put("Prefer", "return=representation");

                try {
                    ConectorAPI.conexaoArrayPATCH(
                            "/rest/v1/pedido?id_pedido=eq." + pedido.getId_pedido(),
                            headers,
                            gerarJSONArrayConfirmacao(),
                            getApplicationContext(),
                            new ConectorAPI.VolleyArrayCallback() {
                                @Override
                                public void onSuccess(JSONArray response) throws JSONException {
                                    Toast.makeText(ConfirmarPedido.this, "Pedido Confirmado", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onError(VolleyError error) {

                                }
                            }
                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarPedido.this);
                builder.setTitle("QRCode errado");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        }
    });
}
