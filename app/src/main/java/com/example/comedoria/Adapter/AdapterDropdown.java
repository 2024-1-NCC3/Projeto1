package com.example.comedoria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

<<<<<<< HEAD:app/src/main/java/com/example/comedoria/AdapterDropdown.java
=======
import com.example.comedoria.R;

import java.util.ArrayList;
>>>>>>> DEV:app/src/main/java/com/example/comedoria/Adapter/AdapterDropdown.java
import java.util.List;

/**
 * Adapter para popular os dropdowns dos filtros de produto.
 */
public class AdapterDropdown extends ArrayAdapter<String> {

    /**
     * Construtor do AdapterDropdown.
     *
     * @param context O contexto da aplicação onde o Spinner está sendo utilizado.
     * @param dados   A lista de dados a serem exibidos no Spinner.
     */
    public AdapterDropdown(@NonNull Context context, List<String> dados) {
        super(context, R.layout.color_spinner_layout, dados);
        // Define o layout para os itens do dropdown
        setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
    }

    /**
     * Retorna a visualização do item do Spinner quando está fechado.
     *
     * @param position    A posição do item na lista de dados.
     * @param convertView A View reutilizável.
     * @param parent      O ViewGroup pai.
     * @return A View do item.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    /**
     * Retorna a visualização do item do Spinner quando está aberto.
     *
     * @param position    A posição do item na lista de dados.
     * @param convertView A View reutilizável.
     * @param parent      O ViewGroup pai.
     * @return A View do item.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    /**
     * Cria a visualização do item do Spinner.
     *
     * @param position    A posição do item na lista de dados.
     * @param convertView A View reutilizável.
     * @param parent      O ViewGroup pai.
     * @return A View do item do Spinner.
     */
    private View createItemView(int position, View convertView, ViewGroup parent) {
        // Inflar o layout do item do Spinner
        View view = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_layout, parent, false);

        // Personalize a visualização do item do Spinner aqui, se necessário
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(getItem(position));

        return view;
    }
}
