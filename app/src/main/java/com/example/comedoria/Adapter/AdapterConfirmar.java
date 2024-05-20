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

    public AdapterConfirmar(List<Produto> listaProduto) {
        this.listaProduto = listaProduto;
    }

    @NonNull
    @Override
    public AdapterConfirmar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirmar, parent, false);
        return new AdapterConfirmar.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConfirmar.MyViewHolder holder, int position) {
        Produto produto = listaProduto.get(position);

        holder.txtNomeProduto.setText(produto.getNome());
        holder.txtQuantidade.setText(produto.getQuantidade()+"");
        holder.txtPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getQuantidade() * produto.getPreco()));


    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeProduto, txtQuantidade, txtPreco, txtTotal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNomeProduto = itemView.findViewById(R.id.txtNomeProdConfirmar);
            txtQuantidade = itemView.findViewById(R.id.txtQuantidadeConfirmar);
            txtPreco = itemView.findViewById(R.id.txtPrecoConfirmar);
            txtTotal = itemView.findViewById(R.id.txtTotalConfirmar);
        }
    }
}
