package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GetAccessActivity extends AppCompatActivity {

    EditText accee;
    ImageButton accept;
    SwitchCompat rememberMe;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_access);

        if (DataBase.getInstance(GetAccessActivity.this).magazinExist()){
            DataBase.getInstance(getApplicationContext()).setMagazinRefFromDataBase();
            finish();
            startActivity(new Intent(GetAccessActivity.this,MainActivity.class));
        }

        try {
            Toolbar tool_bar =findViewById(R.id.tool_bar);
            setSupportActionBar(tool_bar);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }



        accee = findViewById(R.id.get_access_code);
        accept = findViewById(R.id.get_access_accept);
        rememberMe = findViewById(R.id.get_access_remember_me);
        progressBar = findViewById(R.id.get_access_progress_bar);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCodeAccee();
            }
        });
    }

    void verifyCodeAccee(){
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("Magazin")
                .whereEqualTo("code",accee.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() == 1){
                            DataBase.getInstance(GetAccessActivity.this)
                                    .setMagazinRef(
                                            rememberMe.isChecked(),
                                            queryDocumentSnapshots.getDocuments().get(0).getReference()
                                    );
                            Toast.makeText(GetAccessActivity.this, "Code accee VALIDE", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            finish();
                            startActivity(new Intent(GetAccessActivity.this,MainActivity.class));
                        }else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(GetAccessActivity.this, "Code accee INVALIDE", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(GetAccessActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}