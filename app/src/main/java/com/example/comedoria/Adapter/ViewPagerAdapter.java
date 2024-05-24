package com.example.comedoria.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comedoria.fragments.PedidosParaRetirar;
import com.example.comedoria.fragments.PedidosRetirados;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Construtor
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Método para criar um Fragment com base na posição
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Retorna um Fragment diferente com base na posição
        switch (position) {
            case 0:
                return new PedidosParaRetirar(); // Retorna o fragmento PedidosParaRetirar para a primeira posição
            case 1:
                return new PedidosRetirados(); // Retorna o fragmento PedidosRetirados para a segunda posição
            default:
                return new PedidosParaRetirar(); // Retorna o fragmento PedidosParaRetirar como padrão
        }
    }

    // Método para obter o número total de itens no ViewPager
    @Override
    public int getItemCount() {
        return 2; // Retorna 2, pois há dois fragments
    }
}
