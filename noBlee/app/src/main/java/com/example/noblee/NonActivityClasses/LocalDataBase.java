package com.example.noblee.NonActivityClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SingluBoxDB.db";
    private static final int DATABASE_VERSION = 1;
    public static LocalDataBase instance;
    public static final String TABLE_PAGNIE = "Pagnie";
    public static final String PAGNIE_NOM = "nom";
    public static final String PAGNIE_PRIX = "prix";
    public static final String PAGNIE_QUENTITE = "quentite";
    public static final String PAGNIE_IMAGE_URL = "imageUrl";
    public static final String TABLE_MAGAZIN = "magazin";
    public static final String MAGAZIN_PATH = "path";






    private LocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized LocalDataBase getInstance() {
        return instance;
    }

    public static void createInstance (Context context){
        if (instance == null) {
            instance = new LocalDataBase(context.getApplicationContext());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creeTablePagnie = "CREATE TABLE "+TABLE_PAGNIE+" ("
                + PAGNIE_NOM +" TEXT PRIMARY KEY,"
                + PAGNIE_PRIX +" TEXT,"
                + PAGNIE_QUENTITE +" TEXT,"
                + PAGNIE_IMAGE_URL +" TEXT)";
        db.execSQL(creeTablePagnie);

        String creeTableMagazinRef = "CREATE TABLE "+TABLE_MAGAZIN+" ("
                + MAGAZIN_PATH +" TEXT PRIMARY KEY)";
        db.execSQL(creeTableMagazinRef);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}

