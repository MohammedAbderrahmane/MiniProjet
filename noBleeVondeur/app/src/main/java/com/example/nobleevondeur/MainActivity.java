package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    AppCompatButton miseAJour,consulter,profile;

    TextView bonjour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        try{
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            Toolbar menu;
            menu = findViewById(R.id.menu);
            setSupportActionBar(menu);
            TextView pageNom = menu.findViewById(R.id.menu_page_name);
            pageNom.setText("Page d'acceill");
            //getSupportActionBar().setDisplayShowTitleEnabled(false);
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        */
        miseAJour = findViewById(R.id.main_mise_a_jour);
        consulter = findViewById(R.id.main_consulter);
        profile = findViewById(R.id.main_profile);
        bonjour = findViewById(R.id.main_bonjour);

        setUpBonjour();

        miseAJour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MiseAJourActivity.class));
            }
        });

        consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ConsulterCommandeActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MagazinActivity.class));
            }
        });
    }

    void setUpBonjour (){
        DataBase.getInstance(MainActivity.this).getMagazinRef()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.contains("nom_vondeur"))
                            bonjour.setText("Veuller modifier votre informations dans Profile!");
                        else
                            bonjour.setText("Bonjour " + documentSnapshot.getString("nom_vondeur") + "!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}