package com.example.login.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Caisse {

        private Integer id_vente;
        private Timestamp heure_creation;
        private String status;
        private Integer prix_total;
        private Integer idMed;
        private String nomMed;
        private Integer qtt;
        private Integer prix_unit;
        private Timestamp heureCreation;
        private int nombreVentes;
        private int prixTotal;
        private String statutFacture;
        private String facture;

        public String getFacture() {
                return facture;
        }

        public void setFacture(String facture) {
                this.facture = facture;
        }

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

        public Caisse() {

        }


        public Integer getId_vente() {
                return id_vente;
        }

        public void setId_vente(Integer id_vente) {
                this.id_vente = id_vente;
        }

        public Timestamp getHeure_creation() {
                return heure_creation;
        }

        public void setHeure_creation(Timestamp heure_creation) {
                this.heure_creation = heure_creation;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public Integer getPrix_total() {
                return prix_total;
        }

        public void setPrix_total(Integer prix_total) {
                this.prix_total = prix_total;
        }

        public Integer getIdMed() {
                return idMed;
        }

        public void setIdMed(Integer idMed) {
                this.idMed = idMed;
        }

        public String getNomMed() {
                return nomMed;
        }

        public void setNomMed(String nomMed) {
                this.nomMed = nomMed;
        }

        public Integer getQtt() {
                return qtt;
        }

        public void setQtt(Integer qtt) {
                this.qtt = qtt;
        }

        public Integer getPrix_unit() {
                return prix_unit;
        }

        public void setPrix_unit(Integer prix_unit) {
                this.prix_unit = prix_unit;
        }

        public Caisse(Integer id_vente, Timestamp heure_creation, String status, Integer prix_total, Integer idMed, String nomMed, Integer qtt, Integer prix_unit, Timestamp heureCreation, int nombreVentes, int prixTotal, String statutFacture, String facture) {
                this.id_vente = id_vente;
                this.heure_creation = heure_creation;
                this.status = status;
                this.prix_total = prix_total;
                this.idMed = idMed;
                this.nomMed = nomMed;
                this.qtt = qtt;
                this.prix_unit = prix_unit;
                this.heureCreation = heureCreation;
                this.nombreVentes = nombreVentes;
                this.prixTotal = prixTotal;
                this.statutFacture = statutFacture;
                this.facture = facture;
        }
}
