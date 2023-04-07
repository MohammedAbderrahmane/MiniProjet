package com.example.noblee.NonActivityClasses.RecycleViewMagazins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ProduitAdapter;
import com.example.noblee.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MagazinAdapter extends RecyclerView.Adapter<MagazinHolder>{

    Context context;
    public List<ItemMagazin> magazins;

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

        holder.nom.setText(magazin.getNom());
        holder.nomVondeur.setText(magazin.getNomVondeur());
        holder.telephone.setText(magazin.getTelephone());

        ProduitAdapter produitAdapter = new ProduitAdapter(context, new ArrayList<ItemProduit>());
        holder.produitsRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.produitsRecycleView.setAdapter(produitAdapter);
        produitAdapter.getProduitsOfFirestore(magazin.getMagazinReference());

    }

    @Override
    public int getItemCount() {
        return magazins.size();
    }

    public void getMagazinsOfFirestore(){
        try {
            FirebaseFirestore.getInstance()
                    .collection("Magazin")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot document : documents){
                                magazins.add(
                                        new ItemMagazin(
                                                document.getString("nom"),
                                                document.getString("nom_vondeur"),
                                                document.getString("telephone"),
                                                document.getReference()
                                        )
                                );
                            }
                            notifyDataSetChanged();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            Toast.makeText(context,e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
