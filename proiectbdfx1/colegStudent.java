package com.example.proiectbdfx1;

public class colegStudent {

    private String nume;
    private String prenume;
    private String anDeStudiu;

    public colegStudent(String nume, String prenume, String anDeStudiu) {
        this.nume = nume;
        this.prenume = prenume;
        this.anDeStudiu = anDeStudiu;
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
}
