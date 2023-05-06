package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    EditText nom,prenom,address;
    AppCompatButton modifier;
    ImageButton deconnecter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent goLogin = new Intent(this, LoginActivity.class);
            goLogin.putExtra("currentPage",LoginActivity.TO_PROFILE);
            finish();
            startActivity(goLogin);
            return;
        }

        nom = findViewById(R.id.profile_nom);
        prenom = findViewById(R.id.profile_prenom);
        address = findViewById(R.id.profile_adresse);
        modifier = findViewById(R.id.profile_modifier);
        deconnecter = findViewById(R.id.profile_deconnecter);




        nom.addTextChangedListener(watcher);
        prenom.addTextChangedListener(watcher);
        address.addTextChangedListener(watcher);

        modifier.setEnabled(false);
        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierInformations(nom.getText().toString(),prenom.getText().toString(),address.getText().toString());
            }
        });

        deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deconnecter();
            }
        });

        setInformations();

    }

    private void modifierInformations(String newNom, String newPrenom, String newAdresse) {
        Map<String,Object> update = new HashMap();
        update.put("nom", newNom);
        update.put("prenom",newPrenom);
        update.put("adresse", newAdresse);

        User.getInstance()
                .getReference()
                .update(update)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        nom.setText(newNom);
                        prenom.setText(newPrenom);
                        address.setText(newAdresse);
                        Toast.makeText(ProfileActivity.this, "Informations modifié", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deconnecter() {
        FirebaseAuth.getInstance().signOut();
        User.disconnect();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private void setInformations() {
        nom.setText(User.getInstance().getNom());
        prenom.setText(User.getInstance().getPrenom());
        address.setText(User.getInstance().getAdresse());
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            modifier.setEnabled(
                    !nom.getText().toString().equals(User.getInstance().getNom()) ||
                    !prenom.getText().toString().equals(User.getInstance().getPrenom()) ||
                    !address.getText().toString().equals(User.getInstance().getAdresse())
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}