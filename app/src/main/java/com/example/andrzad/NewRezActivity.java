package com.example.andrzad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

public class NewRezActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText pin;
    EditText imeRest;
    EditText brOsoba;
    EditText imeRez;
    TextView sati;
    TextView datum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rez);
        imeRest = findViewById(R.id.editText3);
        datum = findViewById(R.id.textView16);
        sati = findViewById(R.id.textView17);
        brOsoba = findViewById(R.id.editText6);
        imeRez = findViewById(R.id.editText7);
    }

    public void pocDatum(View view) {
        DialogFragment datum = new DatePickerFragment();
        datum.show(getSupportFragmentManager(), "date picker");

    }

    public void pocSati(View view) {
        DialogFragment sati = new TimePickerFragment();
        sati.show(getSupportFragmentManager(), "time picker");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar kalendar = Calendar.getInstance();
        kalendar.set(Calendar.YEAR, year);
        kalendar.set(Calendar.MONTH, month);
        kalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String sada = DateFormat.getDateInstance(DateFormat.FULL).format(kalendar.getTime());
        TextView tekst = findViewById(R.id.textView16);
        tekst.setText(sada);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tekst2 = findViewById(R.id.textView17);
        tekst2.setText(hourOfDay + ":" + minute);

    }

    public void odustani(View view) {
        finish();
    }

    public void pokupi(View view){
        Intent intent = getIntent();
        intent.putExtra("imeRest", imeRest.getText().toString());
        intent.putExtra("datum", datum.getText().toString());
        intent.putExtra("sati", sati.getText().toString());
        intent.putExtra("brOsoba", Integer.parseInt(brOsoba.getText().toString()));
        intent.putExtra("imeRez", imeRez.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}