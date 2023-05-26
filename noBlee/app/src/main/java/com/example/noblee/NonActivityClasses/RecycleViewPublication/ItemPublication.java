package com.example.noblee.NonActivityClasses.RecycleViewPublication;

import com.example.noblee.NonActivityClasses.KeyWord;
import com.example.noblee.NonActivityClasses.RecycleViewCommantaire.ItemCommantaire;
import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemPublication {
    String auteur,contenu, numLike, numDislike;
    Date date;
    DocumentReference reference;
    boolean creeParMedecin;
    List<ItemCommantaire> commantaireList;


    public ItemPublication(String auteur, String contenu, Date date, String likes, String dislikes,DocumentReference reference, Boolean creeParMedecin) {
        this.auteur = auteur;
        this.date = date;
        this.contenu = contenu;
        this.numLike = likes;
        this.numDislike = dislikes;
        this.reference = reference;
        this.creeParMedecin = creeParMedecin;
        setUpCommanatires();
    }

    public ItemPublication(String auteur, String contenu, Date date, String likes, String dislikes, Boolean creeParMedecin) {
        this.auteur = auteur;
        this.date = date;
        this.contenu = contenu;
        this.numLike = likes;
        this.numDislike = dislikes;
        this.creeParMedecin = creeParMedecin;
        //setUpCommanatires();
    }

    private void setUpCommanatires() {
        commantaireList = new ArrayList<>();
        reference.collection(KeyWord.PUB_COMMANTAIRE)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            commantaireList.add(
                                    new ItemCommantaire(
                                            document.getString("user"),
                                            document.getString("contenu"),
                                            document.getReference()
                                    )
                            );
                        }
                    }
                });
    }

    public Task<DocumentReference> ajouterCommantaire(String contenu){
        ItemCommantaire commantaire = new ItemCommantaire(
                User.getInstance().getNom() + " " + User.getInstance().getPrenom(),
                contenu
        );

        commantaireList.add(commantaire);
        return reference.collection(KeyWord.PUB_COMMANTAIRE)
                .add(commantaire);
    }
    public String calculerDate() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate currentDate = LocalDate.now();
            Period difference = null;
            difference = Period.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), currentDate);
            if (difference.getMonths() > 0)
                return "" + difference.getMonths() + "months";
            if (difference.getDays() > 0)
                return "" + difference.getDays() + "days";
            return "meme journee";
        }
        return "Vesion incopatiple avec Period";
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumLike() {
        return numLike;
    }

    public void setNumLike(String numLike) {
        this.numLike = numLike;
    }

    public String getNumDislike() {
        return numDislike;
    }

    public void setNumDislike(String numDislike) {
        this.numDislike = numDislike;
    }

    public DocumentReference getReference() {
        return reference;
    }

    public void setReference(DocumentReference reference) {
        this.reference = reference;
    }

    public boolean isCreeParMedecin() {
        return creeParMedecin;
    }

    public void setCreeParMedecin(boolean creeParMedecin) {
        this.creeParMedecin = creeParMedecin;
    }

    public List<ItemCommantaire> getCommantaireList() {
        return commantaireList;
    }
}
