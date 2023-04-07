package com.example.noblee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewMagazins.ItemMagazin;
import com.example.noblee.NonActivityClasses.RecycleViewMagazins.MagazinAdapter;

import java.util.ArrayList;

public class ConsulterProduitsActivity extends AppCompatActivity {

    RecyclerView magazinsRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_produits);

        magazinsRecycleView = findViewById(R.id.consulter_magazins_recycleview);

        MagazinAdapter magazinAdapter = new MagazinAdapter(ConsulterProduitsActivity.this,new ArrayList<ItemMagazin>());
        magazinsRecycleView.setLayoutManager(new LinearLayoutManager(ConsulterProduitsActivity.this));
        magazinsRecycleView.setAdapter(magazinAdapter);
        magazinAdapter.getMagazinsOfFirestore();


    }
}