package com.example.proiectbdfx1;

public class SuperAdminH {

    private String nume;
    private String prenume;
    private String CNP;
    private String IBAN;
    private String adresa;
    private String email;

    public SuperAdminH(String nume, String prenume, String CNP, String IBAN, String adresa, String email) {
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.IBAN = IBAN;
        this.adresa = adresa;
        this.email = email;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
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