package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdapterAddProduto extends RecyclerView.Adapter<AdapterAddProduto.MyViewHolder> {

    private List<String> listar;
    private Context context;

    public AdapterAddProduto(Context context, List<String> listaProduto) {
        this.context = context;
        this.listar = listaProduto;
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
        String listarText = listar.get(position);
        holder.inputNomeProduto.setText("");
        holder.inputPreco.setText("");
        holder.inputIngrediente.setText("");
        holder.inputQnt.setText("");
    }

    @Override
    public int getItemCount() {
        return listar.size();
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
