package com.example.noblee;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.Messagrie;
import com.example.noblee.NonActivityClasses.RecycleViewMessage.ItemMessage;
import com.example.noblee.NonActivityClasses.RecycleViewMessage.MessageAdapter;
import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

        lancer.setEnabled(false);
        FirebaseFirestore.getInstance().collection("Medecin")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int count = 0;
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            int currentCount;
                            if (document.contains("messagrieCount")){
                                currentCount = Integer.parseInt(document.getString("messagrieCount"));
                            }else {
                                currentCount = 0;
                            }

                            if (currentCount >= count){
                                count = currentCount;
                                medecinNomEtPrenom[0] = document.getString("nom") + " " + document.getString("prenom");
                                medecinPathChoisi[0] = document.getReference().getPath();
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
                    }
                });



    }
}