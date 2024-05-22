package com.example.comedoria.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

import java.util.List;
import java.util.Locale;

public class AdapterConfirmar extends RecyclerView.Adapter<AdapterConfirmar.MyViewHolder> {
    // Lista de produtos a serem exibidos
    List<Produto> listaProduto;

    // Construtor do adaptador
    public AdapterConfirmar(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    // Método chamado quando o RecyclerView precisa de uma nova ViewHolder
    @NonNull
    @Override
    public AdapterConfirmar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item e cria uma nova instância de MyViewHolder
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirmar, parent, false);
        return new AdapterConfirmar.MyViewHolder(itemLista);
    }

    // Método chamado quando RecyclerView precisa de uma ViewHolder vinculada aos dados
    @Override
    public void onBindViewHolder(@NonNull AdapterConfirmar.MyViewHolder holder, int position) {
        // Obtém o produto na posição atual da lista
        Produto produto = listaProduto.get(position);

        // Define os dados do produto nas views
        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtQuantidade.setText(String.valueOf(produto.getQuantidade()));
        holder.txtPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getQuantidade() * produto.getPreco()));
    }

    // Retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    // Classe interna que representa a ViewHolder para cada item na lista
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Declaração das views dentro do ViewHolder
        TextView txtNomeProduto, txtQuantidade, txtPreco, txtTotal;

        // Construtor da ViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializa as views
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProdConfirmar);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidadeConfirmar);
            txtPreco = itemView.findViewById(R.id.txtPrecoConfirmar);
            txtTotal = itemView.findViewById(R.id.txtTotalConfirmar);
        }
    }
}
