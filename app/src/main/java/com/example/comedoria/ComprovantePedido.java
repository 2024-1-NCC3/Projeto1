package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class ComprovantePedido extends AppCompatActivity {

    TextView textData, tituloPedido, statusPedido;

    RecyclerView recyclerResumo;

    List<Produto> produtos;

    int dia, mes;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprovante_pedido);

        textData = findViewById(R.id.textData);
        tituloPedido = findViewById(R.id.tituloPedido);
        statusPedido = findViewById(R.id.statusPedido);

        String horaRetirada = getIntent().getStringExtra("retirarProduto");
        textData.setText(horaRetirada);


        recyclerResumo = findViewById(R.id.recycleResumo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerResumo.setLayoutManager(layoutManager);
        recyclerResumo.setHasFixedSize(true);

        AdapterResumoPedido adapterResumoPedido = new AdapterResumoPedido(produtos, this);
        recyclerResumo.setAdapter(adapterResumoPedido);




    }
    public void atualizarLista(List<Produto> listaAtualizada){
        this.produtos = listaAtualizada;
    }





}