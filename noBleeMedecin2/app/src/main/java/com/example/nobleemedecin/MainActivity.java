package com.example.nobleemedecin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nobleemedecin.NonActivityClasses.LocalDataBase;
import com.example.nobleemedecin.NonActivityClasses.Medecin;

public class MainActivity extends AppCompatActivity {

    Button Messagrie,Publication,profile;
    TextView bonjour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bonjour = findViewById(R.id.main_bonjour);
        Publication = findViewById(R.id.main_Publication);
        Messagrie = findViewById(R.id.main_Messagrie);
        profile = findViewById(R.id.main_profile);


        Medecin.createInstance();
        setUpBonjour();

        Publication.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,PublicationActivity.class)));

        Messagrie.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,MessagrieActivity.class)));

        profile.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ProfileActivity.class)));
    }


    void setUpBonjour (){
        LocalDataBase.getInstance(getApplicationContext()).getMedecinRef()
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (!documentSnapshot.contains("nom"))
                        bonjour.setText("Veuller modifier votre informations dans Profile!");
                    else
                        bonjour.setText("Bonjour " + documentSnapshot.getString("nom") + " !");
                });
    }
}