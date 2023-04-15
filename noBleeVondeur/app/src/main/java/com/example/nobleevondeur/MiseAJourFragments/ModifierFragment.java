package com.example.nobleevondeur.MiseAJourFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nobleevondeur.NonActivityClasses.ItemProduit;
import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.example.nobleevondeur.NonActivityClasses.ProduitAdapter;
import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ModifierFragment extends Fragment {

    View view,noProduits;
    RecyclerView recyclerProduit;

    ProduitAdapter produitAdapter;
    List<ItemProduit> produits = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_modifier, container, false);

        recyclerProduit = view.findViewById(R.id.modifier_recycle_produit);
        noProduits = view.findViewById(R.id.modifier_no_produits);

        setUpProduitRecycleView(view);
        return view;
    }

    private void setUpProduitRecycleView(View view) {
        produitAdapter = new ProduitAdapter(getContext(), produits);
        recyclerProduit.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerProduit.setAdapter(produitAdapter);
        DataBase.getInstance(getContext()).getMagazinRef()
                .collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : documents){
                            produits.add(
                                    new ItemProduit(
                                            document.getString("nom"),
                                            document.getString("prix"),
                                            document.getString("categorie"),
                                            document.getString("imageUrl")
                                    )
                            );
                        }
                        produitAdapter.notifyDataSetChanged();
                        if (produits.isEmpty()){
                            noProduits.setVisibility(View.VISIBLE);
                            recyclerProduit.setVisibility(View.GONE);
                        }else {
                            noProduits.setVisibility(View.GONE);
                            recyclerProduit.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}