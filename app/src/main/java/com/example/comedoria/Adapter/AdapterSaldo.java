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

    // Construtor do adaptador
    public AdapterSaldo(List<Cliente> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    // Método chamado para criar um novo ViewHolder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item do cliente
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cliente, parent, false);
        return new MyViewHolder(view);
    }

    // Método chamado para vincular os dados do cliente ao ViewHolder
    @Override
    public void onBindViewHolder(@NonNull AdapterSaldo.MyViewHolder holder, int position) {
        Cliente cliente = lista.get(position);

        // Define o nome e o ID do cliente nos TextViews correspondentes
        holder.txtNomeCliente.setText(cliente.getNomeCompleto());
        holder.txtIdCliente.setText("ID: " + cliente.getIdCliente().substring(0, 8));

        // Define o OnClickListener para o item da RecyclerView
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica se o contexto é uma instância de AdicionarSaldo
                if (context instanceof AdicionarSaldo) {
                    // Chama o método adicionarSaldoParaCliente na atividade AdicionarSaldo
                    ((AdicionarSaldo) context).adicionarSaldoParaCliente(cliente.getIdCliente(), cliente.getNomeCompleto());
                }
            }
        });
    }

    // Retorna a quantidade de itens na lista de clientes
    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Classe ViewHolder que representa cada item na RecyclerView
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeCliente;
        TextView txtIdCliente;
        View view;

        // Construtor que recebe a View do item do cliente
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtNomeCliente = itemView.findViewById(R.id.txtNomeCliente);
            txtIdCliente = itemView.findViewById(R.id.txtIdCliente);
        }
    }
}
