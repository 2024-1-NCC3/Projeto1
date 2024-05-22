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

<<<<<<< HEAD:app/src/main/java/com/example/comedoria/AdapterCarrinho.java
=======
import com.example.comedoria.activities.Carrinho;
import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;

>>>>>>> DEV:app/src/main/java/com/example/comedoria/Adapter/AdapterCarrinho.java
import java.util.List;
import java.util.Locale;

/**
 * Adapter para gerenciar a exibição de uma lista de produtos no carrinho de compras em um RecyclerView.
 */
public class AdapterCarrinho extends RecyclerView.Adapter<AdapterCarrinho.MyViewHolder> {

    // Lista de produtos no carrinho
    private List<Produto> listaCarrinho;
    // Contexto da aplicação onde o RecyclerView está sendo utilizado
    private Context contextCarrinho;

    /**
     * Construtor do AdapterCarrinho.
     *
     * @param lista          A lista de produtos no carrinho.
     * @param contextCarrinho O contexto da aplicação onde o RecyclerView está sendo utilizado.
     */
    public AdapterCarrinho(List<Produto> lista, Context contextCarrinho) {
        this.listaCarrinho = lista;
        this.contextCarrinho = contextCarrinho;
    }

    /**
     * Método chamado quando o RecyclerView precisa de um novo ViewHolder.
     * Este método inflará o layout de cada item da lista.
     *
     * @param parent   O ViewGroup ao qual esta View pertence.
     * @param viewType O tipo de View (não utilizado aqui pois temos um único tipo).
     * @return Uma nova instância de MyViewHolder com o layout inflado.
     */
    @NonNull
    @Override
    public AdapterCarrinho.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item da lista
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_carrinho, parent, false);
        return new AdapterCarrinho.MyViewHolder(itemLista);
    }

    /**
     * Método chamado pelo RecyclerView para exibir os dados na posição especificada.
     * Este método atualiza o conteúdo do ViewHolder para refletir o item na posição dada.
     *
     * @param holder   O MyViewHolder que deve ser atualizado.
     * @param position A posição do item na lista de dados.
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterCarrinho.MyViewHolder holder, int position) {
        Produto produto = listaCarrinho.get(position);

        // Configura os campos de texto do ViewHolder para exibir os dados do produto
        holder.nomeProduto.setText(produto.getNome());
        holder.precoProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", (produto.getPreco() * produto.getQuantidade())));
        holder.quantidadeProduto.setText(String.valueOf(produto.getQuantidade()));

<<<<<<< HEAD:app/src/main/java/com/example/comedoria/AdapterCarrinho.java
        // Configura o botão de adicionar quantidade
=======
        TextPaint paintQuantidade = holder.quantidadeProduto.getPaint();
        float charWidthQuantidade = paintQuantidade.measureText("000");
        holder.quantidadeProduto.setWidth((int) (charWidthQuantidade));

        TextPaint paintPreco = holder.precoProduto.getPaint();
        float charWidthPreco = paintPreco.measureText("R$ 0000,00");
        holder.precoProduto.setWidth((int) (charWidthPreco));


>>>>>>> DEV:app/src/main/java/com/example/comedoria/Adapter/AdapterCarrinho.java
        holder.botaoAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produto.setQuantidade(produto.getQuantidade() + 1);
                notifyDataSetChanged();

                if (contextCarrinho instanceof Carrinho) {
                    ((Carrinho) contextCarrinho).atualizarLista(listaCarrinho);
                    ((Carrinho) contextCarrinho).atualizarTotal();
                }
            }
        });

        // Configura o botão de retirar quantidade
        holder.botaoRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (produto.getQuantidade() - 1 >= 0) {
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    notifyDataSetChanged();

                    if (contextCarrinho instanceof Carrinho) {
                        ((Carrinho) contextCarrinho).atualizarLista(listaCarrinho);
                        ((Carrinho) contextCarrinho).atualizarTotal();
                    }
                }
            }
        });
    }

    /**
     * Retorna o número total de itens na lista de dados.
     *
     * @return O número total de itens.
     */
    @Override
    public int getItemCount() {
        return listaCarrinho.size();
    }

    /**
     * ViewHolder para os itens do RecyclerView.
     * Este ViewHolder contém as referências aos elementos de UI do item de lista.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Campos de texto para o nome do produto, preço e quantidade
        TextView nomeProduto;
        TextView precoProduto;
        TextView quantidadeProduto;
        // Botões para adicionar e retirar quantidade do produto
        ImageView botaoAdicionar;
        ImageView botaoRetirar;

        /**
         * Construtor de MyViewHolder.
         * Inicializa os elementos de UI do item de lista.
         *
         * @param itemView A View do item de lista.
         */
        @SuppressLint("ResourceAsColor")
        public MyViewHolder(View itemView) {
            super(itemView);

            // Encontra os elementos de UI no layout do item de lista
            nomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            precoProduto = itemView.findViewById(R.id.txtSoma);
            quantidadeProduto = itemView.findViewById(R.id.txtQuantidade);

            botaoAdicionar = itemView.findViewById(R.id.imgAdicionar);
            botaoRetirar = itemView.findViewById(R.id.imgRetirar);
        }
    }
}
