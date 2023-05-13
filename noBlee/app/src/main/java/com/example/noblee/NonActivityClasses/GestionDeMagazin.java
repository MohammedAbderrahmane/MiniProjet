package com.example.noblee.NonActivityClasses;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.firestore.DocumentReference;

public class GestionDeMagazin {

    static final SQLiteDatabase dbWrite = LocalDataBase.getInstance().getWritableDatabase();
    static final SQLiteDatabase dbRead = LocalDataBase.getInstance().getReadableDatabase();

    static public boolean checkMemeMagazin(DocumentReference magazin){

        if (DatabaseUtils.queryNumEntries(dbRead,LocalDataBase.TABLE_MAGAZIN) == 0){

            ContentValues values = new ContentValues();
            values.put(LocalDataBase.MAGAZIN_PATH,magazin.getPath());

            dbWrite.insert(
                    LocalDataBase.TABLE_MAGAZIN,
                    null,
                    values
            );
            //dbWrite.close();
            return true;
        }
        // comapre parameter with the line of TABLE_MAGAZIN
        return dbRead.query(
                LocalDataBase.TABLE_MAGAZIN,
                new String[]{LocalDataBase.MAGAZIN_PATH},
                LocalDataBase.MAGAZIN_PATH + "= ?",
                new String[]{magazin.getPath()},
                null,
                null,
                null
        ).getCount() == 1;
    }


    static public String getMagazinPath(){
        Cursor pagniesTable = dbRead.query(
                LocalDataBase.TABLE_MAGAZIN,
                new String[]{LocalDataBase.MAGAZIN_PATH},
                null,
                null,
                null,
                null,
                null
        );
        pagniesTable.moveToNext();
        return pagniesTable.getString(pagniesTable.getColumnIndex(LocalDataBase.MAGAZIN_PATH));
    }

    static public void clearMagazin(){
        dbWrite.delete(LocalDataBase.TABLE_MAGAZIN,null,null);
        //dbWrite.close();
    }
}
