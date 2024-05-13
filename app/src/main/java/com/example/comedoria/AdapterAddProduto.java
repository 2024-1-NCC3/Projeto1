package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AdapterAddProduto extends RecyclerView.Adapter<AdapterAddProduto.MyViewHolder> {

    private List<Produto> listaProduto;
    private Context context;

    public AdapterAddProduto(Context context, List<Produto> listaProduto) {
        this.context = context;
        this.listaProduto = listaProduto;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_add_produto, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = listaProduto.get(position);
        holder.inputNomeProduto.setText(produto.getNome());
        holder.inputPreco.setText(String.valueOf(produto.getPreco()));
        holder.inputIngrediente.setText(produto.getIngrediente());
        holder.inputQnt.setText(String.valueOf(produto.getQuantidade()));
    }

    @Override
    public int getItemCount() {
        return listaProduto.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText inputNomeProduto;
        public TextInputEditText inputPreco;
        public TextInputEditText inputIngrediente;
        public TextInputEditText inputQnt;

        public MyViewHolder(View view) {
            super(view);
            inputNomeProduto = view.findViewById(R.id.textInputNomeProduto);
            inputPreco = view.findViewById(R.id.textInputPreco);
            inputIngrediente = view.findViewById(R.id.textInputDingredientes);
            inputQnt = view.findViewById(R.id.inputIngredientes);
        }
    }
}
