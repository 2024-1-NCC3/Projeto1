package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.ViewHolder> {
    private Context context;
    private List<Produto> listaProdutos;

    // Construtor do AdapterEstoque
    public AdapterEstoque(Context context, List<Produto> lista){
        this.context = context;
        this.listaProdutos = lista;
    }

    // Método chamado para criar novas visualizações de itens de estoque
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Infla o layout do modelo do estoque
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.modelo_estoque, viewGroup, false);
        return new ViewHolder(itemLista);
    }

    // Método chamado para preencher os dados de um item de estoque na visualização
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Obtém o objeto Produto da lista na posição i
        Produto produto = listaProdutos.get(i);

        // Define o nome do produto
        viewHolder.textTitle.setText(produto.getNome());

        // Define a quantidade do produto e a dica no campo de texto
        viewHolder.textQuantidade.setHint("" + produto.getQuantidade());

        // Carrega a imagem do produto usando Picasso
        Picasso.get().load(produto.getCaminhoImg()).into(viewHolder.imgProduto);

        // Define o preço do produto e a dica no campo de texto
        viewHolder.textPreco.setHint(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        // Adiciona um ouvinte de texto ao campo de preço para atualizar o preço do produto
        viewHolder.textPreco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double preco = Double.parseDouble(charSequence.toString().replace("R$", "").trim());
                produto.setPreco(preco);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Adiciona um ouvinte de texto ao campo de quantidade para atualizar a quantidade do produto
        viewHolder.textQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int quantidade = Integer.parseInt(charSequence.toString().trim());
                produto.setQuantidade(quantidade);
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Adiciona um ouvinte de mudança de estado ao CheckBox para gerenciar a promoção do produto
        viewHolder.cBPromocao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    produto.getCategoria().add("Oferta");
                }else{
                    produto.getCategoria().remove("Oferta");
                }
            }
        });

        // Adiciona um ouvinte de clique ao botão de exclusão para remover o produto da lista
        viewHolder.excluir.setOnClickListener(view -> {
            listaProdutos.remove(i);
            notifyItemRemoved(i);
            notifyItemRangeChanged(i, listaProdutos.size());
        });
    }

    // Método chamado para obter o número total de itens na lista
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Método para obter a lista de produtos
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    // Classe ViewHolder para representar os itens de estoque na visualização
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextInputEditText textPreco;
        TextInputEditText textQuantidade;
        ImageView imgProduto;
        CheckBox cBPromocao;
        TextView excluir;

        // Construtor ViewHolder
        @SuppressLint("ResourceAsColor")
        public ViewHolder(View itemView){
            super(itemView);
            // Inicializa os componentes da visualização
            textTitle = itemView.findViewById(R.id.textTitulo);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
            textPreco = itemView.findViewById(R.id.textPrecoEstoque);
            imgProduto = itemView.findViewById(R.id.imgProduto);
            cBPromocao = itemView.findViewById(R.id.cBPromocao);
            excluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}
