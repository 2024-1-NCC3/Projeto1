package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.activities.Carrinho;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

import java.util.List;
import java.util.Locale;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {

    private List<Produto> listaCarrinho;
    private Context contextCarrinho;

    // Método para inflar o layout do item de carrinho quando necessário
    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new AdapterCarrinho.MyViewHolder(itemLista);
    }

    // Construtor do adaptador
    public AdapterCarrinho(List<Produto> lista, Context contextCarrinho){
        this.listaCarrinho = lista;
        this.contextCarrinho = contextCarrinho;
    }

    // Método chamado quando um novo item de carrinho é exibido
    @Override
    public void onBindViewHolder(@NonNull AdapterCarrinho.MyViewHolder holder, int position) {
        Produto produto = listaCarrinho.get(position);

        // Define o nome do produto
        holder.nomeProduto.setText(produto.getNome());

        // Define o preço total do produto (quantidade * preço unitário)
        holder.precoProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", (produto.getPreco() * produto.getQuantidade())) );

        // Define a quantidade do produto no carrinho
        holder.quantidadeProduto.setText(produto.getQuantidade()+"");

        // Calcula a largura do TextView com base no tamanho do texto
        TextPaint paintQuantidade = holder.quantidadeProduto.getPaint();
        float charWidthQuantidade = paintQuantidade.measureText("000");
        holder.quantidadeProduto.setWidth((int) (charWidthQuantidade));

        TextPaint paintPreco = holder.precoProduto.getPaint();
        float charWidthPreco = paintPreco.measureText("R$ 0000,00");
        holder.precoProduto.setWidth((int) (charWidthPreco));

        // Configuração do botão para adicionar um item ao carrinho
        holder.botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getQuantidade() + 1 <= produto.getEstoque()){
                    // Incrementa a quantidade do produto
                    produto.setQuantidade(produto.getQuantidade() + 1);
                    notifyDataSetChanged();

                    // Atualiza a lista de produtos e o total no carrinho
                    if(contextCarrinho instanceof Carrinho){
                        ((Carrinho)contextCarrinho).atualizarLista(listaCarrinho);
                        ((Carrinho)contextCarrinho).atualizarTotal();
                    }
                }else{
                    // Exibe uma mensagem de aviso se a quantidade exceder o estoque disponível
                    Toast.makeText(contextCarrinho, "Desculpe, não temos essa quantidade disponível", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuração do botão para remover um item do carrinho
        holder.botaoRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produto.getQuantidade() - 1 >= 0){
                    // Decrementa a quantidade do produto
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    notifyDataSetChanged();

                    // Atualiza a lista de produtos e o total no carrinho
                    if(contextCarrinho instanceof Carrinho){
                        ((Carrinho)contextCarrinho).atualizarLista(listaCarrinho);
                        ((Carrinho)contextCarrinho).atualizarTotal();
                    }
                }
            }
        });
    }

    // Retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return listaCarrinho.size();
    }

    // Classe ViewHolder que contém as visualizações dos itens de carrinho
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProduto;
        TextView precoProduto;
        TextView quantidadeProduto;
        ImageView botaoAdicionar;
        ImageView botaoRetirar;

        // Construtor que inicializa as visualizações
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
