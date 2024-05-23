package com.example.comedoria.activities;

import static com.example.comedoria.BuildConfig.API_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.comedoria.Adapter.AdapterResumoPedido;
import com.example.comedoria.Class.Comprovante;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConectorAPI;
import com.example.comedoria.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ComprovantePedido extends AppCompatActivity {

    ImageView imgQRCode;

    String numPedido;
    TextView textData, tituloPedido, statusPedido;
    String accessToken,idPedido;
    Comprovante comprovante;
    RecyclerView recyclerResumo;
    AdapterResumoPedido adapterResumoPedido;
    List<Produto> produtos = new ArrayList<>();

    int hora, minuto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_pedido);

        accessToken = getIntent().getStringExtra("accessToken");
        idPedido = getIntent().getStringExtra("idPedido");

        buscarPedido(idPedido);
        textData = findViewById(R.id.textData);
        tituloPedido = findViewById(R.id.tituloNumPedido);
        statusPedido = findViewById(R.id.statusPedido);
        imgQRCode = findViewById(R.id.imgQRCode);

        adapterResumoPedido = new AdapterResumoPedido(produtos, this);
        recyclerResumo = findViewById(R.id.recycleResumo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerResumo.setLayoutManager(layoutManager);
        recyclerResumo.setHasFixedSize(true);

        recyclerResumo.setAdapter(adapterResumoPedido);
    }

    public void buscarPedido(String idPedido){
        Map<String, String> headers = new HashMap<>();
        //define os heades que a solicitação vai precisar
        headers.put("apikey", API_KEY);
        headers.put("Authorization", "Bearer " + accessToken);
        ConectorAPI.conexaoArrayGET(
                "/rest/v1/pedido?id_pedido=eq." + idPedido + "&select=*,produtos(nome_produto,preco),detalhes_pedido(quantidade)",
                headers,
                getApplicationContext(),
                new ConectorAPI.VolleyArrayCallback() {
                    @Override
                    public void onSuccess(JSONArray response) throws JSONException {
                        if(response.length()>0){
                            JSONObject resposta = response.getJSONObject(0);

                            String status = resposta.getString("status");
                            String observacoes = resposta.getString("observacoes");
                            int numeroPedido = resposta.getInt("numero_pedido");
                            String idPedido = resposta.getString("id_pedido");
                            String dataRetirada = resposta.getString("data_para_retirada");
                            String horaRetirada = resposta.getString("hora_para_retirada");

                            JSONArray produtos = resposta.getJSONArray("produtos");
                            JSONArray detalhesPedido = resposta.getJSONArray("detalhes_pedido");
                            List<Produto> listaProdutos = new ArrayList<>();

                            for(int i =0; i< produtos.length();i++){
                                JSONObject item = produtos.getJSONObject(i);
                                JSONObject objetoDetalhes = detalhesPedido.getJSONObject(i);

                                double preco = item.getDouble("preco");
                                String nomeProd = item.getString("nome_produto");
                                int quantidade = objetoDetalhes.getInt("quantidade");

                                listaProdutos.add(new Produto(nomeProd,preco,quantidade));
                            }

                            comprovante = new Comprovante(status,observacoes,numeroPedido,dataRetirada,horaRetirada,listaProdutos, idPedido);
                            Log.i("Supabase", comprovante.toString());

                            tituloPedido.setText("Pedido nº " +comprovante.getNumeroPedido());
                            statusPedido.setText(comprovante.getStatus());

                            generateQR();

                            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");
                            try {
                                Date dataFormatada = formato.parse(comprovante.getDataRetirada());
                                textData.setText("Retirar as: " + meuFormato.format(dataFormatada) +
                                        " "+comprovante.getHoraRetirada());
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }

                            adapterResumoPedido.setListaProduto(comprovante.getListaProdutos());
                            adapterResumoPedido.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                }
        );
    }


    public void voltarTelaComprovante(View view){
        finish();
    }

    public void voltarInicio(View view){
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }


    
    private void interpretarJsonArray(JSONArray response){

    }
    public void atualizarLista(List<Produto> listaAtualizada){
        this.produtos = listaAtualizada;
    }

    public void generateQR(){
        String textQr = String.valueOf(comprovante.getIdPedido());
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(textQr, BarcodeFormat.QR_CODE, 700, 700);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imgQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

}