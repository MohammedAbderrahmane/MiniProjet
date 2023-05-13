package com.example.noblee.NonActivityClasses;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.noblee.NonActivityClasses.RecycleViewPagnie.ItemPagnie;

import java.util.ArrayList;
import java.util.List;

public class GestionDePagnies {

    static final SQLiteDatabase dbWrite = LocalDataBase.getInstance().getWritableDatabase();
    static final SQLiteDatabase dbRead = LocalDataBase.getInstance().getReadableDatabase();

    public static List<ItemPagnie> pagnies = new ArrayList<>();


    static public boolean ajouterPagnie (String nom, String prix, String quentite,String imageUrl){

        ContentValues values = new ContentValues();
        values.put(LocalDataBase.PAGNIE_NOM, nom);
        values.put(LocalDataBase.PAGNIE_PRIX, prix);
        values.put(LocalDataBase.PAGNIE_QUENTITE,quentite);
        values.put(LocalDataBase.PAGNIE_IMAGE_URL,imageUrl);

        dbWrite.insert(LocalDataBase.TABLE_PAGNIE,null,values);
        //dbWrite.close();

        pagnies.add(
                new ItemPagnie(nom, prix, quentite, imageUrl)
        );
        return true;
    }

    static public void supprimerPagnie (String nom){
        dbWrite.delete(
                LocalDataBase.TABLE_PAGNIE,
                LocalDataBase.PAGNIE_NOM + "= ?",
                new String[] {nom}
        );
        //dbWrite.close();
        for (int i = 0; i < pagnies.size(); i++) {
            if (pagnies.size() == 1)
                GestionDeMagazin.clearMagazin();
            if (pagnies.get(i).getNom() == nom){
                pagnies.remove(i);
                return;
            }
        }

    }

    static public boolean checkEmptyPagnies(){
        if (GestionDePagnies.pagnies.isEmpty())
            return true;
        return false;
    }

    static public void viderPagnie (){
        dbWrite.delete(LocalDataBase.TABLE_PAGNIE,null,null);
        //dbWrite.close();
        pagnies.clear();
        GestionDeMagazin.clearMagazin();
    }

    static public boolean poduitDejaDemmande(String nom){
        return dbRead.query(
                LocalDataBase.TABLE_PAGNIE,
                new String[]{LocalDataBase.PAGNIE_NOM},
                LocalDataBase.PAGNIE_NOM + "= ?",
                new String[]{nom},
                null,
                null,
                null
        ).getCount() > 0;
    }

    static public void setUpListPagnie (){
        Cursor pagniesTable = dbRead.query(
                LocalDataBase.TABLE_PAGNIE,
                new String[] {LocalDataBase.PAGNIE_NOM,LocalDataBase.PAGNIE_PRIX,LocalDataBase.PAGNIE_QUENTITE,LocalDataBase.PAGNIE_IMAGE_URL},
                null,
                null,
                null,
                null,
                null
        );
        //pagnies.clear();
        while (pagniesTable.moveToNext()) {
            String nom = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_NOM));
            String prix = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_PRIX));
            String quentite = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_QUENTITE));
            String imageUrl = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_IMAGE_URL));
            pagnies.add(
                    new ItemPagnie(
                            nom,
                            prix,
                            quentite,
                            imageUrl
                    )
            );
        }
        pagniesTable.close();
    }

    static public int calculerSommeCommande(){
        int somme = 0;
        Cursor pagniesTable = dbRead.query(
                LocalDataBase.TABLE_PAGNIE,
                new String[] {LocalDataBase.PAGNIE_QUENTITE,LocalDataBase.PAGNIE_PRIX},
                null,
                null,
                null,
                null,
                null
        );
        while (pagniesTable.moveToNext()) {
            String prixUnitaire = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_PRIX));
            String quentite = pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.PAGNIE_QUENTITE));
            somme += Integer.parseInt(prixUnitaire)*Integer.parseInt(quentite);
        }
        //pagniesTable.close();
        return somme;
    }
}
