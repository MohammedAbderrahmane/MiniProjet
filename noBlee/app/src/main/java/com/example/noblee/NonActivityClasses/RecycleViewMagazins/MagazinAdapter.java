package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ProduitAdapter;
import com.example.noblee.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MagazinAdapter extends RecyclerView.Adapter<MagazinHolder>{

    Context context;
    List<ItemMagazin> magazins;
    List<ItemProduit> produits;

    public MagazinAdapter(Context context, List<ItemMagazin> magazins) {
        this.context = context;
        this.magazins = magazins;
    }

    @NonNull
    @Override
    public MagazinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MagazinHolder(LayoutInflater.from(context).inflate(R.layout.layout_magazin,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MagazinHolder holder, int position) {
        ItemMagazin magazin = magazins.get(position);

        setUpImage(holder,magazin.getImageUrl());
        holder.nom.setText(         "Magazin : " + magazin.getNom());
        holder.location.setText(    "Location : " + magazin.getLocation());
        holder.nomVendeur.setText(  "nom de vendeur : " + magazin.getNomVondeur());
        holder.telephone.setText(   "Numero telephone : " + magazin.getTelephone());

        holder.consulter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageRecycleView(holder.produitsRecycleView.getVisibility(),holder,magazin);
            }
        });
    }

    private void manageRecycleView(int visibility, MagazinHolder holder, ItemMagazin magazin) {
        if (visibility == View.VISIBLE){
            holder.produitsRecycleView.setVisibility(View.GONE);
            holder.consulter.setText("Consulter les produits");
            return;
        }
        holder.produitsRecycleView.setVisibility(View.VISIBLE);
        holder.consulter.setText("Casher les produits");
        setUpRecycleView(holder,magazin.getReference());
    }

    public void setUpRecycleView(MagazinHolder holder, DocumentReference reference) {
        ProduitAdapter produitAdapter = new ProduitAdapter(context,produits = new ArrayList<ItemProduit>());
        produitAdapter.setMagazinRef(reference);
        holder.produitsRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.produitsRecycleView.setAdapter(produitAdapter);


        reference.collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            produits.add(
                                    new ItemProduit(
                                            documentSnapshot.getString("nom"),
                                            documentSnapshot.getString("prix"),
                                            documentSnapshot.getString("imageUrl"),
                                            documentSnapshot.getString("categorie")
                                    )
                            );
                        }
                        produitAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void setUpImage(MagazinHolder holder,String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return magazins.size();
    }

    public List<ItemMagazin> getMagazins() {
        return magazins;
    }

    public List<ItemProduit> getProduits() {
        return produits;
    }

    public void fillProduitsList(DocumentReference reference){
        reference.collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            produits.add(
                                    new ItemProduit(
                                            documentSnapshot.getString("nom"),
                                            documentSnapshot.getString("prix"),
                                            documentSnapshot.getString("imageUrl"),
                                            documentSnapshot.getString("categorie")
                                    )
                            );
                        }
                    }
                });
    }


}
