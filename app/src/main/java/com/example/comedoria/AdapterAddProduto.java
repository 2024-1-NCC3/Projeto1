package com.example.comedoria;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AdapterAddProduto extends RecyclerView.Adapter<AdapterAddProduto.MyViewHolder> {

    private List<Produto> listaProduto;

    private Context contextAddProduto;



    @NonNull
    @Override
    public AdapterAddProduto.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText inputNomeProduto;
        TextInputEditText inputPreco;
        TextInputEditText inputIngrediente;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            inputNomeProduto = itemView.findViewById(R.id.textInputNomeProduto);
            inputIngrediente = itemView.findViewById(R.id.textInputPreco);
            inputPreco = itemView.findViewById(R.id.textInputQnt);
        }
    }
}
