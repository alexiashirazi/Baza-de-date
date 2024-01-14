package com.example.proiectbdfx1;

public class NotaPentruProfesor {

    private String Materie;
    private String numeStudent;
    private int notaCurs;
    private int notaSeminar;
    private int notaLaborator;
    private int notaFinala;

    public NotaPentruProfesor(String materie, String numeStudent, int notaCurs, int notaSeminar, int notaLaborator, int notaFinala) {
        Materie = materie;
        this.numeStudent = numeStudent;
        this.notaCurs = notaCurs;
        this.notaSeminar = notaSeminar;
        this.notaLaborator = notaLaborator;
        this.notaFinala = notaFinala;
    }
    public void setMaterie(String materie) {
        Materie = materie;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public void setNotaCurs(int notaCurs) {
        this.notaCurs = notaCurs;
    }

    public void setNotaSeminar(int notaSeminar) {
        this.notaSeminar = notaSeminar;
    }

    public void setNotaLaborator(int notaLaborator) {
        this.notaLaborator = notaLaborator;
    }

    public void setNotaFinala(int notaFinala) {
        this.notaFinala = notaFinala;
    }
    public String getMaterie() {
        return Materie;
    }

    public String getNumeStudent() {
        return numeStudent;
    }

    public int getNotaCurs() {
        return notaCurs;
    }

    public int getNotaSeminar() {
        return notaSeminar;
    }

    public int getNotaLaborator() {
        return notaLaborator;
    }

    public int getNotaFinala() {
        return notaFinala;
    }
}
