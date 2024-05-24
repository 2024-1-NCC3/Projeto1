package com.example.comedoria.Adapter;

import android.content.Context;
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

    /**Carrega a lista dos pedidos para retirar*/
    public AdapterPedido(List<Pedido> lista, PedidosParaRetirar fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosParaRetirar = fragment;
    }

    /**Carrega a lista dos pedidos já retirados*/
    public AdapterPedido(List<Pedido> lista, PedidosRetirados fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosRetirados = fragment;
    }

    /**Cria as Views baseadas em um modelo*/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new MyViewHolder(itemLista);
    }

    /**Popula as Views geradas com funções*/
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = PedidosNaoCompletados.get(position);

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
        holder.txtProdutos.setText(pedido.produtosComoString());
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", pedido.getTotal()));
        holder.txtStatus.setText(pedido.getStatus());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pedidosRetirados == null ){
                    pedidosParaRetirar.irParaConfirmarPedido(pedido);
                }
                else if(pedidosParaRetirar == null){
                    // TODO: 19/05/2024 fazer a tela para mostrar detalhes de pedidos já confirmados
                }


            }
        });
    }

    /**Define o tamanho da lista carregada*/
    @Override
    public int getItemCount() {
        return PedidosNaoCompletados.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtData, txtProdutos, txtTotal, txtStatus;
        View view;
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
