package com.example.proiectbdfx1;

public class ColleagueStudent {

    private String nume;
    private String prenume;
    private String Departament;

    public ColleagueStudent(String nume, String prenume, String Departament) {
        this.nume = nume;
        this.prenume = prenume;
        this.Departament = Departament;
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
}
