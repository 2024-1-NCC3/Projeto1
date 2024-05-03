package com.example.comedoria;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


    //Adapter para popular os dropdowns dos filtros de produto
public class AdapterDropdown extends ArrayAdapter<String> {

    public AdapterDropdown(@NonNull Context context, List<String> dados) {
        super(context, R.layout.color_spinner_layout, dados);
        setDropDownViewResource(R.layout.color_spinner_dropdown_layout);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = super.getView(position, convertView, parent);
        return view;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        return view;
    }

}
