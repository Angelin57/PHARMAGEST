package com.example.login.model;

public class Vente {
    private Integer idVente;
    private String nomMedicament;
    private Integer prixMedicament;
    private Integer qttAchete;
    private Integer prixTotal;
    private String nomClient;
    private String nomMedecin;
    private String statut;
    private Integer idMedicament;
    private Integer idFacture;

    public Vente(String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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

    public void setIdVente(Integer idMedicament) {
        this.idVente = idMedicament;
    }

    public String getNomMed() {
        return nomMedicament;
    }

    public void setNomMedicament(String nomMedicament) {
        this.nomMedicament = nomMedicament;
    }

    public Integer getPrixMed() {
        return prixMedicament;
    }

    public void setPrixMedicament(Integer prixMedicament) {
        this.prixMedicament = prixMedicament;
    }

    public Integer getQttAchete () {
        return qttAchete;
    }

    public void setQttAchete(Integer qttAchete) {
        this.qttAchete = qttAchete;
    }

    public Integer getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(Integer prixTotal) {
        this.prixTotal = prixTotal;
    }

    public Integer getIdMedicament() { return idMedicament; }

    public void setIdMedicament(Integer idMedicament) { this.idMedicament = idMedicament; }

    public int getIdFacture() { return idFacture; }

    public void setIdFacture(int idFacture) { this.idFacture = idFacture;    }





    public Vente(Integer idVente, String nomMedicament, Integer prixMedicament, Integer qttAchete, Integer prixTotal) {
        this.idVente = idVente;
        this.nomMedicament = nomMedicament;
        this.prixMedicament = prixMedicament;
        this.qttAchete = qttAchete;
        this.prixTotal = prixTotal;
    }

}
