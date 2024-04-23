package com.example.login.model;

public class Vente {
    private Integer idVente;
    private String nomMed;
    private Integer prixMed;
    private Integer qttAcheteMed;
    private Integer prixTotal;
    private String nomClient;
    private String nomMedecin;
    private String status;

    public Vente(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vente(String nomClient, String nomMedecin) {
        this.nomClient = nomClient;
        this.nomMedecin = nomMedecin;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNomMedecin() {
        return nomMedecin;
    }

    public void setNomMedecin(String nomMedecin) {
        this.nomMedecin = nomMedecin;
    }



    public Vente() {

    }

    public Integer getIdVente() {
        return idVente;
    }

    public void setIdVente(Integer idMed) {
        this.idVente = idMed;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public Integer getPrixMed() {
        return prixMed;
    }

    public void setPrixMed(Integer prixMed) {
        this.prixMed = prixMed;
    }

    public Integer getQttAcheteMed() {
        return qttAcheteMed;
    }

    public void setQttAcheteMed(Integer qttAcheteMed) {
        this.qttAcheteMed = qttAcheteMed;
    }

    public Integer getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Integer prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Vente(Integer idVente, String nomMed, Integer prixMed, Integer qttAcheteMed, Integer prixTotal) {
        this.idVente = idVente;
        this.nomMed = nomMed;
        this.prixMed = prixMed;
        this.qttAcheteMed = qttAcheteMed;
        this.prixTotal = prixTotal;
    }
}
