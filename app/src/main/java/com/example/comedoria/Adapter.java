package com.example.comedoria;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Categoria> listaCategorias;


    public Adapter(List<Categoria> lista){
        this.listaCategorias = lista;
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

        holder.descricaoCategoria.setText(categoria.getDescricao());
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
