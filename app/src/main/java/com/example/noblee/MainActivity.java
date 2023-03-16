package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noblee.NonActivityClasses.QuentiteDialoge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ImageButton prfl;
    Button consulterProduits,pageCommande;
    EditText recherche_ed;
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        consulterProduits = findViewById(R.id.main_consulter_produits);
        pageCommande = findViewById(R.id.main_page_commande);

        prfl = findViewById(R.id.main_to_profile);
        recherche_ed = findViewById(R.id.main_recherche);


        prfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                else
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                //finish();
            }
        });

        consulterProduits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ConsulterProduitsActivity.class));
            }
        });
        pageCommande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CommandeActivity.class));
            }
        });

        QuentiteDialoge selectinerQuentiteDialoge = new QuentiteDialoge();
    }


    //RECHERCHE
    TextWatcher recherche = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // TODO
            String motKle = recherche_ed.getText().toString();
            db.collection("Produits")
                    .whereEqualTo("nom",motKle)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                QuerySnapshot produitRecherche = task.getResult();
                                Toast.makeText(MainActivity.this, "Product found :"+produitRecherche, Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(MainActivity.this, "Error while shershing", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}