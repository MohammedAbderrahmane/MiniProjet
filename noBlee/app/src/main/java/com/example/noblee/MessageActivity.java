package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.Messagrie;
import com.example.noblee.NonActivityClasses.RecycleViewMessage.ItemMessage;
import com.example.noblee.NonActivityClasses.RecycleViewMessage.MessageAdapter;
import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class MessageActivity extends AppCompatActivity {
    
    View lancerLayout,messagesLayout;
    Button lancer,send;
    EditText message;
    RecyclerView messagesRecycleView;

    Messagrie messagrie;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent goLogin = new Intent(this, LoginActivity.class);
            goLogin.putExtra("currentPage",LoginActivity.TO_MESSAGE);
            finish();
            startActivity(goLogin);
            return;
        }

        lancerLayout = findViewById(R.id.message_lancer_layout);
        messagesLayout = findViewById(R.id.message_messages_layout);
        lancer = findViewById(R.id.message_lancer);
        send = findViewById(R.id.message_send);
        message = findViewById(R.id.message_edit_text);
        messagesRecycleView = findViewById(R.id.message_recycle_view);
        
        setUpActivity();
    }

    private void setUpActivity() {
        FirebaseFirestore.getInstance().collection("Messagrie")
                .whereEqualTo("maladePath", User.getInstance().getReference().getPath())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().size() == 0){
                            lancerLayout.setVisibility(View.VISIBLE);
                            messagesLayout.setVisibility(View.GONE);

                            lancer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    lancerCommunication();
                                }
                            });
                        }else{
                            lancerLayout.setVisibility(View.GONE);
                            messagesLayout.setVisibility(View.VISIBLE);

                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(0);
                            messagrie = new Messagrie(
                                    document.getString("maladePath"),
                                    document.getString("medecinPath"),
                                    document.getString("maladeNomPrenom"),
                                    document.getString("medecinNomPrenom"),
                                    document.getReference()
                            );

                            send.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    sendMessage(message.getText().toString());
                                }
                            });

                            setUpRecycleView();
                        }

                    }
                });
    }

    private void setUpRecycleView() {
        MessageAdapter messageAdapter = new MessageAdapter(this,messagrie.getMessages());
        messagesRecycleView.setLayoutManager(new LinearLayoutManager(this));
        messagesRecycleView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();

        messagrie.getReference()
                .collection("Messages")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(MessageActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange documentChange : value.getDocumentChanges()){
                            if (documentChange.getType() == DocumentChange.Type.ADDED){
                                DocumentSnapshot document = documentChange.getDocument();
                                messagrie.getMessages().add(
                                        new ItemMessage(
                                                document.getString("contenu"),
                                                document.getBoolean("sentByMalade"),
                                                document.getTimestamp("date")
                                        )
                                );
                            }
                        }
                    }
                });
    }

    private void sendMessage(String contenu){
        messagrie.getReference().collection("Messages")
                .add(
                        new ItemMessage(
                                contenu,
                                true,
                                new Timestamp(new Date())
                        )

                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        message.setText("");
                        messagesRecycleView.getAdapter().notifyDataSetChanged();
                    }
                });
    }

    private void lancerCommunication(){
        final String[] medecinPathChoisi = new String[1];
        final String[] medecinNomEtPrenom = new String[1];
        final DocumentReference[] medecinRef = new DocumentReference[1];
        final int[] count = {0};

        lancer.setEnabled(false);
        FirebaseFirestore.getInstance().collection("Medecin")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            int currentCount;
                            if (document.contains("numMessagrie")) {
                                currentCount = Integer.parseInt(document.getString("numMessagrie"));
                            } else {
                                currentCount = 0;
                            }

                            if (currentCount >= count[0]) {
                                medecinRef[0] = document.getReference();
                                count[0] = currentCount;
                                medecinNomEtPrenom[0] = document.getString("nom") + " " + document.getString("prenom");
                                medecinPathChoisi[0] = document.getReference().getPath();
                            }
                        }
                            FirebaseFirestore.getInstance().collection("Messagrie")
                                    .add(
                                            new Messagrie(
                                                    User.getInstance().getReference().getPath(),
                                                    medecinPathChoisi[0],
                                                    User.getInstance().getNom() + " " + User.getInstance().getPrenom(),
                                                    medecinNomEtPrenom[0]
                                                    )
                                    )
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference reference) {
                                            medecinRef[0].update("numMessagrie",String.valueOf(count[0]+1));


                                            messagrie = new Messagrie(
                                                    User.getInstance().getReference().getPath(),
                                                    medecinPathChoisi[0],
                                                    User.getInstance().getNom() + " " + User.getInstance().getPrenom(),
                                                    medecinNomEtPrenom[0],
                                                    reference
                                            );
                                            setUpActivity();
                                        }
                                    });

                    }
                });



    }
}