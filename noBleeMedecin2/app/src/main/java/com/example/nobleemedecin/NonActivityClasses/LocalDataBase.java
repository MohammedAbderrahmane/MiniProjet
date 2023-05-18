package com.example.nobleemedecin.NonActivityClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocalDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NoBleeMedecin.db";
    public static final int DATABASE_VERSION = 1;

    private static final String MEDECIN = "Medecin";
    private static final String PATH = "Path";

    private static LocalDataBase instance;

    private DocumentReference medecinRef = null;

    private LocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setMedecinRef(boolean remeberMe,DocumentReference ref){
        if (remeberMe){
            ContentValues values = new ContentValues();
            values.put(PATH,ref.getPath());
            getWritableDatabase().insert(
                    MEDECIN,
                    null,
                    values
            );
            getWritableDatabase().close();
        }
        medecinRef = ref;
    }

    public void clearMedecin(){
        getWritableDatabase().delete(MEDECIN,null,null);
        getWritableDatabase().close();
        medecinRef = null;
    }

    public void setMedecinRefFromDataBase(){
        Cursor pagniesTable = getWritableDatabase().query(
                MEDECIN,
                new String[]{PATH},
                null,
                null,
                null,
                null,
                null
        );
        pagniesTable.moveToNext();
        medecinRef = FirebaseFirestore.getInstance().document(pagniesTable.getString(pagniesTable.getColumnIndex(PATH)));
        pagniesTable.close();
    }
    public boolean medecinExist (){
        return DatabaseUtils.queryNumEntries(getReadableDatabase(),MEDECIN) == 1;
    }

    public DocumentReference getMedecinRef() {
        return medecinRef;
    }


    public static LocalDataBase getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataBase(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creeTableMagazinRef = "CREATE TABLE "+ MEDECIN +" ("
                + PATH +" TEXT PRIMARY KEY)";
        sqLiteDatabase.execSQL(creeTableMagazinRef);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static DocumentReference getCurrentReference(){
        return instance.getMedecinRef();
    }

}
