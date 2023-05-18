package com.example.nobleemedecin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nobleemedecin.NonActivityClasses.PassData;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessage.ItemMessage;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessage.MessageAdapter;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie.ItemMessagrie;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Date;

public class MessageActivity extends AppCompatActivity {

    EditText newMessage;
    Button send;
    RecyclerView messageRecycleView;
    ItemMessagrie messagrie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
try{
        messagrie = PassData.itemMessagrie;

        newMessage = findViewById(R.id.message_edit_text);
        send = findViewById(R.id.message_send);
        messageRecycleView = findViewById(R.id.message_recycle_view);

    setUpRecycleView();

        newMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!newMessage.getText().toString().isEmpty()){
                    send.setEnabled(true);
                }else{
                    send.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterUnMessage(newMessage.getText().toString());

            }
        });

    }catch(Exception e){Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();}


    }

    void setUpRecycleView(){
        MessageAdapter messageAdapter = new MessageAdapter(this,messagrie.getMessages());
        messageRecycleView.setLayoutManager(new LinearLayoutManager(this));
        messageRecycleView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

    private void ajouterUnMessage(String newMessage) {
        messagrie.getReference()
                .collection("Messages")
                .add(
                        new ItemMessage(newMessage,false,new Timestamp(new Date()))
                )
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        messagrie.getMessages().add(new ItemMessage(newMessage,false,new Timestamp(new Date())));
                        MessageActivity.this.newMessage.setText("");
                    }
                });
    }
}