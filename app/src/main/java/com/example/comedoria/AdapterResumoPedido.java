package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterResumoPedido extends RecyclerView.Adapter<AdapterResumoPedido.MyviewHolder> {

    private List<Produto> listaCarrinho;
    private Context contextResumo;

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent,false);
        return new AdapterResumoPedido.MyviewHolder(itemLista);

    }
    public AdapterResumoPedido(List<Produto> lista, Context contextResumo){
        this.listaCarrinho = lista;
        this.contextResumo = contextResumo;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterResumoPedido.MyviewHolder holder, int position) {
        Produto produto = listaCarrinho.get(position);

        holder.nomeProduto.setText((produto.getNome()));
        holder.quantidadeProduto.setText((produto.getQuantidade()));



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{

        TextView nomeProduto;
        TextView quantidadeProduto;





        @SuppressLint("ResourceAsColor")
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.descricaoProduto);
            quantidadeProduto = itemView.findViewById(R.id.qntProduto);
        }
    }






}
