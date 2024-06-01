package com.example.login.model;

import java.util.ArrayList;
import java.util.List;

public class Caisse {
        private List<Facture> factures;

        // Constructeur
        public Caisse() {
                factures = new ArrayList<>();
        }

        // Getters et setters pour factures
        public List<Facture> getFactures() {
                return factures;
        }

        public void setFactures(List<Facture> factures) {
                this.factures = factures;
        }

        public void ajouterFacture(Facture facture) {
                factures.add(facture);
        }


}
