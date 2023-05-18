package com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie;

import com.example.nobleemedecin.NonActivityClasses.Medecin;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessage.ItemMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemMessagrie implements Serializable{

    String maladePath,medecinPath;
    String maladeNomPrenom,medecinNomPrenom;
    List<ItemMessage> messages;
    DocumentReference reference;
    // TODO : get messagge from document/collection(messages)


    public ItemMessagrie(String maladePath, String medecinPath,String maladeNomPrenom,String medecinNomPrenom,DocumentReference reference) {
        this.maladePath = maladePath;
        this.maladeNomPrenom = maladeNomPrenom;
        this.medecinNomPrenom = medecinNomPrenom;
        this.medecinPath = medecinPath;
        this.reference = reference;
        setUpMessagesList();
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
