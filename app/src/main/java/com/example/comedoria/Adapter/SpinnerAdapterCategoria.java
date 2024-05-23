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

    public SpinnerAdapterCategoria(Context context, List<Categoria> categorias){
        super(context,0,categorias);
        setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Categoria categoria = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_layout, parent, false);
        }
        TextView textView = convertView.findViewById(android.R.id.text1);

        textView.setText(categoria.getNome());

        return convertView;
    }
}
