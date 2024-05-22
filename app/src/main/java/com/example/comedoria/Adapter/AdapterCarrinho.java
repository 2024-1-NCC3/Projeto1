package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.activities.Carrinho;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

import java.util.List;
import java.util.Locale;

public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {

    // Lista de produtos no carrinho
    private List<Produto> listaCarrinho;
    // Contexto da atividade ou fragmento que usa este adaptador
    private Context contextCarrinho;

    // Método chamado quando o RecyclerView precisa de uma nova ViewHolder
    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item e cria uma nova instância de MyViewHolder
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new AdapterCarrinho.MyViewHolder(itemLista);
    }

    // Construtor do adaptador
    public AdapterCarrinho(List<Produto> lista, Context contextCarrinho){
        this.listaCarrinho = lista;
        this.contextCarrinho = contextCarrinho;
    }

    // Método chamado quando RecyclerView precisa de uma ViewHolder vinculada aos dados
    @Override
    public void onBindViewHolder(@NonNull AdapterCarrinho.MyViewHolder holder, int position) {
        // Obtém o produto na posição atual da lista
        Produto produto = listaCarrinho.get(position);

        // Define o nome, preço e quantidade do produto nas TextViews
        holder.nomeProduto.setText(produto.getNome());
        holder.precoProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", (produto.getPreco() * produto.getQuantidade())));
        holder.quantidadeProduto.setText(produto.getQuantidade() + "");

        // Ajusta a largura das TextViews para garantir um layout consistente
        TextPaint paintQuantidade = holder.quantidadeProduto.getPaint();
        float charWidthQuantidade = paintQuantidade.measureText("000");
        holder.quantidadeProduto.setWidth((int) (charWidthQuantidade));

        TextPaint paintPreco = holder.precoProduto.getPaint();
        float charWidthPreco = paintPreco.measureText("R$ 0000,00");
        holder.precoProduto.setWidth((int) (charWidthPreco));

        // Define os listeners de clique nos botões de adicionar e retirar
        holder.botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Incrementa a quantidade do produto e notifica o RecyclerView
                produto.setQuantidade(produto.getQuantidade() + 1);
                notifyDataSetChanged();

                // Atualiza a lista e o total no CarrinhoActivity
                if(contextCarrinho instanceof Carrinho){
                    ((Carrinho)contextCarrinho).atualizarLista(listaCarrinho);
                    ((Carrinho)contextCarrinho).atualizarTotal();
                }
            }
        });

        holder.botaoRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Decrementa a quantidade do produto e notifica o RecyclerView
                if(produto.getQuantidade() - 1 >= 0){
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    notifyDataSetChanged();

                    // Atualiza a lista e o total no CarrinhoActivity
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

    // Classe interna que representa a ViewHolder para cada item na lista
    public class MyViewHolder extends RecyclerView.ViewHolder{
        // Declaração das views dentro do ViewHolder
        TextView nomeProduto;
        TextView precoProduto;
        TextView quantidadeProduto;
        ImageView botaoAdicionar;
        ImageView botaoRetirar;

        // Construtor da ViewHolder
        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView){
            super(itemView);

            // Inicialização das views
            nomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            precoProduto = itemView.findViewById(R.id.txtSoma);
            quantidadeProduto = itemView.findViewById(R.id.txtQuantidade);
            botaoAdicionar = itemView.findViewById(R.id.imgAdicionar);
            botaoRetirar = itemView.findViewById(R.id.imgRetirar);
        }
    }
}
