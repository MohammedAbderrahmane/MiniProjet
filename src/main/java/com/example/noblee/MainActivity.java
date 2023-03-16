package com.example.noblee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    ImageButton prfl;
    EditText recherche_ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        prfl = findViewById(R.id.profile);
        recherche_ed = findViewById(R.id.recherche_ed);


        prfl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });

    }


    //RECHERCHE
    TextWatcher recherche = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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
        public void afterTextChanged(Editable editable) {

        }
    };
}