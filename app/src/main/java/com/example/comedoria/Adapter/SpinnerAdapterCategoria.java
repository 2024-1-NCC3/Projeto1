package com.example.comedoria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.comedoria.Class.Categoria;
import com.example.comedoria.R;

import java.util.List;

public class SpinnerAdapterCategoria extends ArrayAdapter<Categoria> {

    // Construtor do adaptador
    public SpinnerAdapterCategoria(Context context, List<Categoria> categorias){
        // Chama o construtor da superclasse com o contexto, o layout de item padrão (0) e a lista de categorias
        super(context, 0, categorias);
        // Define o layout do dropdown do Spinner
        setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
    }

    // Método chamado para exibir o item selecionado no Spinner
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Obtém a categoria na posição atual
        Categoria categoria = getItem(position);

        // Se a convertView for nula, infla o layout do item do Spinner
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_layout, parent, false);
        }

        // Obtém a referência ao TextView no layout do item do Spinner
        TextView textView = convertView.findViewById(android.R.id.text1);

        // Define o texto do TextView como o nome da categoria
        textView.setText(categoria.getNome());

        return convertView;
    }
}
