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
    private List<Categoria> listaCategorias;
    private Context context;

    // Construtor do adaptador
    public AdapterCategoria(List<Categoria> lista, Context context){
        this.listaCategorias = lista;
        this.context = context;
    }

    // Método chamado quando um novo item de categoria é criado
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    // Método chamado para exibir os dados de uma categoria
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);

        // Verifica se a categoria deve ser exibida ou não
        if(!categoria.getAparecer()){
            holder.rootView.setVisibility(View.GONE);
        }

        // Define o comportamento de clique na categoria
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaginaInicial)context).irParaProdutos(categoria.getNome());
            }
        });

        // Define a descrição da categoria
        holder.descricaoCategoria.setText(categoria.getDescricao());

        // Carrega a imagem da categoria utilizando Picasso
        Picasso.get().load(categoria.getCaminho()).into(holder.imgCategoria);
    }

    // Retorna o número total de categorias na lista
    @Override
    public int getItemCount() {
        return listaCategorias.size();
    }

    // Classe ViewHolder que contém as visualizações dos itens de categoria
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView descricaoCategoria;
        ImageView imgCategoria;
        View rootView;

        // Construtor que inicializa as visualizações
        public MyViewHolder(View itemView){
            super(itemView);
            rootView = itemView;
            descricaoCategoria = itemView.findViewById(R.id.txtDescricaoCategoria);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }
    }
}
