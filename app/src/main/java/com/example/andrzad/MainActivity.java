package com.example.andrzad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText pin;
    EditText imeRest;
    EditText brOsoba;
    EditText imeRez;
    TextView sati;
    TextView datum;

    ArrayList<Rezervacija> podaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pin = findViewById(R.id.editText);
        imeRest = findViewById(R.id.editText2);
        sati = findViewById(R.id.textView9);
        datum = findViewById(R.id.textView10);
        brOsoba = findViewById(R.id.editText4);
        imeRez = findViewById(R.id.editText5);
    }

    public void setDatum(View view){
        DialogFragment datum = new DatePickerFragment();
        datum.show(getSupportFragmentManager(), "date picker");
    }
    public void setSati(View view){
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
        TextView tekst = findViewById(R.id.textView9);
        tekst.setText(sada);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView tekst2 = findViewById(R.id.textView10);
        tekst2.setText(hourOfDay + ":" + minute);
    }

    public void dodajRez(View view){
        Intent intent = new Intent(this, NewRezActivity.class);
        startActivityForResult(intent, 1);
    }

    public void spremiRez(){
        SharedPreferences rezervacije = getSharedPreferences("rezervacije", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = rezervacije.edit();
        Gson jason = new Gson();
        String jsonString = jason.toJson(podaci);
        e.putString("podaci", jsonString);
        e.apply();
    }

    public void ucitajRez(){
        SharedPreferences rezervacije = getSharedPreferences("rezervacije", Context.MODE_PRIVATE);
        Gson jason = new Gson();
        String json = rezervacije.getString("podaci", null);
        Type t = new TypeToken<ArrayList<Rezervacija>>() {}.getType();
        podaci = jason.fromJson(json, t);

        if (podaci == null){
            podaci = new ArrayList<Rezervacija>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /* Prezentacija Aktivnosti na Merlinu */
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ucitajRez();
                Rezervacija reza = new Rezervacija(
                        data.getStringExtra("imeRest"),
                        data.getStringExtra("datum"),
                        data.getStringExtra("sati"),
                        data.getIntExtra("brOsoba", 0),
                        data.getStringExtra("imeRez")
                );
                reza.setPin();

                while (podaci.contains(reza)) {
                    reza.setPin();
                }

                podaci.add(reza);
                spremiRez();

                pin.setText(reza.getPin() + "");
                imeRest.setText(reza.getImeRest() + "");
                datum.setText(reza.getDatum() + "");
                sati.setText(reza.getSati() + "");
                brOsoba.setText(reza.getBrOsoba() + "");
                imeRez.setText(reza.getImeRez() + "");

            }
        }
    }
    public void urediRez(View view){
        int editPIN;
        try {
            editPIN = Integer.parseInt(pin.getText()+"");
        } catch (NumberFormatException ex){
            Toast.makeText(getApplicationContext(),
                    "Molimo upišite ispravan PIN",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ucitajRez();
        for (Rezervacija podatak : podaci){
            if (podatak.getPin() == editPIN){
                podatak.setImeRest(imeRest.getText().toString());
                podatak.setDatum(datum.getText().toString());
                podatak.setSati(sati.getText().toString());
                podatak.setBrOsoba(Integer.parseInt(brOsoba.getText().toString()));
                podatak.setImeRez(imeRez.getText().toString());
                spremiRez();
                Toast.makeText(getApplicationContext(),
                        "Rezervacija pod brojem "+podatak.getPin() + "uspješno izmijenjena",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }
            Toast.makeText(getApplicationContext(),
                    "Nije nađena rezervacija "+editPIN,
                    Toast.LENGTH_SHORT)
                    .show();
    }

    public void izbrisiRez(View view){
        int editPIN;
        try {
            editPIN = Integer.parseInt(pin.getText()+"");
        } catch (NumberFormatException ex){
            Toast.makeText(getApplicationContext(),
                    "Molimo upišite ispravan PIN",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        ucitajRez();
        for (Rezervacija podatak : podaci){
            if (podatak.getPin() == editPIN){
                podaci.remove(podatak);
                pin.setText("");
                imeRest.setText("");
                datum.setText("");
                sati.setText("");
                brOsoba.setText("");
                imeRez.setText("");
                spremiRez();
                Toast.makeText(getApplicationContext(),
                        "Rezervacija pod brojem "+ editPIN + "uspješno izbrisana",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }
        Toast.makeText(getApplicationContext(),
                "Nije nađena rezervacija "+editPIN,
                Toast.LENGTH_SHORT)
                .show();
    }


        public void nadiRez(View view){
            int searchPIN;
            try {
                searchPIN = Integer.parseInt(pin.getText()+"");
            } catch (NumberFormatException ex){
                Toast.makeText(getApplicationContext(),
                        "Molimo upišite ispravan PIN",
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            ucitajRez();

            for (Rezervacija podatak : podaci){
                if (podatak.getPin()==searchPIN){
                    pin.setText(podatak.getPin() + "");
                    imeRest.setText(podatak.getImeRest() + "");
                    datum.setText(podatak.getDatum() + "");
                    sati.setText(podatak.getSati() + "");
                    brOsoba.setText(podatak.getBrOsoba() + "");
                    imeRez.setText(podatak.getImeRez() + "");
                    Toast.makeText(getApplicationContext(), "Rezervacija uspješno učitana", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            pin.setText("");
            imeRest.setText("");
            datum.setText("");
            sati.setText("");
            brOsoba.setText("");
            imeRez.setText("");

            Toast.makeText(getApplicationContext(), "Traženi PIN nije pronađen", Toast.LENGTH_SHORT).show();
        }



    }

