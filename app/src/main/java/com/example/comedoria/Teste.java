package com.example.comedoria;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Teste extends AppCompatActivity {
    private RecyclerView recycleView;
    private List<Categoria> listaCategorias = new ArrayList<>();
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
        Categoria categoria = new Categoria("Oferta","Aproveite nossas promoções!", "");
        listaCategorias.add(categoria);

        categoria = new Categoria("Bebida Sazonal","Conheça nossas bebidas sazonais!", "");
        listaCategorias.add(categoria);

        categoria = new Categoria("Marmitas","Experimente nossas marmitas", "");
        listaCategorias.add(categoria);
    }
}
