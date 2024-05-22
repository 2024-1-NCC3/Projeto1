package com.example.comedoria.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comedoria.fragments.PedidosParaRetirar;
import com.example.comedoria.fragments.PedidosRetirados;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Construtor do adaptador
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Método para criar um novo fragmento com base na posição
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Determina qual fragmento criar com base na posição
        switch (position){
            case 0: return new PedidosParaRetirar(); // Retorna o fragmento PedidosParaRetirar para a posição 0
            case 1: return new PedidosRetirados(); // Retorna o fragmento PedidosRetirados para a posição 1
            default: return new PedidosParaRetirar(); // Retorna o fragmento PedidosParaRetirar como padrão
        }
    }

    // Método para obter o número total de fragmentos
    @Override
    public int getItemCount() {
        return 2; // Retorna 2, pois temos dois fragmentos
    }
}
