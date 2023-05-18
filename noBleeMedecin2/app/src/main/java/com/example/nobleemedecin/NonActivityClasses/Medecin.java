package com.example.nobleemedecin.NonActivityClasses;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class Medecin {
    String nom,prenom;
    DocumentReference reference;

    static private Medecin medecin;

    static public Medecin getInstance(){
        return medecin;
    }
    static public void createInstance(){
        medecin = new Medecin();
    }

    private Medecin (){
        reference = LocalDataBase.getCurrentReference();
        setUpInfo();
    }

    private void setUpInfo() {
        reference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        nom = documentSnapshot.getString("nom");
                        prenom = documentSnapshot.getString("prenom");
                    }
                });
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public DocumentReference getReference() {
        return reference;
    }
}
