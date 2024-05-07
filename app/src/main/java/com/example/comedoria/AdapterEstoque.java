package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.Locale;

public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.ViewHolder> {

    private LayoutInflater layoutInflater;
    private android.content.Context context;
    private List<Produto> listaProdutos;


    AdapterEstoque(Context context, List<Produto> lista){
        this.context = context;
        this.listaProdutos = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_estoque, viewGroup, false);
        return new ViewHolder(itemLista);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Produto produto = listaProdutos.get(i);
        viewHolder.textTitle.setText(produto.getNome());
        viewHolder.textTitle.setText(produto.getNome());
        Picasso.get().load(produto.getCaminhoImg()).into(viewHolder.imgProduto);

        viewHolder.textPreco.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()) );
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textQuantidade, textPreco;
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
