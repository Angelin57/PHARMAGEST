package com.example.login.model;

import java.util.Date;

public class ListeCommande {
    private Integer idAppro;
    private String nomMed;
    private Date dateRecue;
    private String fournisseur;
    private Integer prixFournisseur;
    private Integer qttRecue;
    private Integer qttCommande;
    private String status;
    private Integer prixUnit;
    private Integer qttInitial;

    public ListeCommande(Integer prixUnit, Integer qttInitial) {
        this.prixUnit = prixUnit;
        this.qttInitial = qttInitial;
    }

    public Integer getPrixUnit() {
        return prixUnit;
    }

    public void setPrixUnit(Integer prixUnit) {
        this.prixUnit = prixUnit;
    }

    public Integer getQttInitial() {
        return qttInitial;
    }

    public void setQttInitial(Integer qttInitial) {
        this.qttInitial = qttInitial;
    }

    public ListeCommande() {

    }


    public Integer getIdAppro() {
        return idAppro;
    }

    public void setIdAppro(Integer idAppro) {
        this.idAppro = idAppro;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public Date getDateRecue() {
        return dateRecue;
    }

    public void setDateRecue(Date dateRecue) {
        this.dateRecue = dateRecue;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Integer getPrixFournisseur() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(Integer prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public Integer getQttRecue() {
        return qttRecue;
    }

    public void setQttRecue(Integer qttRecue) {
        this.qttRecue = qttRecue;
    }

    public Integer getQttCommande() {
        return qttCommande;
    }

    public void setQttCommande(Integer qttCommande) {
        this.qttCommande = qttCommande;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ListeCommande(Integer idAppro, String nomMed, Date dateRecue, String fournisseur, Integer prixFournisseur, Integer qttRecue, Integer qttCommande, String status) {
        this.idAppro = idAppro;
        this.nomMed = nomMed;
        this.dateRecue = dateRecue;
        this.fournisseur = fournisseur;
        this.prixFournisseur = prixFournisseur;
        this.qttRecue = qttRecue;
        this.qttCommande = qttCommande;
        this.status = status;
    }

    public void setMedicament(Medicament medicament) {
    }
}
