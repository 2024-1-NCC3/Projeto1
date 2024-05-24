package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

import java.util.List;

public class AdapterResumoPedido extends RecyclerView.Adapter<AdapterResumoPedido.MyViewHolder> {

    private List<Produto> listaResumo;
    private Context contextResumo;

    @NonNull
    @Override

    /**Carrega a lista e o contexto da tela*/
    public AdapterResumoPedido.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent,false);
        return new AdapterResumoPedido.MyViewHolder(itemLista);
    }

    /**Define o tamanho da lista carregada*/
    @Override
    public int getItemCount() {
        return listaResumo.size();
    }

    /**Carrega a lista e o contexto da tela*/
    public AdapterResumoPedido(List<Produto> lista, Context contextResumo){
        this.listaResumo = lista;
        this.contextResumo = contextResumo;
    }

    /**Carrega a lista do resumo do pedido*/
    public void setListaProduto(List<Produto> listaProduto){
        this.listaResumo = listaProduto;
    }

    /**Popula as Views geradas com funções*/
    @Override
    public void onBindViewHolder(@NonNull AdapterResumoPedido.MyViewHolder holder, int position) {
        Produto produto = listaResumo.get(position);

        holder.nomeProduto.setText((produto.getNome()));
        holder.quantidadeProduto.setText((produto.getQuantidade() +""));
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeProduto;
        TextView quantidadeProduto;


        @SuppressLint("ResourceAsColor")

        public MyViewHolder(View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.descricaoResumoProduto);
            quantidadeProduto = itemView.findViewById(R.id.qntProduto);

        }
    }



}

