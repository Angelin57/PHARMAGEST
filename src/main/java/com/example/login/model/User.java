package com.example.login.model;

public class User {
    private int idUser;
    private String identifiant;
    private String mdp;
    private String profil;

    public User(int idUser, String identifiant, String mdp, String profil) {
        this.idUser = idUser;
        this.identifiant = identifiant;
        this.mdp = mdp;
        this.profil = profil;
    }
    public User(){

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }
}
