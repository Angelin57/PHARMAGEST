package com.example.login.model;

import java.util.Date;

public class Approvisionnement {
    //Appro Table//
    private Integer idApprovisonnement;

    private String nom;
    private Date dateRecue;
    private Integer prixFournisseur;
    private Integer quantiteCommande;

    public Integer getId_med() {
        return id_med;
    }

    public void setId_med(Integer id_med) {
        this.id_med = id_med;
    }

    private Integer id_med;

    public Approvisionnement(Integer id_med) {
        this.id_med = id_med;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    private Integer quantiteRecue;
    private String fournisseur;
    //-------------//
    //Medicament//
    private Integer idMed;
    private String nomMedDessous;
    private Integer qttStock;

    public Approvisionnement() {

    }

    public Integer getIdMed() {
        return idMed;
    }

    public void setIdMed(Integer idMed) {
        this.idMed = idMed;
    }

    public String getNomMedDessous() {
        return nomMedDessous;
    }

    public void setNomMedDessous(String nomMedDessous) {
        this.nomMedDessous = nomMedDessous;
    }

    public Integer getQttStock() {
        return qttStock;
    }

    public void setQttStock(Integer qttStock) {
        this.qttStock = qttStock;
    }

    public Approvisionnement(Integer idMed, String nomMedDessous, Integer qttStock) {
        this.idMed = idMed;
        this.nomMedDessous = nomMedDessous;
        this.qttStock = qttStock;
    }
//---------------------------------------//


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Approvisionnement(String status) {
        this.status = status;
    }

    private String status;

    public Integer getIdApprovisonnement() {
        return idApprovisonnement;
    }

    public void setIdApprovisonnement(Integer idApprovisonnement) {
        this.idApprovisonnement = idApprovisonnement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateRecue() {
        return dateRecue;
    }

    public void setDateRecue(Date dateRecue) {
        this.dateRecue = dateRecue;
    }

    public Integer getPrixFournisseur() {
        return prixFournisseur;
    }

    public void setPrixFournisseur(Integer prixFournisseur) {
        this.prixFournisseur = prixFournisseur;
    }

    public Integer getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(Integer quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public Integer getQuantiteRecue() {
        return quantiteRecue;
    }

    public void setQuantiteRecue(Integer quantiteRecue) {
        this.quantiteRecue = quantiteRecue;
    }

    public Approvisionnement(Integer idApprovisonnement, String fournisseur ,String nom, Date dateRecue, Integer prixFournisseur, Integer quantiteCommande, Integer quantiteRecue, String status, Integer id_med) {
        this.idApprovisonnement = idApprovisonnement;
        this.nom = nom;
        this.dateRecue = dateRecue;
        this.prixFournisseur = prixFournisseur;
        this.quantiteCommande = quantiteCommande;
        this.quantiteRecue = quantiteRecue;
        this.fournisseur = fournisseur;
        this.status = status;
        this.id_med = id_med;
    }

    @Override
    public String toString() {
        return nom;
    }

}
