package com.example.login.model;

public class Famille {
    private int idFamille;
    private String Nom;

    public Famille() {

    }

    public int getIdFamille() {
        return idFamille;
    }

    public void setIdFamille(int idFamille) {
        this.idFamille = idFamille;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        this.Nom = nom;
    }

    public Famille(int idFamille, String nom) {
        this.idFamille = idFamille;
        this.Nom = nom;
    }
}
