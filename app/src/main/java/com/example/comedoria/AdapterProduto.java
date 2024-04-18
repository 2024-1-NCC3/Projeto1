package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;
    private android.content.Context context;

    public AdapterProduto(List<Produto> lista){
        this.listaProdutos = lista;
    }
    @NonNull

    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_produto, parent, false);
        return new MyViewHolder1(itemLista);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.descricaoProduto.setText(produto.getNome());
        holder.imgProduto.setImageResource(produto.getCaminhoImg());
        holder.cbProduto.setText("R$ " + produto.getPreco() );




    }





    @Override
    public int getItemCount() {

        return listaProdutos.size();
    }

    public class MyViewHolder1 extends  RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;

        CheckBox cbProduto;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder1(View itemView){
            super(itemView);

            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);


        }


    }


}
