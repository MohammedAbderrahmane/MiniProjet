package com.example.noblee.NonActivityClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.noblee.NonActivityClasses.RecycleViewPagnie.ItemPagnie;

import java.util.ArrayList;
import java.util.List;

public class SavedPagniesDb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "savedPagnies.db";
    private static final int DATABASE_VERSION = 1;

    private static final String T = "Pagnie";
    private static final String N = "Nom";
    private static final String P = "Prix";
    private static final String Q = "Quentite";
    private static SavedPagniesDb instance;

    public static List<ItemPagnie>pagnies = new ArrayList<ItemPagnie>();
    private SavedPagniesDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SavedPagniesDb getInstance(Context context) {
        if (instance == null) {
            instance = new SavedPagniesDb(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creeTablePagnie = "CREATE TABLE "+T+" ("
                + N +" TEXT PRIMARY KEY,"
                + P +" TEXT,"
                + Q +" TEXT)";
        db.execSQL(creeTablePagnie);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade your database schema here
    }

    public boolean ajouterUnPagnie (String nom, String prix, String quentite){
        //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        if (
                getWritableDatabase().query(
                        T,
                        new String[] {N},
                        N + "= ?",
                        new String[] {nom},
                        null,
                        null,
                        null
                ).getCount() > 0
        )
            return false;

        ContentValues values = new ContentValues();
        values.put(N, nom);
        values.put(P, prix);
        values.put(Q,quentite);
        getWritableDatabase().insert(T,null,values);
        getWritableDatabase().close();

        pagnies.add(new ItemPagnie(
                nom,
                prix,
                quentite));
        return true;
    }

    public void supprimer (String nom){
        getWritableDatabase().delete(T,N +"= ?",new String[] {nom});
        getWritableDatabase().close();
        for (int i = 0; i < pagnies.size(); i++) {
            if (pagnies.get(i).getNom() == nom){
                pagnies.remove(i);
                return;
            }
        }
    }

    public void viderPagnie (){
        getWritableDatabase().delete(T,null,null);
        getWritableDatabase().close();
        pagnies.clear();
    }

    public void setUpListPagnie (){
        Cursor pagniesTable = getWritableDatabase().query(
                T,
                new String[] {N,P,Q},
                null,
                null,
                null,
                null,
                null
        );
        pagnies.clear();
        while (pagniesTable.moveToNext()) {
            String nom = pagniesTable.getString(pagniesTable.getColumnIndex(N));
            String prix = pagniesTable.getString(pagniesTable.getColumnIndex(P));
            String quentite = pagniesTable.getString(pagniesTable.getColumnIndex(Q));
            pagnies.add(
                    new ItemPagnie(
                            nom,
                            prix,
                            quentite
                    )
            );
        }
        pagniesTable.close();
    }

    public int calculerSommeCommande(){
        int somme = 0;
        Cursor pagniesTable = getWritableDatabase().query(
                T,
                new String[] {N,P,Q},
                null,
                null,
                null,
                null,
                null
        );
        while (pagniesTable.moveToNext()) {
            somme += Integer.getInteger(pagniesTable.getString(pagniesTable.getColumnIndex(P)));
        }
        pagniesTable.close();
        return somme;
    }
}

