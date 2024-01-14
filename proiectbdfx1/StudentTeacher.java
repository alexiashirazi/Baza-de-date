package com.example.proiectbdfx1;

import javafx.beans.property.SimpleStringProperty;


public class StudentTeacher {
    private String nume;
    private String prenume;
    private String anDeStudiu;
    private String CNP;
    private String adresa;
    private String nrDeTelefon;
    private String email;

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setAnDeStudiu(String anDeStudiu) {
        this.anDeStudiu = anDeStudiu;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setNrDeTelefon(String nrDeTelefon) {
        this.nrDeTelefon = nrDeTelefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    private String IBAN;


    public StudentTeacher(String nume, String prenume, String anDeStudiu) {
        this.nume = nume;
        this.prenume = prenume;
        this.anDeStudiu = anDeStudiu;
    }


    public StudentTeacher(String nume, String prenume, String anDeStudiu,String CNP,String adresa,String nrDeTelefon,String email,String IBAN) {
        this.nume = nume;
        this.prenume = prenume;
        this.anDeStudiu = anDeStudiu;
        this.CNP = CNP;
        this.adresa = adresa;
        this.nrDeTelefon = nrDeTelefon;
        this.email = email;
        this.IBAN = IBAN;


    }



    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAnDeStudiu() {
        return anDeStudiu;
    }

    public String getCNP() {
        return CNP;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getNrDeTelefon() {
        return nrDeTelefon;
    }

    public String getEmail() {
        return email;
    }

    public String getIBAN() {
        return IBAN;
    }

}