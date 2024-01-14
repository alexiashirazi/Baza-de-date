package com.example.proiectbdfx1;



public class Colleague {
    private String nume;
    private String prenume;
    private String Departament;
    private String CNP;
    private String IBAN;
    private String adresa;
    private String email;


    public Colleague(String nume, String prenume, String departament) {
        this.nume = nume;
        this.prenume = prenume;
        this.Departament = departament;
    }

    public Colleague(String nume, String prenume, String departament, String CNP, String IBAN, String adresa, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.IBAN = IBAN;
        this.Departament = departament;
        this.adresa = adresa;
        this.email = email;
    }


    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getDepartament() {
        return Departament;
    }
    public String getCNP() {
        return CNP;
    }

    public String getIBAN() {
        return IBAN;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getEmail() {
        return email;
    }
}