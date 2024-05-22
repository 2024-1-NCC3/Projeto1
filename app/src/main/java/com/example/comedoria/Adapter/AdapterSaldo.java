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

    // Construtor do AdapterSaldo
    public AdapterSaldo(List<Cliente> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    // Método chamado para criar novas visualizações de itens do saldo
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla o layout do item de cliente
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_cliente, parent, false);
        return new MyViewHolder(view);
    }

    // Método chamado para preencher os dados de um item de saldo na visualização
    @Override
    public void onBindViewHolder(@NonNull AdapterSaldo.MyViewHolder holder, int position) {
        Cliente cliente = lista.get(position);

        // Define o nome completo do cliente
        holder.txtNomeCliente.setText(cliente.getNomeCompleto());
        // Define o ID do cliente
        holder.txtIdCliente.setText("ID: " + cliente.getIdCliente().substring(0, 8));

        // Define o evento de clique para adicionar saldo ao cliente
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Verifica se o contexto é uma instância de AdicionarSaldo
                if (context instanceof AdicionarSaldo) {
                    // Chama o método para adicionar saldo para o cliente clicado
                    ((AdicionarSaldo) context).adicionarSaldoParaCliente(cliente.getIdCliente(), cliente.getNomeCompleto());
                }
            }
        });
    }

    // Método chamado para obter o número total de itens de saldo na lista
    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Classe ViewHolder para representar os itens de saldo na visualização
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtNomeCliente;
        TextView txtIdCliente;
        View view;

        // Construtor ViewHolder
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa os componentes da visualização
            view = itemView;
            txtNomeCliente = itemView.findViewById(R.id.txtNomeCliente);
            txtIdCliente = itemView.findViewById(R.id.txtIdCliente);
        }
    }
}
