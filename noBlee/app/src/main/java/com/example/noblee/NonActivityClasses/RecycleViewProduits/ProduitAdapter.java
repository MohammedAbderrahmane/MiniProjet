package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noblee.NonActivityClasses.SavedPagniesDb;
import com.example.noblee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitHolder> {

    private Context context;
    private List<ItemProduit> produits;
    private DocumentReference magazinRef;


    public ProduitAdapter(Context context, List<ItemProduit> produits) {
        this.context = context;
        this.produits = produits;
    }

    @NonNull
    @Override
    public ProduitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProduitHolder(LayoutInflater.from(context).inflate(R.layout.layout_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder, int position) {
        ItemProduit produit = produits.get(position);
        // nom de produit et prix
        holder.nomProduit.setText(produit.getNom());
        holder.prixProduit.setText(produit.getPrix());
        // image
        setUpImage(produit.getImageUrl(),holder);
        // dialog
        setUpAjouterAuPagnie(holder,position);
    }

    private void setUpImage(String imageUrl, ProduitHolder holder) {
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageProduit);
    }

    private void setUpAjouterAuPagnie(ProduitHolder holder, int position){
        Bundle argsOfDialog = new Bundle();
        QuentiteDialoge quentiteDialoge = new QuentiteDialoge();

        holder.ajouterAuPagnie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SavedPagniesDb.getInstance(context).checkMemeMagazin(magazinRef)) {
                    argsOfDialog.putString("nom", produits.get(position).getNom());
                    argsOfDialog.putString("prix", produits.get(position).getPrix());
                    quentiteDialoge.setArguments(argsOfDialog);
                    quentiteDialoge.show(((AppCompatActivity) context).getSupportFragmentManager(), "quentite");
                }else{
                    Toast.makeText(context, "You must select products from one Shop", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getProduitsOfFirestore(DocumentReference magazinReference){
        magazinRef = magazinReference;
        magazinReference.collection("Produits")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            produits.add(
                                    new ItemProduit(
                                            documentSnapshot.getString("nom"),
                                            documentSnapshot.getString("prix"),
                                            documentSnapshot.getString("imageUrl")
                                    )
                            );
                        }
                        notifyDataSetChanged();
                        Toast.makeText(context,"azaza", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

}
