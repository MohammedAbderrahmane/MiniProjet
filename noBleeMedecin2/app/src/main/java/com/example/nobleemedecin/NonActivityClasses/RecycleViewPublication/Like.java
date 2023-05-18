package com.example.nobleemedecin.NonActivityClasses.RecycleViewPublication;

public class Like {

    String user;
    boolean creeParMedecin ;
    public Like(String uid,boolean creeParMedecin) {
        user = uid;
        this.creeParMedecin = creeParMedecin;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
