package com.example.comedoria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Categoria;
import com.example.comedoria.activities.PaginaInicial;
import com.example.comedoria.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter para gerenciar a exibição de uma lista de categorias em um RecyclerView.
 */
public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {
    // Lista de categorias a serem exibidas
    private List<Categoria> listaCategorias;
    // Contexto da aplicação onde o RecyclerView está sendo utilizado
    private Context context;

    /**
     * Construtor do AdapterCategoria.
     *
     * @param lista   A lista de categorias.
     * @param context O contexto da aplicação onde o RecyclerView está sendo utilizado.
     */
    public AdapterCategoria(List<Categoria> lista, Context context) {
        this.listaCategorias = lista;
        this.context = context;
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar o layout do item da lista
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    /**
     * Método chamado pelo RecyclerView para exibir os dados na posição especificada.
     * Este método atualiza o conteúdo do ViewHolder para refletir o item na posição dada.
     *
     * @param holder   O MyViewHolder que deve ser atualizado.
     * @param position A posição do item na lista de dados.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);

        // Esconde o item se a categoria não deve aparecer
        if (!categoria.getAparecer()) {
            holder.rootView.setVisibility(View.GONE);
            return;
        }

        // Define o clique para navegar para a lista de produtos da categoria
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaginaInicial) context).irParaProdutos(categoria.getNome());
            }
        });

        // Define a descrição da categoria
        holder.descricaoCategoria.setText(categoria.getDescricao());
        // Carrega a imagem da categoria usando a URL
        Picasso.get().load(categoria.getCaminho()).into(holder.imgCategoria);
    }

    /**
     * Retorna o número total de itens na lista de dados.
     *
     * @return O número total de itens.
     */
    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    /**
     * ViewHolder para os itens do RecyclerView.
     * Este ViewHolder contém as referências aos elementos de UI do item de lista.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Campos de texto para a descrição da categoria
        TextView descricaoCategoria;
        // Imagem da categoria
        ImageView imgCategoria;
        // Raiz do item da lista
        View rootView;

        /**
         * Construtor de MyViewHolder.
         * Inicializa os elementos de UI do item de lista.
         *
         * @param itemView A View do item de lista.
         */
        public MyViewHolder(View itemView) {
            super(itemView);
            // Encontra os elementos de UI no layout do item de lista
            rootView = itemView;
            descricaoCategoria = itemView.findViewById(R.id.txtDescricaoCategoria);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }
    }
}
