package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.ViewHolder> {
    private android.content.Context context;
    private List<Produto> listaProdutos;


    AdapterEstoque(Context context, List<Produto> lista){
        this.context = context;
        this.listaProdutos = lista;
    }

    @NonNull
    @Override
    public AdapterEstoque.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_estoque, viewGroup, false);
        return new AdapterEstoque.ViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Produto produto = listaProdutos.get(i);

        viewHolder.textTitle.setText(produto.getNome());
        viewHolder.textQuantidade.setText("Quantidade: "+produto.getQuantidade()+"");
        Picasso.get().load(produto.getCaminhoImg()).into(viewHolder.imgProduto);
        viewHolder.textPreco.setHint(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()) );
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textQuantidade;

        TextInputEditText textPreco;
        ImageView imgProduto;

        @SuppressLint("ResourceAsColor")
        public ViewHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitulo);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
            textPreco = itemView.findViewById(R.id.textPreco);
            imgProduto = itemView.findViewById(R.id.imgProduto);
        }
    }
}
