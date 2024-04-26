package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterResumoPedido extends RecyclerView.Adapter<AdapterResumoPedido.MyViewHolder> {

    private List<Produto> listaResumo;
    private Context contextResumo;

    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent,false);
        return new AdapterResumoPedido.MyViewHolder(itemLista);

    }

    public AdapterResumoPedido(List<Produto> lista, Context contextResumo){
        this.listaResumo = lista;
        this.contextResumo = contextResumo;
    }


}
