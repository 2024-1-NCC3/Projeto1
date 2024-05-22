package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterResumoPedido extends RecyclerView.Adapter<AdapterResumoPedido.MyViewHolder> {

    private List<Produto> listaResumo; // Lista de produtos para exibir no resumo do pedido
    private Context contextResumo; // Contexto da aplicação

    @NonNull
    @Override
    public AdapterResumoPedido.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        // Infla o layout do item do resumo do pedido
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_resumo_pedido, parent,false);
        return new AdapterResumoPedido.MyViewHolder(itemLista);
    }

    /**
     * Retorna o número total de itens no conjunto de dados mantido pelo adaptador.
     *
     * @return O número total de itens.
     */
    @Override
    public int getItemCount() {
        return listaResumo.size();
    }

    /**
     * Construtor do AdapterResumoPedido.
     *
     * @param lista Lista de produtos para exibir no resumo do pedido.
     * @param context Contexto da aplicação.
     */
    public AdapterResumoPedido(List<Produto> lista, Context context){
        this.listaResumo = lista;
        this.contextResumo = context;
    }

    /**
     * Define uma nova lista de produtos para exibir no resumo do pedido.
     *
     * @param listaProduto Lista de produtos.
     */
    public void setListaProduto(List<Produto> listaProduto){
        this.listaResumo = listaProduto;
    }

    /**
     * Atualiza o conteúdo de um ViewHolder para refletir o item na posição dada.
     *
     * @param holder O ViewHolder que deve ser atualizado para representar o conteúdo do item na posição dada no conjunto de dados.
     * @param position A posição do item dentro dos dados.
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterResumoPedido.MyViewHolder holder, int position) {
        Produto produto = listaResumo.get(position);

        holder.nomeProduto.setText(produto.getNome()); // Define o nome do produto
        holder.quantidadeProduto.setText(String.valueOf(produto.getQuantidade())); // Define a quantidade do produto
    }

    /**
     * Classe ViewHolder que representa os itens de dados e as views sobrepostas a eles.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto; // TextView para exibir o nome do produto
        TextView quantidadeProduto; // TextView para exibir a quantidade do produto

        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);
            // Inicializa as TextViews a partir dos IDs definidos no layout do item do resumo do pedido
            nomeProduto = itemView.findViewById(R.id.descricaoResumoProduto);
            quantidadeProduto = itemView.findViewById(R.id.qntProduto);
        }
    }
}
