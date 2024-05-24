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
    private Context context;

    // Método para definir uma nova lista filtrada
    public void setFilteredList(List<Produto> listaFiltrada){
        listaProdutos = listaFiltrada;
        notifyDataSetChanged();
    }

    // Construtor do adaptador
    public AdapterProduto(Context context, List<Produto> lista){
        this.listaProdutos = lista;
        this.context = context;
    }

    // Método chamado para criar um novo ViewHolder
    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do produto
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder1(itemLista);
    }

    // Método chamado para vincular os dados do produto ao ViewHolder
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);

        // Define a visibilidade do item com base na propriedade "aparecer" do produto
        if(!produto.isAparecer()){
            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        }else{
            holder.rootView.setVisibility(View.VISIBLE);
        }

        // Define a descrição do produto e carrega a imagem do produto usando Picasso
        holder.descricaoProduto.setText(produto.getNome());
        Picasso.get().load(produto.getCaminhoImg()).into(holder.imgProduto);

        // Define o texto do preço do produto e verifica se o checkbox está marcado
        holder.cbProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));
        holder.cbProduto.setOnCheckedChangeListener(null);
        holder.cbProduto.setChecked(produto.isSelecionado());

        // Define o comportamento do checkbox ao ser clicado
        holder.cbProduto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                produto.setSelecionado(!produto.isSelecionado());
                Log.i("Checkboxes", produto.getNome());
            }
        });

        // Define o comportamento ao clicar na imagem do produto
        holder.imgProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre a tela de ingredientes do produto
                ((Produtos)context).abrirIngredientes(produto.getNome(), produto.getCaminhoImg(),
                        produto.getIngrediente());
            }
        });
    }

    // Retorna a quantidade de itens na lista de produtos
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Retorna a lista de produtos
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    // Classe ViewHolder que representa cada item na RecyclerView
    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;
        CheckBox cbProduto;
        View rootView;

        // Construtor que recebe a View do item
        public MyViewHolder1(View itemView){
            super(itemView);
            // Inicializa os componentes do layout do item
            rootView = itemView;
            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);
        }
    }
}
