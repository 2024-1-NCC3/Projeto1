package com.example.comedoria;

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
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
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
    RecyclerView recycleCarrinho;
    List<Produto> produtos;
    Calendar dataFinal;
    int dia, mes, ano, hora, minuto;

    //Pega as chaves necessárias para acessar a API
    private static final String API_URL = BuildConfig.API_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);


        String arrayProdutos = getIntent().getStringExtra("produtosSelecionados");
        produtos = Arrays.asList(new Gson().fromJson(arrayProdutos, Produto[].class));
        txtTotal = findViewById(R.id.txtTotal);
        txtHora = findViewById(R.id.txtHora);
        txtData = findViewById(R.id.txtData);
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

                txtHora.setText(simpleDateFormat.format(calendar.getTime()));


                System.out.printf(txtHora.getText().toString());
            }
        });

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
                String date = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date(selection));

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                ano = calendar.get(Calendar.YEAR);

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
        for(Produto produto: produtos){
            soma += produto.getPreco() * produto.getQuantidade();
        }
        if(soma <= 0){
            Toast.makeText(this, "Selecione no mínimo um produto", Toast.LENGTH_SHORT).show();
            return;
        }else{
            //Retira qualquer produto que tenha quantidade 0
            for(Produto produto: produtos){
                if(produto.getQuantidade() == 0){
                    produtos.remove(produto);
                }
            }

            JSONObject body = montarRequisicao();
            JSONArray req = new JSONArray();

            req.put(body);

            //url para logar com senha
            String url = API_URL + "/auth/v1/token?grant_type=password";


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
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );
            RequestQueue filaRequest = Volley.newRequestQueue(Carrinho.this);
            filaRequest.add(request);
        }

    }

    private JSONObject montarRequisicao()throws JSONException{
        JSONArray listReq = new JSONArray();

        for(Produto produto: produtos){
            JSONObject prod = new JSONObject();
            prod.put("id_produto", produto.getId());
            prod.put("quantidade", produto.getQuantidade());

            listReq.put(prod);
        }
        JSONObject requisicao = new JSONObject();
        requisicao.put("id_usuario", 50);
        requisicao.put("modificado_por", "Vitor");
        requisicao.put("produtos",listReq);

        return requisicao;
    }
}