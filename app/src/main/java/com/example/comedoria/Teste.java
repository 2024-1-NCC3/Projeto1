package com.example.comedoria;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Teste extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<Categoria> listaCategorias = new ArrayList<>();

    private int[] listaImg = {
            R.drawable.promocao,
            R.drawable.bebidassazonais,
            R.drawable.delivery_capa
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste);

        recycleView = findViewById(R.id.recycleView);

        listarCategorias();

        Adapter adapter = new Adapter(listaCategorias);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recycleView.setLayoutManager(layoutManager);
        recycleView.setHasFixedSize(true);

        recycleView.setAdapter(adapter);
    }

    private void listarCategorias(){
        Categoria categoria = new Categoria("Oferta","Aproveite nossas promoções!", listaImg[0]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Bebida Sazonal","Conheça nossas bebidas sazonais!", listaImg[1]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Marmitas","Experimente nossas marmitas", listaImg[2]);
        listaCategorias.add(categoria);
    }

    public void log(){
        Intent home = new Intent(Teste.this, Login.class);
        startActivity(home);
    }

    public void cardapio(){
        Intent cardapio = new Intent();
        startActivity(cardapio);
    }
    public void carrinho(){
        Intent carrinho = new Intent();
        startActivity(carrinho);
    }
    public void usuario(){
        Intent usuario = new Intent();
        startActivity(usuario);
    }
}
