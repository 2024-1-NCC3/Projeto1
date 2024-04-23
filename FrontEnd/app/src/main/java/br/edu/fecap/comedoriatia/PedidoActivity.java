package br.edu.fecap.comedoriatia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class PedidoActivity extends AppCompatActivity {

    private RecyclerView recycleProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        recycleProduto = findViewById(R.id.recycleProduto);



        //configurar RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycleProduto.setLayoutManager(layoutManager);
        recycleProduto.setHasFixedSize(true);


    }
}