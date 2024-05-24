package com.example.comedoria.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.Class.Pedido;
import com.example.comedoria.activities.Perfil;
import com.example.comedoria.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {

    private List<Pedido> historico;
    private Context contextPerfil;

    // Construtor que recebe a lista de pedidos e o contexto da atividade Perfil
    public AdapterHistorico(List<Pedido> lista, Context contextPerfil){
        this.historico = lista;
        this.contextPerfil = contextPerfil;
    }

    // Método chamado quando o ViewHolder precisa ser criado
    @NonNull
    @Override
    public AdapterHistorico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do histórico
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new AdapterHistorico.MyViewHolder(itemLista);
    }

    // Método chamado para vincular os dados do pedido ao ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AdapterHistorico.MyViewHolder holder, int position) {
        Pedido pedido = historico.get(position);

        // Formato da data do pedido
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat meuFormato = new SimpleDateFormat("dd/MM/yy");

        try {
            // Converte a data para o formato desejado
            Date dataFormatada = formato.parse(pedido.getData());
            holder.txtData.setText(meuFormato.format(dataFormatada));
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

        // Define a cor do texto do status com base no estado do pedido
        if(pedido.getStatus().equals("Retirado")){
            holder.txtStatus.setTextColor(Color.parseColor("#ff669900"));
        } else if (pedido.getStatus().equals("Aguardando Pagamento")) {
            holder.txtStatus.setTextColor(Color.parseColor("#ffffbb33"));
        }

        // Determina a largura fixa do status e do total para uma exibição consistente
        TextPaint paintStatus = holder.txtStatus.getPaint();
        float charWidthStatus = paintStatus.measureText("Aguardando ");
        holder.txtStatus.setWidth((int) (charWidthStatus));

        TextPaint paintTotal = holder.txtTotal.getPaint();
        float charWidthTotal = paintTotal.measureText("R$ 000,00");
        holder.txtTotal.setWidth((int) (charWidthTotal));

        // Define o comportamento ao clicar em um item do histórico para visualizar o pedido
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chama o método na atividade Perfil para visualizar o pedido
                ((Perfil)contextPerfil).irParaPedido(pedido.getId_pedido());
            }
        });
    }

    // Retorna a quantidade de itens no histórico de pedidos
    @Override
    public int getItemCount() {
        return historico.size();
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
