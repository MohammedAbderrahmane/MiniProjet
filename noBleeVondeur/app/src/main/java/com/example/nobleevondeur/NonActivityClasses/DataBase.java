package com.example.nobleevondeur.NonActivityClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "NoBleeVondeur.db";
    public static final int DATABASE_VERSION = 1;

    private static final String MAGAZIN = "Magazin";
    private static final String PATH = "Path";

    private static DataBase instance;

    private DocumentReference magazinRef = null;

    private DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void setMagazinRef(boolean remeberMe,DocumentReference ref){
        if (remeberMe){
            ContentValues values = new ContentValues();
            values.put(PATH,ref.getPath());
            getWritableDatabase().insert(
                    MAGAZIN,
                    null,
                    values
            );
            getWritableDatabase().close();
        }
        magazinRef = ref;
    }

    public void clearMagazin(){
        getWritableDatabase().delete(MAGAZIN,null,null);
        getWritableDatabase().close();
        magazinRef = null;
    }

    public void setMagazinRefFromDataBase(){
        Cursor pagniesTable = getWritableDatabase().query(
                MAGAZIN,
                new String[]{PATH},
                null,
                null,
                null,
                null,
                null
        );
        pagniesTable.moveToNext();
        magazinRef = FirebaseFirestore.getInstance().document(pagniesTable.getString(pagniesTable.getColumnIndex(PATH)));
        pagniesTable.close();
    }
    public boolean magazinExist (){
        return DatabaseUtils.queryNumEntries(getReadableDatabase(),MAGAZIN) == 1;
    }

    public DocumentReference getMagazinRef() {
        return magazinRef;
    }

    public DocumentReference getMagazinRef(String path) {
        return FirebaseFirestore.getInstance().document(path);
    }

    public static DataBase getInstance(Context context) {
        if (instance == null) {
            instance = new DataBase(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creeTableMagazinRef = "CREATE TABLE "+ MAGAZIN +" ("
                + PATH +" TEXT PRIMARY KEY)";
        sqLiteDatabase.execSQL(creeTableMagazinRef);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
