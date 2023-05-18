package com.example.nobleemedecin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nobleemedecin.NonActivityClasses.LocalDataBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Button modifier;
    ImageButton logout;
    EditText nom,prenom,numTelephone,adress;
    Map<String,String> MedecinData;
    ProgressBar progressBar;
    boolean nomChanged=false,prenomChanged=false,numTelephoneChanged=false,adresschanged=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nom = findViewById(R.id.medecin_nom);
        prenom = findViewById(R.id.prenom_medecin);
        numTelephone = findViewById(R.id.num_telephone);
        adress=findViewById(R.id.adress_medecin);
        modifier = findViewById(R.id.modifier_information);
        logout = findViewById(R.id.medecin_logout);
        progressBar = findViewById(R.id.medecin_progress_bar);

        setUpInfo();

        nom.addTextChangedListener(textWatcher);
        numTelephone.addTextChangedListener(textWatcher);
        prenom.addTextChangedListener(textWatcher);
        adress.addTextChangedListener(textWatcher);

        modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierInformation(
                        nom.getText().toString(),
                        prenom.getText().toString(),
                        numTelephone.getText().toString(),
                        adress.getText().toString()

                );
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medecinLogout();
            }
        });
    }

    private void medecinLogout() {
        LocalDataBase.getInstance(getApplicationContext()).clearMedecin();
        // TODO : finsih main activity too
        finish();
        startActivity(new Intent(ProfileActivity.this,GetAccessActivity.class));
    }

    private void modifierInformation(String nom,String prenom,String numtelephone,String adress) {
        progressBar.setVisibility(View.VISIBLE);
        Map<String,Object> updates = new HashMap();
        updates.put("nom",nom);
        updates.put("prenom",prenom);
        updates.put("numtelephone",numtelephone);
        updates.put("adress",adress);

        LocalDataBase.getInstance(getApplicationContext()).getMedecinRef()
                .update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "les information a ete modifiée", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setUpInfo() {
        // TODO : replace in MEDECin claas
        LocalDataBase.getInstance(getApplicationContext()).getMedecinRef()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        MedecinData = new HashMap<>();
                        MedecinData.put("nom",documentSnapshot.getString("nom"));
                        MedecinData.put("prenom",documentSnapshot.getString("prenom"));
                        MedecinData.put("telephone",documentSnapshot.getString("telephone"));
                        MedecinData.put("adress",documentSnapshot.getString("adress"));

                        nom.setText(documentSnapshot.getString("nom"));
                        prenom.setText(documentSnapshot.getString("prenom"));
                        numTelephone.setText(documentSnapshot.getString("telephone"));
                        adress.setText(documentSnapshot.getString("adress"));
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nomChanged = !nom.getText().toString().equals((String) MedecinData.get("nom"));
            prenomChanged = !prenom.getText().toString().equals((String)MedecinData.get("prenom"));
            numTelephoneChanged = !numTelephone.getText().toString().equals((String)MedecinData.get("telephone"));
            adresschanged = !adress.getText().toString().equals((String)MedecinData.get("adress"));

            if (prenomChanged||numTelephoneChanged||nomChanged||adresschanged)
                modifier.setVisibility(View.VISIBLE);
            else
                modifier.setVisibility(View.INVISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}