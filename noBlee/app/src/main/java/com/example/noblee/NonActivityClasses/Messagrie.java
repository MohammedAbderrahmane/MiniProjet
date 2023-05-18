package com.example.noblee.NonActivityClasses;

import com.example.noblee.NonActivityClasses.RecycleViewMessage.ItemMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Messagrie {

    String maladePath,medecinPath;
    String maladeNomPrenom,medecinNomPrenom;
    List<ItemMessage> messages;
    DocumentReference reference;
    // TODO : get messagge from document/collection(messages)


    public Messagrie(String maladePath, String medecinPath,String maladeNomPrenom,String medecinNomPrenom,DocumentReference reference) {
        this.maladePath = maladePath;
        this.maladeNomPrenom = maladeNomPrenom;
        this.medecinNomPrenom = medecinNomPrenom;
        this.medecinPath = medecinPath;
        this.reference = reference;
        setUpMessagesList();
    }

    public Messagrie(String maladePath, String medecinPath,String maladeNomPrenom,String medecinNomPrenom) {
        this.maladePath = maladePath;
        this.maladeNomPrenom = maladeNomPrenom;
        this.medecinNomPrenom = medecinNomPrenom;
        this.medecinPath = medecinPath;
    }

    public String getMaladePath() {
        return maladePath;
    }

    public String getMedecinPath() {
        return medecinPath;
    }

    public String getMaladeNomPrenom() {
        return maladeNomPrenom;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public List<ItemMessage> getMessages() {
        return messages;
    }

    public String getMedecinNomPrenom() {
        return medecinNomPrenom;
    }

    public void setMedecinNomPrenom(String medecinNomPrenom) {
        this.medecinNomPrenom = medecinNomPrenom;
    }

    void setUpMessagesList(){
        messages = new ArrayList<>();
        reference.collection("Messages")
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            messages.add(
                                    new ItemMessage(
                                            document.getString("contenu"),
                                            document.getBoolean("sentByMalade"),
                                            document.getTimestamp("date")
                                    )
                            );
                        }
                    }
                });
    }


}
