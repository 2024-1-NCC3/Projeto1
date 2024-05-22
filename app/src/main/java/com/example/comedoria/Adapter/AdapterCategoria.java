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

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {
    // Lista de categorias a serem exibidas
    private List<Categoria> listaCategorias;
    // Contexto da atividade ou fragmento que usa este adaptador
    private Context context;

    // Construtor do adaptador
    public AdapterCategoria(List<Categoria> lista, Context context){
        this.listaCategorias = lista;
        this.context = context;
    }

    // Método chamado quando o RecyclerView precisa de uma nova ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item e cria uma nova instância de MyViewHolder
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    // Método chamado quando RecyclerView precisa de uma ViewHolder vinculada aos dados
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Obtém a categoria na posição atual da lista
        Categoria categoria = listaCategorias.get(position);

        // Verifica se a categoria deve aparecer ou não
        if(!categoria.getAparecer()){
            holder.rootView.setVisibility(View.GONE);
        }

        // Define o clique na raiz do item para abrir a lista de produtos
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaginaInicial)context).irParaProdutos(categoria.getNome());
            }
        });

        // Define a descrição da categoria e carrega a imagem usando Picasso
        holder.descricaoCategoria.setText(categoria.getDescricao());
        Picasso.get().load(categoria.getCaminho()).into(holder.imgCategoria);
    }

    // Retorna o número total de itens na lista
    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    // Classe interna que representa a ViewHolder para cada item na lista
    public class MyViewHolder extends RecyclerView.ViewHolder{
        // Declaração das views dentro do ViewHolder
        TextView descricaoCategoria;
        ImageView imgCategoria;
        View rootView;

        // Construtor da ViewHolder
        public MyViewHolder(View itemView){
            super(itemView);
            rootView = itemView;
            descricaoCategoria = itemView.findViewById(R.id.txtDescricaoCategoria);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }
    }
}
