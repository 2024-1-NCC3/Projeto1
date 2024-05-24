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

    List<Produto> listaProduto;

    // Construtor que recebe a lista de produtos
    public AdapterConfirmar(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    // Método chamado quando uma nova ViewHolder é criada
    @NonNull
    @Override
    public AdapterConfirmar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item da lista
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirmar, parent, false);
        // Retorna uma nova instância da ViewHolder com o layout do item
        return new AdapterConfirmar.MyViewHolder(itemLista);
    }

    // Método chamado para associar os dados de um produto à ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AdapterConfirmar.MyViewHolder holder, int position) {
        Produto produto = listaProduto.get(position);

        // Define os valores dos TextViews com os dados do produto
        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtQuantidade.setText(String.valueOf(produto.getQuantidade())); // Converte a quantidade para String
        holder.txtPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco())); // Formata o preço
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getQuantidade() * produto.getPreco())); // Calcula e formata o total
    }

    // Retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    // Classe ViewHolder que contém as visualizações dos itens da lista
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeProduto, txtQuantidade, txtPreco, txtTotal;

        // Construtor que inicializa as visualizações
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializa os TextViews
            txtNomeProduto = itemView.findViewById(R.id.txtNomeProdConfirmar);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidadeConfirmar);
            txtPreco = itemView.findViewById(R.id.txtPrecoConfirmar);
            txtTotal = itemView.findViewById(R.id.txtTotalConfirmar);
        }
    }
}
