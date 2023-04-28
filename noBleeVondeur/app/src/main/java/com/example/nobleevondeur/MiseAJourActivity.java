package com.example.nobleevondeur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nobleevondeur.MiseAJourFragments.AjouterFragment;
import com.example.nobleevondeur.MiseAJourFragments.ModifierFragment;

public class MiseAJourActivity extends AppCompatActivity {




    AppCompatButton ajouter,modifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mise_ajour);

        ajouter = findViewById(R.id.miseajour_ajouter);
        modifier = findViewById(R.id.miseajour_modifier);

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AjouterFragment());
            }
        });

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ModifierFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.miseajour_frame_layout,fragment)
                .commit();
    }
}