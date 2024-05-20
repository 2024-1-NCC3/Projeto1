package com.example.comedoria.Adapter;

import android.content.Context;
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

    public AdapterHistorico(List<Pedido> lista, Context contextPerfil){
        this.historico = lista;
        this.contextPerfil = contextPerfil;
    }

    @NonNull
    @Override
    public AdapterHistorico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new AdapterHistorico.MyViewHolder(itemLista);
    }

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
                ((Perfil)contextPerfil).irParaPedido(pedido.getId_pedido());

            }
        });
    }

    @Override
    public int getItemCount() {
        return historico.size();
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
