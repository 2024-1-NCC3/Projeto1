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
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;
    private android.content.Context context;

    // Método para atualizar a lista de produtos com base em um filtro
    public void setFilteredList(List<Produto> listaFiltrada){
        listaProdutos = listaFiltrada;
        notifyDataSetChanged();
    }

    // Construtor do AdapterProduto
    public AdapterProduto(Context context, List<Produto> lista){
        this.listaProdutos = lista;
        this.context = context;
    }

    // Método chamado para criar novas visualizações de itens de produtos
    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do card do produto
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder1(itemLista);
    }

    // Método chamado para preencher os dados de um item de produto na visualização
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);

        // Define a visibilidade do card com base na propriedade "aparecer" do produto
        if(!produto.isAparecer()){
            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        } else {
            holder.rootView.setVisibility(View.VISIBLE);
        }

        // Define a descrição do produto
        holder.descricaoProduto.setText(produto.getNome());

        // Carrega a imagem do produto usando Picasso
        Picasso.get().load(produto.getCaminhoImg()).into(holder.imgProduto);

        // Define o preço do produto no CheckBox
        holder.cbProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        // Define o estado do CheckBox como selecionado ou não
        holder.cbProduto.setOnCheckedChangeListener(null);
        holder.cbProduto.setChecked(produto.isSelecionado());
        holder.cbProduto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // Atualiza o estado de seleção do produto
                produto.setSelecionado(!produto.isSelecionado());
                // Log para verificar a seleção do CheckBox
                Log.i("Checkboxes", produto.getNome());
            }
        });
    }

    // Método chamado para obter o número total de itens na lista de produtos
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Método para obter a lista de produtos
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    // Classe ViewHolder para representar os itens de produtos na visualização
    public class MyViewHolder1 extends RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;
        CheckBox cbProduto;
        View rootView;

        // Construtor ViewHolder
        @SuppressLint("ResourceAsColor")
        public MyViewHolder1(View itemView){
            super(itemView);
            // Inicializa os componentes da visualização
            rootView = itemView;
            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);
        }
    }
}
