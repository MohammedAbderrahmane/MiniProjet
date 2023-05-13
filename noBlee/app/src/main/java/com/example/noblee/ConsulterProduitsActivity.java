package com.example.noblee;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewMagazins.ItemMagazin;
import com.example.noblee.NonActivityClasses.RecycleViewMagazins.MagazinAdapter;
import com.example.noblee.NonActivityClasses.RecycleViewProduits.ItemProduit;
import com.example.noblee.NonActivityClasses.RecycleViewSearch.ChercheAdapter;
import com.example.noblee.NonActivityClasses.RecycleViewSearch.ItemSearchMagazin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsulterProduitsActivity extends AppCompatActivity {

    View chercheLayout;
    TextView aucuneResult;
    RecyclerView magazinsView,chercheRecycleView;
    SearchView chercheView;
    ImageButton goBack,cherche;
    List<ItemMagazin> magazins;
    List<ItemSearchMagazin> chercheResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_produits);

        magazinsView = findViewById(R.id.consulter_magazins_recycleview);
        chercheLayout = findViewById(R.id.consulter_recherche_layout);
        chercheView = findViewById(R.id.consulter_recherche);
        chercheRecycleView = findViewById(R.id.recherche_recycle_view);
        aucuneResult = findViewById(R.id.recherche_aucune_produit);

        goBack = findViewById(R.id.consulter_return);
        cherche = findViewById(R.id.consulter_search);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chercheLayout.getVisibility() == View.GONE){
                    chercheLayout.setVisibility(View.VISIBLE);
                    setUpChercheView();
                }else {
                    chercheLayout.setVisibility(View.GONE);
                }
            }
        });

        setUpMagazinRecycleView();

    }



    private void setUpChercheView() {
        chercheView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                chercheResult = new ArrayList<>();
                ChercheAdapter chercheAdapter = new ChercheAdapter(ConsulterProduitsActivity.this,chercheResult);
                setUpChercheRecycleView(chercheAdapter);
                filterChercheList(s,chercheAdapter);
                if (chercheResult.size() == 0){
                    aucuneResult.setVisibility(View.VISIBLE);
                    chercheRecycleView.setVisibility(View.GONE);
                }else{
                    aucuneResult.setVisibility(View.GONE);
                    chercheRecycleView.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    private void filterChercheList(String motKlee, ChercheAdapter chercheAdapter) {
        // TODO : load produitList avaant recherche
        // TODO : option par categorie

        if (motKlee.equals("")){
            return;
        }
        for (ItemMagazin magazin : ((MagazinAdapter) magazinsView.getAdapter()).getMagazins()){
            ItemSearchMagazin itemMagazin = new ItemSearchMagazin(magazin.getNom());
            for (ItemProduit produit : ((MagazinAdapter) magazinsView.getAdapter()).getProduits()){
                if (produit.getNom().toLowerCase().contains(motKlee.toLowerCase())){
                    itemMagazin.produits.add(produit);
                }
            }
            if (itemMagazin.produits.size() > 0){
                chercheResult.add(itemMagazin);
            }
        }
        chercheAdapter.notifyDataSetChanged();
    }

    private void setUpChercheRecycleView(ChercheAdapter chercheAdapter) {

        chercheRecycleView.setLayoutManager(new LinearLayoutManager(this));
        chercheRecycleView.setAdapter(chercheAdapter);
    }

    public void setUpMagazinRecycleView(){

        MagazinAdapter magazinAdapter = new MagazinAdapter(ConsulterProduitsActivity.this,magazins = new ArrayList<ItemMagazin>());
        magazinsView.setLayoutManager(new LinearLayoutManager(ConsulterProduitsActivity.this));
        magazinsView.setAdapter(magazinAdapter);


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
                                            document.getString("location"),
                                            document.getString("nom_vondeur"),
                                            document.getString("telephone"),
                                            document.getString("imageUrl"),
                                            document.getReference()
                                    )
                            );
                        }
                        magazinAdapter.notifyDataSetChanged();
                    }
                });

    }
}