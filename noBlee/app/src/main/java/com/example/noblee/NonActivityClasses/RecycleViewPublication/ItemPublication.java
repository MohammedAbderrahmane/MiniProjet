package com.example.noblee.NonActivityClasses.RecycleViewPublication;

import com.google.firebase.firestore.DocumentReference;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class ItemPublication {
    String auteur,contenu, numLike, numDislike;
    Date date;
    DocumentReference reference;


    public ItemPublication(String auteur, String contenu, Date date, String likes, String dislikes) {
        this.auteur = auteur;
        this.date = date;
        this.contenu = contenu;
        this.numLike = likes;
        this.numDislike = dislikes;
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

    public void subNumDislike() {
        numDislike = String.valueOf(
                Integer.parseInt(numDislike) - 1
        );
    }

    public void subLike() {
        numLike = String.valueOf(
                Integer.parseInt(numLike) - 1
        );
    }

    public void addLike() {
        numLike = String.valueOf(
                Integer.parseInt(numLike) + 1
        );
    }

    public void addDislike() {
        numDislike = String.valueOf(
                Integer.parseInt(numDislike) + 1
        );
    }
}
