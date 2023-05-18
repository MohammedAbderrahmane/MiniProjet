package com.example.nobleemedecin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nobleemedecin.NonActivityClasses.Medecin;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie.ItemMessagrie;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie.MessagrieAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MessagrieActivity extends AppCompatActivity {

    RecyclerView mMessageRecyclerView;

    List<ItemMessagrie> messagries ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messagrie);

        mMessageRecyclerView = findViewById(R.id.messagrie_recycle_view);


        setUpRecycleView();

    }

    void setUpRecycleView() {
        MessagrieAdapter messagrieAdapter = new MessagrieAdapter(this, messagries = new ArrayList<>());
        mMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecyclerView.setAdapter(messagrieAdapter);

        FirebaseFirestore.getInstance()
                .collection("Messagrie")
                .whereEqualTo("medecinPath", Medecin.getInstance().getReference().getPath())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            messagries.add(
                                    new ItemMessagrie(
                                            document.getString("maladePath"),
                                            document.getString("medecinPath"),
                                            document.getString("maladeNomPrenom"),
                                            document.getString("medecinNomPrenom"),
                                            document.getReference()
                                    )
                            );
                        }
                        messagrieAdapter.notifyDataSetChanged();
                    }
                });
    }
}