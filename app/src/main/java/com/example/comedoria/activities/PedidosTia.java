package com.example.comedoria.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.comedoria.R;
import com.example.comedoria.Adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PedidosTia extends AppCompatActivity {

    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_tia);

        // Inicialização dos componentes da interface
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager2 = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(this);

        // Configuração do ViewPager2 com o adaptador
        viewPager2.setAdapter(viewPagerAdapter);

        // Definição do comportamento do BottomNavigationView ao selecionar um item
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.para_retirar:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.retirado:
                        viewPager2.setCurrentItem(1);
                        break;
                }
                return false;
            }
        });

        // Atualização do estado do BottomNavigationView ao mudar de página no ViewPager2
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.para_retirar).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.retirado).setChecked(true);
                        break;
                }
                super.onPageSelected(position);
            }
        });

    }

    // Método para voltar à tela anterior
    public void voltarTelaPedidos(View view) {
        finish();
    }

    // Método para voltar à tela inicial
    public void voltarInicio(View view) {
        Intent intent = new Intent(this, PaginaInicial.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
