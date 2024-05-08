package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.MyViewHolder> {
    private List<Categoria> listaCategorias;
    private Context context;

    public AdapterCategoria(List<Categoria> lista, Context context){
        this.listaCategorias = lista;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Categoria categoria = listaCategorias.get(position);


        if(!categoria.getAparecer()){
            holder.rootView.setVisibility(View.GONE);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaginaInicial)context).irParaProdutos(categoria.getNome());
            }
        });

        holder.descricaoCategoria.setText(categoria.getDescricao());
        //Cuida de carregar a imagem pela URL
        Picasso.get().load(categoria.getCaminho()).into(holder.imgCategoria);
    }

    @Override
    public int getItemCount() {

        return listaCategorias.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView descricaoCategoria;
        ImageView imgCategoria;
        View rootView;

        public MyViewHolder(View itemView){            
            super(itemView);
            rootView = itemView;
            descricaoCategoria = itemView.findViewById(R.id.txtDescricaoCategoria);
            imgCategoria = itemView.findViewById(R.id.imgCategoria);
        }
    }
}
