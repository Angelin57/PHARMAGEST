package com.example.login.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;

public class Medicament {
    public Medicament(Integer id_medicament) {
        this.id_medicament = id_medicament;
    }

    public Medicament() {

    }

    public Medicament(String familleName) {
    }

    public Integer getId_medicament() {
        return id_medicament;
    }

    public void setId_medicament(Integer id_medicament) {
        this.id_medicament = id_medicament;
    }

    private Integer id_medicament;
    private String nom_medicament;
    private Integer quantite_medicament;
    private String fournisseur_medicament;
    private String famille_medicament;
    private Integer prix_medicament;

    public String getNom_medicament() {
        return nom_medicament;
    }

    public void setNom_medicament(String nom_medicament) {
        this.nom_medicament = nom_medicament;
    }

    public Integer getQuantite_medicament() {
        return quantite_medicament;
    }

    public void setQuantite_medicament(Integer quantite_medicament) {
        this.quantite_medicament = quantite_medicament;
    }

    public String getFournisseur_medicament() {
        return fournisseur_medicament;
    }

    public void setFournisseur_medicament(String fournisseur_medicament) {
        this.fournisseur_medicament = fournisseur_medicament;
    }

    public String getFamille_medicament() {
        return famille_medicament;
    }

    public void setFamille_medicament(String famille_medicament) {
        this.famille_medicament = famille_medicament;
    }

    public Integer getPrix_medicament() {
        return prix_medicament;
    }

    public void setPrix_medicament(Integer prix_medicament) {
        this.prix_medicament = prix_medicament;
    }

    public Medicament(String nom_medicament, Integer quantite_medicament, String fournisseur_medicament, String famille_medicament, Integer prix_medicament) {
        this.nom_medicament = nom_medicament;
        this.quantite_medicament = quantite_medicament;
        this.fournisseur_medicament = fournisseur_medicament;
        this.famille_medicament = famille_medicament;
        this.prix_medicament = prix_medicament;
    }
    private String nomFamille;
    private String nomFournisseur;


}
