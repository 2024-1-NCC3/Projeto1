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

// Adaptador para exibir e gerenciar os produtos no estoque
public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.ViewHolder> {

    private Context context;
    private List<Produto> listaProdutos;

    // Construtor que recebe o contexto e a lista de produtos
    public AdapterEstoque(Context context, List<Produto> lista){
        this.context = context;
        this.listaProdutos = lista;
    }

    // Método chamado quando o ViewHolder precisa ser criado
    @NonNull
    @Override
    public AdapterEstoque.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Infla o layout do item do estoque
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_estoque, viewGroup, false);
        return new AdapterEstoque.ViewHolder(itemLista);
    }

    // Método chamado para vincular os dados do produto ao ViewHolder
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Produto produto = listaProdutos.get(i);

        // Define o nome do produto
        viewHolder.textTitle.setText(produto.getNome());
        // Define a quantidade do produto
        viewHolder.textQuantidade.setText("Quantidade: " + produto.getQuantidade());
        // Carrega a imagem do produto usando Picasso
        Picasso.get().load(produto.getCaminhoImg()).into(viewHolder.imgProduto);
        // Define o preço do produto
        viewHolder.textPreco.setText("Preço: " + String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        // Define o comportamento do botão para modificar o produto
        viewHolder.modificarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chama o método na atividade Estoque para modificar o produto
                ((Estoque)context).irModificarProduto(produto.getId(), produto.getNome(), produto.getCaminhoImg(),
                        produto.getQuantidade(), produto.getPreco(), produto.getIdEstoque());
            }
        });
    }

    // Retorna a quantidade de itens na lista de produtos
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Método para obter a lista de produtos
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    // Classe ViewHolder que representa cada item na RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle;
        TextView textPreco;
        TextView textQuantidade;
        ImageView imgProduto;
        TextView modificarProduto;

        // Construtor que recebe a View do item
        @SuppressLint("ResourceAsColor")
        public ViewHolder(View itemView){
            super(itemView);
            // Inicializa os componentes do layout do item
            textTitle = itemView.findViewById(R.id.txtNomeProdutoModificarProduto);
            textQuantidade = itemView.findViewById(R.id.txtQuantidadeEstoque);
            textPreco = itemView.findViewById(R.id.txtPrecoEstoque);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            modificarProduto = itemView.findViewById(R.id.btnModificarProduto);
        }
    }
}
