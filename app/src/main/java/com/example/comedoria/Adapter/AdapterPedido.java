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

    // Construtor usado quando a RecyclerView está dentro do fragmento PedidosParaRetirar
    public AdapterPedido(List<Pedido> lista, PedidosParaRetirar fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosParaRetirar = fragment;
    }

    // Construtor usado quando a RecyclerView está dentro do fragmento PedidosRetirados
    public AdapterPedido(List<Pedido> lista, PedidosRetirados fragment){
        this.PedidosNaoCompletados = lista;
        this.pedidosRetirados = fragment;
    }

    // Método chamado quando o ViewHolder precisa ser criado
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do pedido
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new MyViewHolder(itemLista);
    }

    // Método chamado para vincular os dados do pedido ao ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pedido pedido = PedidosNaoCompletados.get(position);

        // Formato da data do pedido
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");

        String horaFormatada = "";

        if(pedido.getHora() != null){
            horaFormatada = pedido.getHora().substring(0,5);
        }

        try {
            // Converte a data para o formato desejado
            Date dataFormatada = formato.parse(pedido.getData());

            // Define o texto da data e hora do pedido
            holder.txtData.setText(meuFormato.format(dataFormatada) + "\n" + horaFormatada);
        } catch (ParseException e) {
            // Trata exceções de análise de data
            throw new RuntimeException(e);
        }

        // Define os produtos do pedido
        holder.txtProdutos.setText(pedido.produtosComoString());
        // Define o total do pedido
        holder.txtTotal.setText(String.format(Locale.getDefault(), "R$ %.2f", pedido.getTotal()));
        // Define o status do pedido
        holder.txtStatus.setText(pedido.getStatus());

        // Define o comportamento ao clicar em um item do pedido
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica em qual fragmento o adaptador está sendo usado e executa a ação correspondente
                if(pedidosRetirados == null ){
                    // Se o fragmento PedidosRetirados for nulo, navega para a confirmação do pedido
                    pedidosParaRetirar.irParaConfirmarPedido(pedido);
                } else if(pedidosParaRetirar == null){
                    // Se o fragmento PedidosParaRetirar for nulo, faça alguma outra ação, como mostrar detalhes de pedidos já confirmados
                    // TODO: 19/05/2024 fazer a tela para mostrar detalhes de pedidos já confirmados
                }
            }
        });
    }

    // Retorna a quantidade de itens na lista de pedidos
    @Override
    public int getItemCount() {
        return PedidosNaoCompletados.size();
    }

    // Classe ViewHolder que representa cada item na RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtData, txtProdutos, txtTotal, txtStatus;
        View view;

        // Construtor que recebe a View do item
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa os componentes do layout do item
            view = itemView;
            txtData = itemView.findViewById(R.id.txtDataHistorico);
            txtProdutos = itemView.findViewById(R.id.txtProdutosHistorico);
            txtTotal = itemView.findViewById(R.id.txtTotalHistorico);
            txtStatus = itemView.findViewById(R.id.txtStatusHistorico);
        }
    }
}
