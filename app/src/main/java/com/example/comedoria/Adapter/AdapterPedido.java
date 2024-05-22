package com.example.comedoria.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Pedido;
import com.example.comedoria.R;
import com.example.comedoria.fragments.PedidosParaRetirar;
import com.example.comedoria.fragments.PedidosRetirados;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.MyViewHolder> {
    private List<Pedido> PedidosNaoCompletados;
    private PedidosParaRetirar pedidosParaRetirar;
    private PedidosRetirados pedidosRetirados;

    // Construtor do AdapterPedido para pedidos não completados (PedidosParaRetirar)
    public AdapterPedido(List<Pedido> lista, PedidosParaRetirar fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosParaRetirar = fragment;
    }

    // Construtor do AdapterPedido para pedidos completados (PedidosRetirados)
    public AdapterPedido(List<Pedido> lista, PedidosRetirados fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosRetirados = fragment;
    }

    // Método chamado para criar novas visualizações de itens do pedido
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do pedido
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new MyViewHolder(itemLista);
    }

    // Método chamado para preencher os dados de um item do pedido na visualização
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = PedidosNaoCompletados.get(position);

        // Formata a data do pedido para exibição
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");
        String horaFormatada = "";
        if(pedido.getHora() != null){
            horaFormatada = pedido.getHora().substring(0,5);
        }
        try {
            Date dataFormatada = formato.parse(pedido.getData());
            holder.txtData.setText(meuFormato.format(dataFormatada) + "\n" + horaFormatada);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Define os produtos do pedido
        holder.txtProdutos.setText(pedido.produtosComoString());

        // Define o total do pedido
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", pedido.getTotal()));

        // Define o status do pedido
        holder.txtStatus.setText(pedido.getStatus());

        // Adiciona um ouvinte de clique ao item do pedido para visualizar detalhes do pedido
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica se o contexto é PedidosParaRetirar ou PedidosRetirados
                if(pedidosRetirados == null ){
                    pedidosParaRetirar.irParaConfirmarPedido(pedido);
                } else if(pedidosParaRetirar == null){
                    // TODO: Implementar a tela para mostrar detalhes de pedidos já confirmados
                }
            }
        });
    }

    // Método chamado para obter o número total de itens no pedido
    @Override
    public int getItemCount() {
        return PedidosNaoCompletados.size();
    }

    // Classe ViewHolder para representar os itens do pedido na visualização
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtData, txtProdutos, txtTotal, txtStatus;
        View view;

        // Construtor ViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa os componentes da visualização
            view = itemView;
            txtData = itemView.findViewById(R.id.txtDataHistorico);
            txtProdutos = itemView.findViewById(R.id.txtProdutosHistorico);
            txtTotal = itemView.findViewById(R.id.txtTotalHistorico);
            txtStatus = itemView.findViewById(R.id.txtStatusHistorico);
        }
    }
}
