package com.example.noblee.NonActivityClasses.RecycleViewCommantaire;

public class ItemCommantaire {
    String user,contenu;

    public ItemCommantaire(String user, String contenu) {
        this.user = user;
        this.contenu = contenu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
