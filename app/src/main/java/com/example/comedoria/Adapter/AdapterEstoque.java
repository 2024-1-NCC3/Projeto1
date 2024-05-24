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

import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;
import com.example.comedoria.activities.Estoque;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.ViewHolder> {
    private android.content.Context context;
    private List<Produto> listaProdutos;

    /**Carrega a lista e o contexto da tela*/
    public AdapterEstoque(Context context, List<Produto> lista){
        this.context = context;
        this.listaProdutos = lista;
    }

    /**Cria as Views baseadas em um modelo*/
    @NonNull
    @Override
    public AdapterEstoque.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_estoque, viewGroup, false);
        return new AdapterEstoque.ViewHolder(itemLista);
    }

    /**Popula as Views geradas com funções*/
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Produto produto = listaProdutos.get(i);
        viewHolder.textTitle.setText(produto.getNome());
        viewHolder.textQuantidade.setText("Quantidade: "+produto.getQuantidade());
        Picasso.get().load(produto.getCaminhoImg()).into(viewHolder.imgProduto);
        viewHolder.textPreco.setText("Preço: "+String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        /**Quando for clicado para modificar algum produto, direciona para a tela de alteração com as informações do produto*/
        viewHolder.modificarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Estoque)context).irModificarProduto(produto.getId(), produto.getNome(), produto.getCaminhoImg(),
                        produto.getQuantidade(), produto.getPreco(), produto.getIdEstoque());
            }
        });
    }

    /**Define o tamanho da lista carregada*/
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    /**Carrega a lista de produtos*/
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle;

        TextView textPreco;

        TextView textQuantidade;
        ImageView imgProduto;

        TextView modificarProduto;


        @SuppressLint("ResourceAsColor")
        public ViewHolder(View itemView){
            super(itemView);
            textTitle = itemView.findViewById(R.id.txtNomeProdutoModificarProduto);
            textQuantidade = itemView.findViewById(R.id.txtQuantidadeEstoque);
            textPreco = itemView.findViewById(R.id.txtPrecoEstoque);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            modificarProduto = itemView.findViewById(R.id.btnModificarProduto);
        }
    }
}
