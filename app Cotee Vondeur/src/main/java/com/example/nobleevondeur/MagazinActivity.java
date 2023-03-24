package com.example.nobleevondeur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nobleevondeur.NonActivityClasses.Magazin;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class MagazinActivity extends AppCompatActivity {

    Button modifier;
    EditText nomMagazin,nomVondeur,numTelephone;
    Map<String,String> MagazinData;
    boolean nomMagazinChanged=false,nomVondeurChanged=false,numTelephoneChanged=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazin);

            nomMagazin = findViewById(R.id.magazin_nom);
            nomVondeur = findViewById(R.id.magazin_nom_vondeur);
            numTelephone = findViewById(R.id.magazin_num_telephone);
            modifier = findViewById(R.id.magazin_modifier_information);

            setUpInfo();

            nomMagazin.addTextChangedListener(textWatcher);
            numTelephone.addTextChangedListener(textWatcher);
            nomVondeur.addTextChangedListener(textWatcher);

            modifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modifierInformation();
                }
            });


    }

    private void modifierInformation() {
        if (nomMagazinChanged){
            Magazin.getInstance().getRef()
                    .update("nom",nomMagazin.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MagazinActivity.this, "Seccus", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MagazinActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (nomVondeurChanged){
            Magazin.getInstance().getRef()
                    .update("nom_vondeur",nomVondeur.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MagazinActivity.this, "Seccus", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MagazinActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (numTelephoneChanged){
            Magazin.getInstance().getRef()
                    .update("telephone",numTelephone.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MagazinActivity.this, "Seccus", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MagazinActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setUpInfo() {
        Magazin.getInstance().getRef()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        MagazinData = new HashMap<>();
                        MagazinData.put("nom",documentSnapshot.getString("nom"));
                        MagazinData.put("nom_vondeur",documentSnapshot.getString("nom_vondeur"));
                        MagazinData.put("telephone",documentSnapshot.getString("telephone"));
                        nomMagazin.setText(documentSnapshot.getString("nom"));
                        nomVondeur.setText(documentSnapshot.getString("nom_vondeur"));
                        numTelephone.setText(documentSnapshot.getString("telephone"));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MagazinActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            nomMagazinChanged = !nomMagazin.getText().toString().equals((String) MagazinData.get("nom"));
            nomVondeurChanged = !nomVondeur.getText().toString().equals((String)MagazinData.get("nom_vondeur"));
            numTelephoneChanged = !numTelephone.getText().toString().equals((String)MagazinData.get("telephone"));

            if (nomVondeurChanged||numTelephoneChanged||nomMagazinChanged)
                modifier.setVisibility(View.VISIBLE);
            else
                modifier.setVisibility(View.INVISIBLE);

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}