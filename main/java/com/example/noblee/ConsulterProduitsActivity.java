package com.example.noblee;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ProduitAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsulterProduitsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView statueRecycleView;
    ProduitAdapter produitAdapter;
    List<ItemProduit> desProduits = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_produits);


        statueRecycleView = findViewById(R.id.recycleView_statue);

        setUpProduitRecycleView();

    }

    private void setUpProduitRecycleView() {
        recyclerView = findViewById(R.id.recycleview);
        produitAdapter = new ProduitAdapter(ConsulterProduitsActivity.this,desProduits);
        recyclerView.setLayoutManager(new LinearLayoutManager(ConsulterProduitsActivity.this));
        recyclerView.setAdapter(produitAdapter);
        FirebaseFirestore.getInstance().collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot>documents = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : documents){
                            desProduits.add(
                                    new ItemProduit(
                                            document.getString("nom"),
                                            document.getString("prix"),
                                            document.getString("image")
                                    )
                            );
                        }
                        produitAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ConsulterProduitsActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });;
    }

    private void setUpImage(){

    }

}