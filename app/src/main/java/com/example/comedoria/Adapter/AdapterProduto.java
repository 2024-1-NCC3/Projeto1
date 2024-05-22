package com.example.comedoria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Produto;
import com.example.comedoria.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Um adaptador para exibir produtos em um RecyclerView.
 */
public class AdapterProduto extends RecyclerView.Adapter<AdapterProduto.MyViewHolder1> {
    private List<Produto> listaProdutos;
    private android.content.Context context;

    /**
     * Define uma nova lista filtrada de produtos e notifica o RecyclerView para atualizar.
     *
     * @param listaFiltrada A lista filtrada de produtos.
     */
    public void setFilteredList(List<Produto> listaFiltrada){
        listaProdutos = listaFiltrada;
        notifyDataSetChanged();
    };

    /**
     * Construtor do AdapterProduto.
     *
     * @param context O contexto onde o adapter será usado.
     * @param lista   A lista de produtos a ser exibida.
     */
    public AdapterProduto(Context context, List<Produto> lista){
        this.listaProdutos = lista;
        this.context = context;
    }

    /**
     * Cria novas instâncias de MyViewHolder1, associando-as ao layout card_produto.
     *
     * @param parent   O ViewGroup pai onde a nova View será adicionada após ser anexada a uma ViewHolder.
     * @param viewType O tipo de view do novo View.
     * @return Uma nova instância de MyViewHolder1.
     */
    @NonNull
    @Override
    public MyViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_produto, parent, false);
        return new MyViewHolder1(itemLista);
    }

    /**
     * Atualiza o conteúdo de um ViewHolder para refletir o item na posição dada.
     *
     * @param holder   O ViewHolder que deve ser atualizado para representar o conteúdo do item na posição dada no conjunto de dados.
     * @param position A posição do item dentro dos dados.
     */
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder1 holder, int position) {
        Produto produto = listaProdutos.get(position);

        if(!produto.isAparecer()){
            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new ViewGroup.LayoutParams(0,0));
        }else{
            holder.rootView.setVisibility(View.VISIBLE);
        }

        holder.descricaoProduto.setText(produto.getNome());
        Picasso.get().load(produto.getCaminhoImg()).into(holder.imgProduto);

        holder.cbProduto.setText(String.format(Locale.getDefault(), "R$ %.2f", produto.getPreco()));

        holder.cbProduto.setOnCheckedChangeListener(null);

        holder.cbProduto.setChecked(produto.isSelecionado());
        holder.cbProduto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                produto.setSelecionado(!produto.isSelecionado());
                Log.i("Checkboxes", produto.getNome());

            }
        });
    }

    /**
     * Retorna o número total de itens no conjunto de dados mantido pelo adaptador.
     *
     * @return O número total de itens.
     */
    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    /**
     * Retorna a lista de produtos.
     *
     * @return A lista de produtos.
     */
    public List<Produto> getListaProdutos(){
        return this.listaProdutos;
    }

<<<<<<< HEAD:app/src/main/java/com/example/comedoria/AdapterProduto.java
    /**
     * Classe ViewHolder que representa os itens de dados e as views sobrepostas a eles.
     */
    public class MyViewHolder1 extends  RecyclerView.ViewHolder{
=======
    public class MyViewHolder1 extends RecyclerView.ViewHolder{
>>>>>>> DEV:app/src/main/java/com/example/comedoria/Adapter/AdapterProduto.java
        TextView descricaoProduto;
        ImageView imgProduto;
        CheckBox cbProduto;
        View rootView;

        /**
         * Construtor de MyViewHolder1.
         *
         * @param itemView A view inflada a ser usada como item de um RecyclerView.
         */
        @SuppressLint("ResourceAsColor")
        public MyViewHolder1(View itemView){
            super(itemView);
            rootView = itemView;
            cbProduto = itemView.findViewById(R.id.cbProduto);
            descricaoProduto = itemView.findViewById(R.id.descricaoProduto);
            imgProduto = itemView.findViewById(R.id.imageProduto);
        }
    }
}
