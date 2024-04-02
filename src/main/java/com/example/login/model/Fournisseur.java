package com.example.login.model;

public class Fournisseur {
    private int IdFournisseur;
    private String Nom;
    private int Telephone;
    private String Email;


    public Fournisseur(int idFournisseur, String nom, String email, int telephone) {
     this.IdFournisseur = idFournisseur;
     this.Email = email;
     this.Nom = nom;
     this.Telephone = telephone;
    }

    public Fournisseur() {

    }

    public int getIdFournisseur() {return IdFournisseur;}
    public void setIdFournisseur(int idFournisseur){IdFournisseur = idFournisseur;}
    public String getNom() {return Nom;}
    public void setNom(String nom) {Nom = nom;}
    public String getEmail() {return Email;}

    public void setEmail(String email) {Email = email;}

    public int getTelephone() {return Telephone;}

    public void setTelephone(int telephone) {Telephone = telephone;}
}
