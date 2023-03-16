package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.RecycleViewPagnie.PagnieAdapter;
import com.example.noblee.NonActivityClasses.SavedPagniesDb;

public class CommandeActivity extends AppCompatActivity {

    RecyclerView a;
    Button confirme,annuler;
    ImageButton ajouter;
    TextView decripption;
    PagnieAdapter pagnieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);

        SavedPagniesDb.getInstance(getApplicationContext()).setUpListPagnie();

        a = findViewById(R.id.commande_pagnies);
        confirme = findViewById(R.id.commande_confirme);
        annuler = findViewById(R.id.commande_annuler);
        ajouter = findViewById(R.id.commande_ajouter);
        decripption = findViewById(R.id.commande_description);


        pagnieAdapter = new PagnieAdapter(getApplicationContext());
        a.setLayoutManager(new LinearLayoutManager(this));
        a.setAdapter(pagnieAdapter);


        confirme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavedPagniesDb.getInstance(getApplicationContext()).viderPagnie();
                Toast.makeText(CommandeActivity.this, "pagnie est videe", Toast.LENGTH_SHORT).show();
                pagnieAdapter.notifyDataSetChanged();
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),ConsulterProduitsActivity.class));

            }
        });


    }
}