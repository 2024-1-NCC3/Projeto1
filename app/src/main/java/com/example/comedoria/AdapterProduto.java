package com.example.comedoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;


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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.descricaoProduto.setText(produto.getNome());
        holder.imgProduto.setImageResource(produto.getCaminhoImg());
        holder.radioProduto.setText(produto.getPreco() + "");

    }



    @Override
    public int getItemCount() {

        return listaProdutos.size();
    }

    public class MyViewHolder1 extends  RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;

        RadioButton radioProduto;

        public MyViewHolder1(View itemView){
            super(itemView);

            radioProduto = itemView.findViewById(R.id.rbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);
        }
    }
}
