package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.QuentiteDialoge;
import com.example.noblee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProduitAdapter extends RecyclerView.Adapter<ProduitHolder> {

    private Context context;
    private List<ItemProduit> produits;


    public ProduitAdapter(Context context, List<ItemProduit> produits) {
        this.context = context;
        this.produits = produits;
    }

    public ProduitAdapter() {
    }

    public void add_produit(ItemProduit i){
        produits.add(i);
    }

    @NonNull
    @Override
    public ProduitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProduitHolder(LayoutInflater.from(context).inflate(R.layout.layout_produit,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitHolder holder, int position) {

        ItemProduit produit = produits.get(position);

        holder.nomProduit.setText(produit.getNom());
        holder.prixProduit.setText(produit.getPrix());
        // TODO : fix exeption image (firebase - object doesnt exist at location)
        setUpImage(position,holder);
        // TODO ajouter aggnie (quentite)
        QuentiteDialoge quentiteDialoge = new QuentiteDialoge();
        setUpAjouterAuPagnie(holder,quentiteDialoge,position);
    }
    private void setUpAjouterAuPagnie(ProduitHolder holder, QuentiteDialoge quentiteDialoge, int position){
        Bundle argsOfDialog = new Bundle();
        holder.ajouterAuPagnie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                argsOfDialog.putString("nom",produits.get(position).getNom());
                argsOfDialog.putString("prix",produits.get(position).getPrix());
                quentiteDialoge.setArguments(argsOfDialog);
                quentiteDialoge.show(((AppCompatActivity) context).getSupportFragmentManager(), "quentite");
            }
        });
    }
    private void setUpImage(int position,ProduitHolder holder) {
        if (produits!=null && produits.size()!=0){
            try {
                File localFile = File.createTempFile("tmp", "jpg");
                FirebaseStorage.getInstance().getReference("ProduitsImages" + produits.get(position).getImage())
                        .getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                holder.imageProduit.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                                //notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                                holder.imageProduit.setImageResource(R.drawable.exemple_image_produit);
                                //notifyDataSetChanged();
                            }
                        });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public int getItemCount() {
        return produits.size();
    }

}
