package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

/**
 * Um adaptador personalizado para exibir itens de produtos em um RecyclerView.
 */
public class AdapterAddProduto extends RecyclerView.Adapter<AdapterAddProduto.MyViewHolder> {

    private List<String> listaProdutos; // Lista de produtos a serem exibidos
    private Context context; // Contexto da aplicação

    /**
     * Construtor do adaptador.
     *
     * @param context       Contexto da aplicação
     * @param listaProdutos Lista de produtos a serem exibidos
     */
    public AdapterAddProduto(Context context, List<String> listaProdutos) {
        this.context = context;
        this.listaProdutos = listaProdutos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item de produto
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_add_produto, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Obtém o produto na posição atual
        String produto = listaProdutos.get(position);
        // Define o texto vazio para os campos de entrada
        holder.inputNomeProduto.setText("");
        holder.inputPreco.setText("");
        holder.inputIngrediente.setText("");
        holder.inputQnt.setText("");
    }

    @Override
    public int getItemCount() {
        // Retorna o tamanho da lista de produtos
        return listaProdutos.size();
    }

    /**
     * Classe interna que representa a visualização de um item de produto no RecyclerView.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextInputEditText inputNomeProduto;
        public TextInputEditText inputPreco;
        public TextInputEditText inputIngrediente;
        public TextInputEditText inputQnt;

        /**
         * Construtor da classe MyViewHolder.
         *
         * @param view A visualização de um item de produto
         */
        public MyViewHolder(View view) {
            super(view);
            // Inicializa os componentes de entrada de texto
            inputNomeProduto = view.findViewById(R.id.textInputNomeProduto);
            inputPreco = view.findViewById(R.id.textInputPreco);
            inputIngrediente = view.findViewById(R.id.textInputDingredientes);
            inputQnt = view.findViewById(R.id.inputIngredientes);
        }
    }
}
