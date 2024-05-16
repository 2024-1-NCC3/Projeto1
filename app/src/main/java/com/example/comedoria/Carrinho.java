package com.example.comedoria;

import static com.example.comedoria.BuildConfig.API_KEY;
import static com.example.comedoria.BuildConfig.API_URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Carrinho extends AppCompatActivity {
    TextView txtHora, txtData,txtTotal;
    TextInputEditText inputObservacoes;
    RecyclerView recycleCarrinho;
    List<Produto> produtos;
    String accessToken,idUsuario,idPedido,date,horas;
    Calendar dataFinal;
    int dia, mes, ano, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        accessToken = getIntent().getStringExtra("accessToken");
        idUsuario = getIntent().getStringExtra("idUsuario");

        String arrayProdutos = getIntent().getStringExtra("produtosSelecionados");
        produtos = Arrays.asList(new Gson().fromJson(arrayProdutos, Produto[].class));
        txtTotal = findViewById(R.id.txtTotal);
        txtHora = findViewById(R.id.txtHora);
        txtData = findViewById(R.id.txtData);
        inputObservacoes = findViewById(R.id.inputObservacoes);
        recycleCarrinho = findViewById(R.id.recylerCarrinho);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycleCarrinho.setLayoutManager(layoutManager);
        recycleCarrinho.setHasFixedSize(true);

        AdapterCarrinho adapter = new AdapterCarrinho(produtos, this);
        recycleCarrinho.setAdapter(adapter);
        atualizarTotal();
    }

    public void selecionarHora(View view){
        Calendar calendar = Calendar.getInstance();
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(Calendar.HOUR_OF_DAY)
                .setMinute(Calendar.MINUTE)
                .setTitleText("Selecione a hora de retirada")
                .build();



        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = picker.getHour();
                minuto = picker.getMinute();

                calendar.set(Calendar.HOUR_OF_DAY, hora);
                calendar.set(Calendar.MINUTE, minuto);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                horas = simpleDateFormat.format(calendar.getTime());
                txtHora.setText(horas);


                System.out.printf(txtHora.getText().toString());
            }
        });
        Timestamp time = new Timestamp(calendar.getTimeInMillis());
        picker.show(getSupportFragmentManager(),"tag");
    }

    public void selecionarData(View view){
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data de retirada")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(selection));

                txtData.setText(date);
            }
        });
        picker.show(getSupportFragmentManager(),"tag");
    }

    public void atualizarLista(List<Produto> listaAtualizada){
        this.produtos = listaAtualizada;
    }
    public void atualizarTotal(){
        double soma = 0;
        for(Produto produto: produtos){
            soma+= produto.getPreco() * produto.getQuantidade();
        }
        txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", soma));
    }


    public void finalizarPedido(View view) throws JSONException {

        double soma = 0;

        if(date == null || horas == null){
            Toast.makeText(this, "Por favor, selecione uma data e hora para retirada", Toast.LENGTH_SHORT).show();
            return;
        }
        for(Produto produto: produtos){
            soma += produto.getPreco() * produto.getQuantidade();
        }
        if(soma <= 0){
            Toast.makeText(this, "Selecione no mínimo um produto", Toast.LENGTH_SHORT).show();
            return;
        }else{

            Map<String, String> headers = new HashMap<>();
            //define os heades que a solicitação vai precisar
            headers.put("apikey", API_KEY);
            headers.put("Authorization", "Bearer " + accessToken);
            headers.put("Content-Type", "application/json");
            headers.put("Prefer", "return=representation");



            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.POST,
                    url,
                    req,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.length()>0){
                                for(int i=0; i< response.length();i++){
                                    try{
                                        JSONObject jsonObj = response.getJSONObject(i);
                                        //Se o pedido ter uma resposta, verifica se teve sucesso

                                        Boolean sucesso = jsonObj.getBoolean("sucesso");
                                        String msg = jsonObj.getString("msg");
                                        int pedido = jsonObj.getInt("pedido");

                                        //Manda a mensagem de retorno, pra indicar o status
                                        Toast.makeText(Carrinho.this, msg, Toast.LENGTH_SHORT).show();
                                        //Se estever tudo certo, passa para a próxima página
                                        if(sucesso){
                                            Intent intent = new Intent(Carrinho.this, ComprovantePedido.class);
                                            intent.putExtra("retirarProduto", txtHora.getText().toString());
                                            intent.putExtra("numPedido",pedido);
                                            startActivity(intent);
                                        }
                                    }catch (JSONException ex){

                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            //se a resposta for um erro, irá apresentar um Toast com o erro
                            String body = null;
                            try {
                                body = new String(error.networkResponse.data, "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                throw new RuntimeException(e);
                            }
                            JSONObject data = null;
                            try {
                                data = new JSONObject(body);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            String message = data.optString("error_description");
                            Toast.makeText(Carrinho.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }

    }

    private JSONArray JsonNovoPedido() throws JSONException {
        Log.i("SUpabase", date);

        JSONObject solicitacao = new JSONObject();
        solicitacao.put("status", "Aguardando Pagamento");
        solicitacao.put("id_usuario", idUsuario);
        solicitacao.put("hora_para_retirada", horas + ":00");
        solicitacao.put("data_para_retirada", date);
        solicitacao.put("observacoes", inputObservacoes.getText());


        JSONArray req = new JSONArray();
        req.put(solicitacao);

        return req;
    }
    private void adicionarProdutosNoPedido() throws JSONException {


        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("Prefer", "return=representation");

        ConectorAPI.conexaoArrayPOST(
                "/rest/v1/detalhes_pedido",
                headers,
                JsonPedidosParaAdicionar(),
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        Toast.makeText(Carrinho.this, "Pedido feito com sucesso", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Carrinho.this, ComprovantePedido.class);
                        i.putExtra("accessToken", accessToken);
                        i.putExtra("idPedido", idPedido);
                        startActivity(i);
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Toast.makeText(Carrinho.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private JSONArray JsonPedidosParaAdicionar() throws JSONException {
        JSONArray req = new JSONArray();
        for(Produto produto: produtos){
            JSONObject prod = new JSONObject();

            prod.put("id_produto", produto.getId());
            prod.put("id_pedido", idPedido);
            prod.put("quantidade",produto.getQuantidade());

            req.put(prod);
        }
        return req;
    }
}