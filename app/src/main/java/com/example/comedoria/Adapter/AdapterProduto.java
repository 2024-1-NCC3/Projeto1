package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Produto;
import com.example.comedoria.ConfirmarPedido;
import com.example.comedoria.R;
import com.example.comedoria.activities.Estoque;
import com.example.comedoria.activities.Produtos;
import com.squareup.picasso.Picasso;


import java.util.List;
import java.util.Locale;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;
    private android.content.Context context;

    public void setFilteredList(List<Produto> listaFiltrada){
        listaProdutos = listaFiltrada;
        notifyDataSetChanged();
    };
    public AdapterProduto(Context context, List<Produto> lista){
        this.listaProdutos = lista;
        this.context = context;
    }
    @NonNull

    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder1(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);

        if(!produto.isAparecer()){
            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        }else{
            holder.rootView.setVisibility(View.VISIBLE);
        }

        holder.descricaoProduto.setText(produto.getNome());
        Picasso.get().load(produto.getCaminhoImg()).into(holder.imgProduto);

        holder.cbProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        holder.cbProduto.setOnCheckedChangeListener(null);

        holder.cbProduto.setChecked(produto.isSelecionado());
        holder.cbProduto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                produto.setSelecionado(!produto.isSelecionado());
                Log.i("Checkboxes", produto.getNome());

            }
        });

        holder.imgProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Produtos)context).abrirIngredientes(produto.getId(), produto.getNome(), produto.getCaminhoImg(),
                        produto.getIngrediente());
            }
        });
    }
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;
        CheckBox cbProduto;
        View rootView;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder1(View itemView){
            super(itemView);
            rootView = itemView;
            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);



        }


    }


}
