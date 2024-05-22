package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter para exibir o histórico de pedidos em um RecyclerView.
 */
public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {

    private List<Pedido> historico;
    private Context contextPerfil;

    /**
     * Construtor do AdapterHistorico.
     *
     * @param lista         A lista de pedidos históricos.
     * @param contextPerfil O contexto do perfil onde o adapter será usado.
     */
    public AdapterHistorico(List<Pedido> lista, Context contextPerfil) {
        this.historico = lista;
        this.contextPerfil = contextPerfil;
    }

    /**
     * Cria novas instâncias de MyViewHolder, associando-as ao layout de item_historico.
     *
     * @param parent   O ViewGroup pai onde a nova View será adicionada após ser anexada a uma ViewHolder.
     * @param viewType O tipo de view do novo View.
     * @return Uma nova instância de MyViewHolder.
     */
    @NonNull
    @Override
    public AdapterHistorico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new AdapterHistorico.MyViewHolder(itemLista);
    }

    /**
     * Atualiza o conteúdo de um ViewHolder para refletir o item na posição dada.
     *
     * @param holder   O ViewHolder que deve ser atualizado para representar o conteúdo do item na posição dada no conjunto de dados.
     * @param position A posição do item dentro dos dados.
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterHistorico.MyViewHolder holder, int position) {
        Pedido pedido = historico.get(position);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");

        try {
            Date dataFormatada = formato.parse(pedido.getData());
            holder.txtData.setText(meuFormato.format(dataFormatada));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        holder.txtProdutos.setText(pedido.produtosComoString());
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", pedido.getTotal()));
        holder.txtStatus.setText(pedido.getStatus());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Perfil) contextPerfil).irParaPedido(pedido.getId_pedido());
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
        return historico.size();
    }

    /**
     * Classe ViewHolder que representa os itens de dados e as views sobrepostas a eles.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtData, txtProdutos, txtTotal, txtStatus;
        View view;

        /**
         * Construtor de MyViewHolder.
         *
         * @param itemView A view inflada a ser usada como item de um RecyclerView.
         */
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            txtData = itemView.findViewById(R.id.txtDataHistorico);
            txtProdutos = itemView.findViewById(R.id.txtProdutosHistorico);
            txtTotal = itemView.findViewById(R.id.txtTotalHistorico);
            txtStatus = itemView.findViewById(R.id.txtStatusHistorico);
        }
    }
}
