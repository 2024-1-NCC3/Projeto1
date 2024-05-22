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

    // Método chamado para criar novas visualizações de itens do resumo do pedido
    @NonNull
    @Override
    public AdapterResumoPedido.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do resumo do pedido
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent, false);
        return new AdapterResumoPedido.MyViewHolder(itemLista);
    }

    // Método chamado para obter o número total de itens no resumo do pedido
    @Override
    public int getItemCount() {
        return listaResumo.size();
    }

    // Construtor do AdapterResumoPedido
    public AdapterResumoPedido(List<Produto> lista, Context contextResumo){
        this.listaResumo = lista;
        this.contextResumo = contextResumo;
    }

    // Método para atualizar a lista de produtos no resumo do pedido
    public void setListaProduto(List<Produto> listaProduto){
        this.listaResumo = listaProduto;
    }

    // Método chamado para preencher os dados de um item do resumo do pedido na visualização
    @Override
    public void onBindViewHolder(@NonNull AdapterResumoPedido.MyViewHolder holder, int position) {
        Produto produto = listaResumo.get(position);

        // Define o nome do produto
        holder.nomeProduto.setText((produto.getNome()));
        // Define a quantidade do produto
        holder.quantidadeProduto.setText((produto.getQuantidade() +""));
    }

    // Classe ViewHolder para representar os itens do resumo do pedido na visualização
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeProduto;
        TextView quantidadeProduto;

        // Construtor ViewHolder
        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);
            // Inicializa os componentes da visualização
            nomeProduto = itemView.findViewById(R.id.descricaoResumoProduto);
            quantidadeProduto = itemView.findViewById(R.id.qntProduto);
        }
    }
}
