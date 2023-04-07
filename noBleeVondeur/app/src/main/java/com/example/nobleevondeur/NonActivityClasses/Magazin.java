package com.example.nobleevondeur.NonActivityClasses;

import com.google.firebase.firestore.DocumentReference;

public class Magazin {
    private static Magazin magazin;
    private DocumentReference ref;

    public DocumentReference getRef() {
        return ref;
    }

    public static Magazin getInstance(){
        if (magazin == null)
            magazin = createInstance(null);
        return magazin;
    }

    public static Magazin createInstance(DocumentReference ref){
        magazin = new Magazin();
        magazin.ref = ref;
        return magazin;
    }
}
