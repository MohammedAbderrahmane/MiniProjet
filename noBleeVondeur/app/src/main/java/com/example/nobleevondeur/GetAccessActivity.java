package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nobleevondeur.NonActivityClasses.Magazin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class GetAccessActivity extends AppCompatActivity {
    EditText accee;
    Button accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_access);

        accee = findViewById(R.id.get_access_code);
        accept = findViewById(R.id.get_access_btn);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCodeAccee();
            }
        });
    }

    void verifyCodeAccee(){
        FirebaseFirestore.getInstance().collection("Magazin")
                .whereEqualTo("code",accee.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() == 1){
                            Toast.makeText(GetAccessActivity.this, "Code is correct", Toast.LENGTH_SHORT).show();
                            Magazin.createInstance(queryDocumentSnapshots.getDocuments().get(0).getReference());
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(GetAccessActivity.this, "Code isnt correct", Toast.LENGTH_SHORT).show();
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