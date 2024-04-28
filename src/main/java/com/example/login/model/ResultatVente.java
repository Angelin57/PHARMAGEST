package com.example.login.model;

import java.sql.Timestamp;

public class ResultatVente {
    private Timestamp heureCreation;
    private int nombreVentes;
    private int prixTotal;
    private String statutFacture;

    public String getStatutFacture() {
        return statutFacture;
    }

    public void setStatutFacture(String statutFacture) {
        this.statutFacture = statutFacture;
    }

    public Timestamp getHeureCreation() {
        return heureCreation;
    }

    public void setHeureCreation(Timestamp heureCreation) {
        this.heureCreation = heureCreation;
    }

    public int getNombreVentes() {
        return nombreVentes;
    }

    public void setNombreVentes(int nombreVentes) {
        this.nombreVentes = nombreVentes;
    }

    public int getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(int prixTotal) {
        this.prixTotal = prixTotal;
    }

    public ResultatVente(Timestamp heureCreation, int nombreVentes, int prixTotal, String statutFacture) {
        this.heureCreation = heureCreation;
        this.nombreVentes = nombreVentes;
        this.prixTotal = prixTotal;
        this.statutFacture = statutFacture;

    }


}
