package com.example.noblee.NonActivityClasses;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class User {
    DocumentReference reference;
    String nom="",prenom="",email="",adresse="";

    boolean isMod = false;
    static private User user;

    static public User getInstance(){
        return user;
    }
    static public void createInstance(){
        FirebaseFirestore.getInstance()
                .collection("Malades")
                .whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        user = new User(queryDocumentSnapshots.getDocuments().get(0).getReference());
                    }
                });
    }

    private User(DocumentReference reference){
        this.reference = reference;
        fillInformation();
    }

    void fillInformation (){
        this.reference
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        nom = documentSnapshot.getString("nom");
                        prenom = documentSnapshot.getString("prenom");
                        email = documentSnapshot.getString("email");
                        if (documentSnapshot.contains("adresse")){
                            adresse = documentSnapshot.getString("adresse");
                        }
                        if (documentSnapshot.contains("isMod")){
                            isMod = true;
                        }
                    }
                });
    }

    public boolean isConnected(){
        if (user == null)
            return false;
        return true;
    }

    static public void disconnect(){
        user = null;
    }
    public DocumentReference getReference(){
        return reference;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getAdresse() {
        return adresse;
    }
    public boolean isMod() {return isMod;}
}
