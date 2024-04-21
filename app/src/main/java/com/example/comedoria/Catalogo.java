package com.example.comedoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View; // Importe esta classe

import java.util.ArrayList;
import java.util.List;

public class Catalogo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Categoria> listaCategorias = new ArrayList<>();

    // Array de IDs das imagens das categorias
    private int[] listaImg = {
            R.drawable.promocao,
            R.drawable.marmitas,
            R.drawable.salgados,
            R.drawable.doces,
            R.drawable.picole,
            R.drawable.bebidas
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo); // Define o layout da atividade

        recyclerView = findViewById(R.id.recycleView); // Obtém a referência do RecyclerView do layout

        listarCategorias(); // Chama o método para preencher a lista de categorias

        // Cria um adaptador e define-o para o RecyclerView
        Adapter adapter = new Adapter(listaCategorias);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    // Método para preencher a lista de categorias
    private void listarCategorias() {
        // Cria instâncias de Categoria e adiciona à lista
        Categoria categoria = new Categoria("Oferta", "Aproveite nossas ofertas!", listaImg[0]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Marmitas", "Conheça nossas marmitas!", listaImg[1]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Salgados", "Experimente nossos salgados", listaImg[2]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Doces", "Experimente nossos doces", listaImg[3]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Picolés", "Experimente nossos picolés", listaImg[4]);
        listaCategorias.add(categoria);

        categoria = new Categoria("Bebidas", "Experimente nossas bebidas", listaImg[5]);
        listaCategorias.add(categoria);
    }

    // Método chamado quando o botão "home" é clicado
    public void home(View view) {
        // Cria uma intent para iniciar a atividade de Login e inicia-a
        Intent home = new Intent(Catalogo.this, Teste.class);
        startActivity(home);
    }
}
