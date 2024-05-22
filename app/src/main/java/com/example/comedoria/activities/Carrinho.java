package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import com.example.comedoria.Adapter.AdapterCarrinho;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Carrinho extends AppCompatActivity {
    TextView txtHora, txtData,txtTotal, txtTituloQuantidade, txtTituloSoma, txtTituloHorario;
    TextInputEditText inputObservacoes;
    RecyclerView recycleCarrinho;
    List<Produto> produtos;
    String accessToken,idUsuario,idPedido,date,horas;
    Calendar dataFinal;
    int dia, mes, ano, hora, minuto;
    FloatingActionButton fabHome;

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
        txtTituloQuantidade = findViewById(R.id.txtTituloQuantidade);
        txtTituloSoma = findViewById(R.id.txtTituloSoma);
        txtTituloHorario = findViewById(R.id.txtTituloHorario);

        fabHome = findViewById(R.id.btnFlutuante);
        fabHome.setImageTintList(ColorStateList.valueOf(Color.WHITE));

        //seta a largura do título quantidade corretamente
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.remove);
        int larguraIcon = bitmap.getWidth();

        TextPaint paintQuantidade = txtTituloHorario.getPaint();
        float tamanhoQuantidade = paintQuantidade.measureText("000");
        txtTituloQuantidade.setWidth((int) (tamanhoQuantidade) + (larguraIcon *2));

        float tamanhoSoma = paintQuantidade.measureText("R$ 0000,00 ");
        txtTituloSoma.setWidth((int) (tamanhoSoma));


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
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
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

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");
                try {
                    Date dataFormatada = formato.parse(date);
                    txtData.setText(meuFormato.format(dataFormatada));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
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

    public void voltarTelaCarrinho(View view){
        finish();
    }

    public void voltarInicio(View view){
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
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



            //Faz a requisição para criar um pedido no servidor
            ConectorAPI.conexaoArrayPOST(
                    "/rest/v1/pedido",
                    headers,
                    JsonNovoPedido(),
                    getApplicationContext(),
                    new ConectorAPI.VolleyArrayCallback() {
                        @Override
                        public void onSuccess(JSONArray response) throws JSONException {
                            idPedido = response.getJSONObject(0).getString("id_pedido");
                            adicionarProdutosNoPedido();
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
        solicitacao.put("observacoes", inputObservacoes.getText().toString());


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