package com.example.comedoria;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.comedoria.databinding.ActivityInicialBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InicialActivity extends AppCompatActivity {

    private ActivityInicialBinding binding;
    private ListView categorias;

    private String[] itens = {
            "Promoção",
            "Conheça nossas bebidas sazonais",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categorias = findViewById(R.id.categorias);

        binding = ActivityInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.cardapio, R.id.pedido, R.id.usuario)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_inicial);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Cria adaptador para a lista:
        ArrayAdapter <String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                itens
        );

        categorias.setAdapter(adaptador);

        // Adicina clique na lista

        categorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String valorSelecionado = categorias.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), valorSelecionado, Toast.LENGTH_LONG).show();
            }
        });
    }

}