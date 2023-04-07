package com.example.nobleevondeur.MiseAJourFragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nobleevondeur.MiseAJourActivity;
import com.example.nobleevondeur.NonActivityClasses.ItemProduit;
import com.example.nobleevondeur.NonActivityClasses.Magazin;
import com.example.nobleevondeur.NonActivityClasses.ProduitAdapter;
import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ModifierFragment extends Fragment {

    View view;
    RecyclerView recyclerProduit;

    ProduitAdapter produitAdapter;
    List<ItemProduit> desProduits = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_modifier, container, false);

        setUpProduitRecycleView(view);

        return view;
    }

    private void setUpProduitRecycleView(View view) {
        recyclerProduit = view.findViewById(R.id.modifier_recycle_produit);
        produitAdapter = new ProduitAdapter(getContext(),desProduits);
        recyclerProduit.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerProduit.setAdapter(produitAdapter);
        Magazin.getInstance().getRef().collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : documents){
                            desProduits.add(
                                    new ItemProduit(
                                            document.getString("nom"),
                                            document.getString("prix"),
                                            document.getString("imageUrl")
                                    )
                            );
                        }
                        produitAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });;
    }
}