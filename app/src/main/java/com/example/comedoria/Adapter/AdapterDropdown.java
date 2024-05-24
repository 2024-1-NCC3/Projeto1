package com.example.comedoria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.comedoria.R;

import java.util.List;

// Adapter para popular os dropdowns dos filtros de produto
public class AdapterDropdown extends ArrayAdapter<String> {

    // Construtor que recebe o contexto e a lista de dados para o adapter
    public AdapterDropdown(@NonNull Context context, List<String> dados) {
        super(context, R.layout.color_spinner_layout, dados);
        // Define o layout do item do dropdown quando está aberto
        setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
    }

    // Retorna a visualização do item do Spinner quando está fechado
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    // Retorna a visualização do item do Spinner quando está aberto
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    // Método para criar a visualização do item do Spinner
    private View createItemView(int position, View convertView, ViewGroup parent) {
        // Infla o layout do item do Spinner
        View view = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_layout, parent, false);

        // Personalize a visualização do item do Spinner aqui, se necessário

        // Obtém o TextView do item do Spinner e define o texto com base nos dados da lista
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }
}
