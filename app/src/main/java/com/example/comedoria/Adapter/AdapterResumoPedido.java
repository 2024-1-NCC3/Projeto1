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

    // Método chamado para criar um novo ViewHolder
    @NonNull
    @Override
    public AdapterResumoPedido.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do resumo do pedido
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent, false);
        return new AdapterResumoPedido.MyViewHolder(itemLista);
    }

    // Retorna a quantidade de itens no resumo do pedido
    @Override
    public int getItemCount() {
        return listaResumo.size();
    }

    // Construtor do adaptador
    public AdapterResumoPedido(List<Produto> lista, Context contextResumo){
        this.listaResumo = lista;
        this.contextResumo = contextResumo;
    }

    // Método para definir a lista de produtos do resumo do pedido
    public void setListaProduto(List<Produto> listaProduto){
        this.listaResumo = listaProduto;
    }

    // Método chamado para vincular os dados do produto ao ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AdapterResumoPedido.MyViewHolder holder, int position) {
        Produto produto = listaResumo.get(position);

        // Define o nome e a quantidade do produto no resumo do pedido
        holder.nomeProduto.setText(produto.getNome());
        holder.quantidadeProduto.setText(String.valueOf(produto.getQuantidade()));
    }

    // Classe ViewHolder que representa cada item no resumo do pedido na RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeProduto;
        TextView quantidadeProduto;

        // Construtor que recebe a View do item do resumo do pedido
        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);
            // Inicializa os componentes do layout do item do resumo do pedido
            nomeProduto = itemView.findViewById(R.id.descricaoResumoProduto);
            quantidadeProduto = itemView.findViewById(R.id.qntProduto);
        }
    }
}
