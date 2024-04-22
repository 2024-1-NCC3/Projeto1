package com.example.comedoria;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Carrinho extends AppCompatActivity {
    TextView txtHora, txtData;
    List<Produto> produtos;
    Calendar dataFinal;
    int dia, mes, ano, hora, minuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        String arrayProdutos = getIntent().getStringExtra("produtosSelecionados");
        produtos = Arrays.asList(new Gson().fromJson(arrayProdutos, Produto[].class));
        txtHora = findViewById(R.id.txtHora);
        txtData = findViewById(R.id.txtData);
//        numeroDeObjetos.setText(produtos.get(0).getNome());

    }

    public void selecionarHora(View view){
        Calendar calendar = Calendar.getInstance();
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(Calendar.HOUR_OF_DAY)
                .setMinute(Calendar.MINUTE)
                .setTitleText("Selecione a hora de retirada")
                .build();
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = picker.getHour();
                minuto = picker.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY, hora);
                calendar.set(Calendar.MINUTE, minuto);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                txtData.setText(simpleDateFormat.format(calendar.getTime()));
            }
        });

        picker.show(getSupportFragmentManager(),"tag");
    }

    public void selecionarData(View view){
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data de retirada")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                String date = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date(selection));
                txtData.setText(date);
            }
        });
        picker.show(getSupportFragmentManager(),"tag");
    }
}