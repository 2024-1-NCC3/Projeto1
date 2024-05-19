package com.example.comedoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


    //Adapter para popular os dropdowns dos filtros de produto
    public class AdapterDropdown extends ArrayAdapter<String> {

        public AdapterDropdown(@NonNull Context context, List<String> dados) {
            super(context, R.layout.color_spinner_layout, dados);
            setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Retorna a visualização do item do Spinner quando está fechado
            return createItemView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            // Retorna a visualização do item do Spinner quando está aberto
            return createItemView(position, convertView, parent);
        }

        private View createItemView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_layout, parent, false);

            // Personalize a visualização do item do Spinner aqui, se necessário

            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }
    }

