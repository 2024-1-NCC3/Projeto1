package com.example.comedoria;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;
    private android.content.Context context;

    public AdapterProduto(Context context, List<Produto> lista){
        this.listaProdutos = lista;
        this.context = context;
    }
    @NonNull

    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder1(itemLista);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);

        holder.descricaoProduto.setText(produto.getNome());

        Picasso.get().load(produto.getCaminhoImg()).into(holder.imgProduto);

        //Faz algo acontecer se clicar na imagem

//        holder.imgProduto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String categorias = "";
//                for(int i = 0; i < produto.getCategorias().size(); i++){
//                    categorias += produto.getCategorias().get(i);
//                };
//                Toast.makeText(context, categorias, Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.cbProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()) );

        holder.cbProduto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                produto.setSelecionado(!produto.isSelecionado());
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {

        return listaProdutos.size();
    }
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

    public class MyViewHolder1 extends  RecyclerView.ViewHolder{
        TextView descricaoProduto;
        ImageView imgProduto;
        CheckBox cbProduto;

        @SuppressLint("ResourceAsColor")
        public MyViewHolder1(View itemView){
            super(itemView);

            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);


        }


    }


}
