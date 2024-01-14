package com.example.proiectbdfx1;

public class Nota {
    private String materie_nume;
    private String notaCurs;
    private String notaSeminar;
    private String notaLaborator;
    private String notaFinala;

    public Nota(String materie_nume, String notaCurs, String notaSeminar, String notaLaborator, String notaFinala) {
        this.materie_nume = materie_nume;
        this.notaCurs = notaCurs;
        this.notaSeminar = notaSeminar;
        this.notaLaborator = notaLaborator;
        this.notaFinala = notaFinala;
    }

    public String getMaterie_nume() {
        return materie_nume;
    }

    public String getNotaCurs() {
        return notaCurs;
    }

    public String getNotaSeminar() {
        return notaSeminar;
    }

    public String getNotaLaborator() {
        return notaLaborator;
    }

    public String getNotaFinala() {
        return notaFinala;
    }
}
