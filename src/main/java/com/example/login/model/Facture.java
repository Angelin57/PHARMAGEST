package com.example.login.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Facture {
    private int idFacture;
    private LocalDate dateCreation;
    private LocalTime heureCreation;
    private int montantTotal;
    private String statut;
    private Boolean ordonnance;
    private List<Vente> ventes;

    public Facture() {
        this.ventes = new ArrayList<>();
    }

    // Getters et Setters
    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalTime getHeureCreation() {
        return heureCreation;
    }

    public void setHeureCreation(LocalTime heureCreation) {
        this.heureCreation = heureCreation;
    }

    public int getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(int montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Boolean getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(Boolean ordonnance) {
        this.ordonnance = ordonnance;
    }

    public List<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(List<Vente> ventes) {
        this.ventes = ventes;
    }

    public void ajouterVente(Vente vente) {
        this.ventes.add(vente);
    }
}
