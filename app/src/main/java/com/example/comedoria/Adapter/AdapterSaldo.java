package com.example.comedoria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comedoria.activities.AdicionarSaldo;
import com.example.comedoria.Class.Cliente;
import com.example.comedoria.R;

import java.util.List;

public class AdapterSaldo extends RecyclerView.Adapter<AdapterSaldo.MyViewHolder> {
    private List<Cliente> lista;
    private Context context;

    /**Carrega a lista e o contexto da tela*/
    public AdapterSaldo(List<Cliente> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    /**Cria as Views baseadas em um modelo*/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cliente, parent, false);
        return new MyViewHolder(view);
    }

    /**Popula as Views geradas com funções*/
    @Override
    public void onBindViewHolder(@NonNull AdapterSaldo.MyViewHolder holder, int position) {
        Cliente cliente = lista.get(position);

        holder.txtNomeCliente.setText(cliente.getNomeCompleto());
        holder.txtIdCliente.setText("ID: " + cliente.getIdCliente().substring(0,8));

        /**Redireciona para a tela de adicionar saldo para o cliente carregando o ID do cliente*/
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof AdicionarSaldo){
                    ((AdicionarSaldo)context).adicionarSaldoParaCliente(cliente.getIdCliente(),cliente.getNomeCompleto());
                }
            }
        });
    }

    /**Define o tamanho da lista carregada*/
    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeCliente;
        TextView txtIdCliente;

        View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtNomeCliente = itemView.findViewById(R.id.txtNomeCliente);
            txtIdCliente = itemView.findViewById(R.id.txtIdCliente);
        }
    }
}
