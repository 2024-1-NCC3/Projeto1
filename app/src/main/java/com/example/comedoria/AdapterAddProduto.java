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
 * Adapter para gerenciar a exibição de uma lista de produtos em um RecyclerView.
 */
public class AdapterAddProduto extends RecyclerView.Adapter<AdapterAddProduto.MyViewHolder> {

    // Lista de produtos a serem exibidos no RecyclerView
    private List<String> listar;
    // Contexto da aplicação onde o RecyclerView está sendo utilizado
    private Context context;

    /**
     * Construtor do AdapterAddProduto.
     *
     * @param context     O contexto da aplicação onde o RecyclerView está sendo utilizado.
     * @param listaProduto A lista de produtos a serem exibidos.
     */
    public AdapterAddProduto(Context context, List<String> listaProduto) {
        this.context = context;
        this.listar = listaProduto;
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_add_produto, parent, false);
        return new MyViewHolder(itemView);
    }

    /**
     * Método chamado pelo RecyclerView para exibir os dados na posição especificada.
     * Este método atualiza o conteúdo do ViewHolder para refletir o item na posição dada.
     *
     * @param holder   O ViewHolder que deve ser atualizado.
     * @param position A posição do item na lista de dados.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Pega o texto do produto na posição especificada (aqui não estamos usando, mas pode ser expandido para uso real)
        String listarText = listar.get(position);

        // Configura os campos de texto do ViewHolder para exibir os dados do produto
        holder.inputNomeProduto.setText("");
        holder.inputPreco.setText("");
        holder.inputIngrediente.setText("");
        holder.inputQnt.setText("");
    }

    /**
     * Retorna o número total de itens na lista de dados.
     *
     * @return O número total de itens.
     */
    @Override
    public int getItemCount() {
        return listar.size();
    }

    /**
     * ViewHolder para os itens do RecyclerView.
     * Este ViewHolder contém as referências aos elementos de UI do item de lista.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Campos de texto para o nome do produto, preço, ingredientes e quantidade
        public TextInputEditText inputNomeProduto;
        public TextInputEditText inputPreco;
        public TextInputEditText inputIngrediente;
        public TextInputEditText inputQnt;

        /**
         * Construtor de MyViewHolder.
         * Inicializa os elementos de UI do item de lista.
         *
         * @param view A View do item de lista.
         */
        public MyViewHolder(View view) {
            super(view);
            // Encontra os elementos de UI no layout do item de lista
            inputNomeProduto = view.findViewById(R.id.textInputNomeProduto);
            inputPreco = view.findViewById(R.id.textInputPreco);
            inputIngrediente = view.findViewById(R.id.textInputDingredientes);
            inputQnt = view.findViewById(R.id.inputIngredientes);
        }
    }
}
