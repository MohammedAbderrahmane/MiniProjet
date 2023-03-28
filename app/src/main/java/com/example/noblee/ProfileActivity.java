package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noblee.NonActivityClasses.ModifierMotpasseDialoge;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {
final String ERROR_FETCH = "Error";
    FirebaseUser user;
    TextView nom,prenom,email;
    Button modifierMotpass_btn,deconnecter_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        nom=findViewById(R.id.profile_nom2);
        prenom=findViewById(R.id.profile_prenom2);
        email=findViewById(R.id.profile_email2);
        modifierMotpass_btn=findViewById(R.id.profile_modifierMotpasse_btn);
        deconnecter_btn=findViewById(R.id.profile_deconnecter_btn);


        setInformations();

        modifierMotpass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifierMotpass();
            }
        });
        deconnecter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

    }

    private void modifierMotpass() {
        ModifierMotpasseDialoge modifierMotpasseDialoge = new ModifierMotpasseDialoge();
        modifierMotpasseDialoge.show(getSupportFragmentManager(),"modifierMotpasseDialoge");
    }

    private void setInformations() {
        FirebaseFirestore.getInstance()
                .collection("Malades")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                nom.setText(documentSnapshot.getString("nom"));
                prenom.setText(documentSnapshot.getString("prenom"));
                email.setText(documentSnapshot.getString("email"));
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(getIntent());
                    }
                });

    }
}