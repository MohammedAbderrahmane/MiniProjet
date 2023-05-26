package com.example.noblee.NonActivityClasses.EtatDialog.CommandeRecycleView;

import com.example.noblee.NonActivityClasses.EtatDialog.LigneCommandeRecycleView.ItemLigneCommmande;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemCommande {

    Timestamp date;
    String maladePath,prixTotal,nomMagazin,statue;
    DocumentReference commandeRef;
    public List<ItemLigneCommmande> ligneCommmandeList;

    public ItemCommande(Timestamp date, String maladePath, String prixTotal,String statue, DocumentReference reference) {
        this.date = date;
        this.maladePath = maladePath;
        this.prixTotal = prixTotal;
        this.statue = statue;
        this.commandeRef = reference;
        //setUpLigneCommandeList();
    }

    public ItemCommande(Timestamp date, String maladePath, String prixTotal,String statue,String nomMagazin, DocumentReference reference) {
        this.date = date;
        this.maladePath = maladePath;
        this.prixTotal = prixTotal;
        this.statue = statue;
        this.nomMagazin = nomMagazin;
        this.commandeRef = reference;
        setUpLigneCommandeList();
    }

    private void setUpLigneCommandeList() {
        ligneCommmandeList = new ArrayList<>();
        commandeRef
                .collection("LigneCommande")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            ligneCommmandeList.add(
                                    new ItemLigneCommmande(
                                            document.getString("nom"),
                                            document.getString("prix"),
                                            document.getString("quentite"),
                                            document.getString("imageUrl")
                                    )
                            );
                        }
                    }
                });
    }


    public Timestamp getDate() {
        return date;
    }

    public String getMaladePath() {
        return maladePath;
    }

    public String getPrixTotal() {
        return prixTotal;
    }

    public String getNomMagazin() {
        return nomMagazin;
    }

    public DocumentReference getCommandeRef() {
        return commandeRef;
    }

    public String getStatue() {
        return statue;
    }
}
