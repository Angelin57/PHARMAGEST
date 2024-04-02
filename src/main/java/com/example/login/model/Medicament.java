package com.example.login.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Medicament {
    private IntegerProperty id_medicament;
    private StringProperty nom;
    private StringProperty forme;
    private StringProperty famille;
    private IntegerProperty prix;
    private IntegerProperty quantite;

    //quantite//

    public int getQuantite() {
        return quantite.get();
    }

    public IntegerProperty quantiteProperty() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite.set(quantite);
    }
    //quantite//

    //id//
    public int getId_medicament() {
        return id_medicament.get();
    }

    public IntegerProperty medIdProperty() {
        return id_medicament;
    }

    public void setId_medicament(int id_medicament) {
        this.id_medicament.set(id_medicament);
    }

    //nom//
    public String getNom() {
        return nom.get();
    }

    public StringProperty medNameProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }
    //forme//
    public String getForme() {
        return forme.get();
    }

    public StringProperty medFormeProperty() {
        return forme;
    }

    public void setForme(String forme) {
        this.forme.set(forme);
    }

    //famille//
    public String getFamille() {
        return famille.get();
    }

    public StringProperty medFamilleProperty() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille.set(famille);
    }

    //prix//
    public int getPrix() {
        return prix.get();
    }

    public IntegerProperty medPrixProperty() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix.set(prix);
    }


    public Medicament(IntegerProperty idMedicament, StringProperty nom, StringProperty forme, StringProperty famille, IntegerProperty prix, IntegerProperty quantite) {
        this.id_medicament = idMedicament;
        this.nom = nom;
        this.forme = forme;
        this.famille = famille;
        this.prix = prix;
        this.quantite= quantite;
    }
}
