package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Carrinho;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

import java.util.List;
import java.util.Locale;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {

    private List<Produto> listaCarrinho;
    private Context contextCarrinho;

    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new AdapterCarrinho.MyViewHolder(itemLista);
    }

    public AdapterCarrinho(List<Produto> lista, Context contextCarrinho){
        this.listaCarrinho = lista;
        this.contextCarrinho = contextCarrinho;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarrinho.MyViewHolder holder, int position) {
        Produto produto = listaCarrinho.get(position);

        holder.nomeProduto.setText(produto.getNome());
        holder.precoProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", (produto.getPreco() * produto.getQuantidade())) );
        holder.quantidadeProduto.setText(produto.getQuantidade()+"");

        holder.botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produto.setQuantidade(produto.getQuantidade() + 1);
                notifyDataSetChanged();

                if(contextCarrinho instanceof Carrinho){
                    ((Carrinho)contextCarrinho).atualizarLista(listaCarrinho);
                    ((Carrinho)contextCarrinho).atualizarTotal();
                }

            }
        });

        holder.botaoRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getQuantidade() - 1 >= 0){
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    notifyDataSetChanged();

                    if(contextCarrinho instanceof Carrinho){
                        ((Carrinho)contextCarrinho).atualizarLista(listaCarrinho);
                        ((Carrinho)contextCarrinho).atualizarTotal();
                    }
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaCarrinho.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProduto;
        TextView precoProduto;
        TextView quantidadeProduto;
        ImageView botaoAdicionar;
        ImageView botaoRetirar;


        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView){
            super(itemView);

            nomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            precoProduto = itemView.findViewById(R.id.txtSoma);
            quantidadeProduto = itemView.findViewById(R.id.txtQuantidade);

            botaoAdicionar = itemView.findViewById(R.id.imgAdicionar);
            botaoRetirar = itemView.findViewById(R.id.imgRetirar);

        }
    }
}
