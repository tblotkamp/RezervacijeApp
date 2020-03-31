package com.example.andrzad;

public class Rezervacija {
    private int pin;
    private String imeRest;
    private String sati;
    private String datum;
    private int brOsoba;
    private String imeRez;

    public Rezervacija(String imeRest, String sati, String datum, int brOsoba, String imeRez) {
        setPin();
        this.imeRest = imeRest;
        this.sati = sati;
        this.datum = datum;
        this.brOsoba = brOsoba;
        this.imeRez = imeRez;
    }


    public int getPin() {
        return pin;
    }

    public void setPin() { this.pin = (int)(Math.random()*9000+1000);
    }

    public String getImeRest() {
        return imeRest;
    }

    public void setImeRest(String imeRest) {
        this.imeRest = imeRest;
    }

    public String getSati() {
        return sati;
    }

    public void setSati(String sati) {
        this.sati = sati;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getBrOsoba() {
        return brOsoba;
    }

    public void setBrOsoba(int brOsoba) {
        this.brOsoba = brOsoba;
    }

    public String getImeRez() {
        return imeRez;
    }

    public void setImeRez(String imeRez) {
        this.imeRez = imeRez;
    }

    @Override
    public boolean equals(Object prvi) {
        Rezervacija druga = (Rezervacija) prvi;
        if (druga.getPin() == this.getPin()) {
            return true;
        }
        return false;
    }
}

