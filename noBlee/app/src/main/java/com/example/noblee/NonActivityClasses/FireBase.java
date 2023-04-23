package com.example.noblee.NonActivityClasses;

import com.google.firebase.firestore.FirebaseFirestore;

public class FireBase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String PUBLICATION = "Publication";
    public static final String PUB_AUTEUR = "auteur";
    public static final String PUB_DATE = "date";
    public static final String PUB_CONTENU = "contenu";
    public static final String PUB_COMMANTAIRE = "Commantaires";
    public static final String PUB_NUM_DISLIKE = "numDislike";
    public static final String PUB_NUM_LIKE = "numLike";
    public static final String PUB_DISLIKE = "Dislikes";
    public static final String PUB_LIKE = "Likes";
    public static final String REACTOR = "user";
}
