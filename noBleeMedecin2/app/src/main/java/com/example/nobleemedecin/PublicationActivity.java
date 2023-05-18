package com.example.nobleemedecin;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.nobleemedecin.NonActivityClasses.Medecin;
import com.example.nobleemedecin.NonActivityClasses.PublicationKeyWords;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewPublication.ItemPublication;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewPublication.PublicationAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicationActivity extends AppCompatActivity {

    EditText newPublication;
    View creePublication;
    ImageButton ajouterNewPublication;
    RecyclerView recyclerView;
    List<ItemPublication> publications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
try{
        newPublication = findViewById(R.id.publication_new_publication_edit_text);
        ajouterNewPublication = findViewById(R.id.publication_new_publication_ajouter);
        recyclerView = findViewById(R.id.publication_recycle_view);

        ajouterNewPublication.setEnabled(false);

        newPublication.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ajouterNewPublication.setEnabled(!newPublication.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });



        ajouterNewPublication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterPublication(newPublication.getText().toString().trim());
            }
        });

        setUpRecycleView();

    }catch(Exception e){
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}

    }

    private void ajouterPublication(String contenu) {
        FirebaseFirestore.getInstance()
                .collection(PublicationKeyWords.PUBLICATION)
                .add(
                        new ItemPublication(
                                Medecin.getInstance().getNom() + " " + Medecin.getInstance().getPrenom(),
                                contenu,
                                new Date(),
                                String.valueOf(0),
                                String.valueOf(0),
                                true
                        )
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        Toast.makeText(PublicationActivity.this, "Succe", Toast.LENGTH_SHORT).show();
                        newPublication.setText("");
                        ajouterNewPublication.setEnabled(false);
                    }
                });
    }

    void setUpRecycleView() {
        PublicationAdapter publicationAdapter = new PublicationAdapter(this, publications = new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(publicationAdapter);
        FirebaseFirestore.getInstance()
                .collection(PublicationKeyWords.PUBLICATION)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            ItemPublication publication = new ItemPublication(
                                    document.getString(PublicationKeyWords.PUB_AUTEUR),
                                    document.getString(PublicationKeyWords.PUB_CONTENU),
                                    document.getDate(PublicationKeyWords.PUB_DATE),
                                    document.getString(PublicationKeyWords.PUB_NUM_LIKE),
                                    document.getString(PublicationKeyWords.PUB_NUM_DISLIKE),
                                    // TODO : give for true Badge
                                    document.getBoolean(PublicationKeyWords.PUB_CREE_PAR_MEDECIN));
                            publication.setReference(document.getReference());
                            publications.add(publication);
                        }
                        publicationAdapter.notifyDataSetChanged();
                    }
                });


    }

}