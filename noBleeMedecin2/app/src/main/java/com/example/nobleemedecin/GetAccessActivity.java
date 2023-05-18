package com.example.nobleemedecin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import com.example.nobleemedecin.NonActivityClasses.LocalDataBase;
import com.google.android.gms.tasks.OnSuccessListener;
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

        if (LocalDataBase.getInstance(getApplicationContext()).medecinExist()) {
            LocalDataBase.getInstance(getApplicationContext()).setMedecinRefFromDataBase();
            finish();
            startActivity(new Intent(GetAccessActivity.this, MainActivity.class));
        }

        accee = findViewById(R.id.get_access_code);
        accept = findViewById(R.id.get_access_accept);
        rememberMe = findViewById(R.id.get_access_remember_me);
        progressBar = findViewById(R.id.get_access_progress_bar);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCodeAccee(accee.getText().toString(),rememberMe.isActivated());
            }


        });
    }
    private void verifyCodeAccee(String code, boolean activated) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance()
                .collection("Medecin")
                .whereEqualTo("code",code)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().isEmpty()){
                            Toast.makeText(GetAccessActivity.this, "code erreur", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }else{
                            LocalDataBase.getInstance(getApplicationContext()).setMedecinRef(
                                    activated,
                                    queryDocumentSnapshots.getDocuments().get(0).getReference()
                            );
                            progressBar.setVisibility(View.GONE);
                            finish();
                            startActivity(new Intent(GetAccessActivity.this,MainActivity.class));
                        }
                    }
                });
    }

}