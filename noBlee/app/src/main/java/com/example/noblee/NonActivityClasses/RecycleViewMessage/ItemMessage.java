package com.example.noblee.NonActivityClasses.RecycleViewMessage;

import java.sql.Timestamp;

public class ItemMessage {

    String contenu;
    boolean sentByMalade;
    Timestamp date;
    public ItemMessage(String contenu, boolean sentByMalade,com.google.firebase.Timestamp date) {
        this.date = new Timestamp(date.toDate().getTime());
        this.contenu = contenu;
        this.sentByMalade = sentByMalade;
    }

    public String getContenu() {
        return contenu;
    }

    public boolean isSentByMalade() {
        return sentByMalade;
    }

    public Timestamp getDate() {
        return date;
    }
}
