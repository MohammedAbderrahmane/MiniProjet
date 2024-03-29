package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    Button a;
    EditText nm,prm,eml,psw,cpsw;
    CheckBox accpt;
    TextView statue,toLoginPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        a=findViewById(R.id.register_btn);
        a.setEnabled(false);
        nm=findViewById(R.id.nom_ed);
        prm=findViewById(R.id.prenome_ed);
        eml=findViewById(R.id.email_ed);
        psw=findViewById(R.id.mtps_ed);
        cpsw=findViewById(R.id.cnfm_mtps_ed);
        accpt=findViewById(R.id.acpt_cndtion_chb);
        statue=findViewById(R.id.statue);
        toLoginPage=findViewById(R.id.alrdy_have_acont_text);


        nm.addTextChangedListener(registerWatcher);
        prm.addTextChangedListener(registerWatcher);
        eml.addTextChangedListener(registerWatcher);
        psw.addTextChangedListener(registerWatcher);
        cpsw.addTextChangedListener(registerWatcher);

        toLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(nm.getText().toString(),
                        prm.getText().toString(),
                        eml.getText().toString(),
                        psw.getText().toString()
                );
            }
        });
    }

    void register (String nom, String prenom, String email, String motpass){

        Map<String,String> itm = new HashMap<>();
        itm.put("nom",nom);
        itm.put("prenom",prenom);
        itm.put("email",email);

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email,motpass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DocumentReference refMalade = FirebaseFirestore.getInstance()
                                        .collection("Malades")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                refMalade.set(itm).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                finish();
                                                startActivity (new Intent(RegisterActivity.this, LoginActivity.class));
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(RegisterActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }else{
                                Toast.makeText(RegisterActivity.this,"Failed to register ",Toast.LENGTH_LONG).show();
                            }
                        }
                });

    }

    private TextWatcher registerWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //TODO add acept checkBOx
            if (nm.getText().toString().isEmpty() ||
                    prm.getText().toString().isEmpty() ||
                    eml.getText().toString().isEmpty() ||
                    psw.getText().toString().isEmpty() ||
                    cpsw.getText().toString().isEmpty()) {
                a.setEnabled(false);
                statue.setText("All fields must be filled");
                statue.setVisibility(View.VISIBLE);
            }else if (psw.getText().toString().length() < 7){
                a.setEnabled(false);
                statue.setText("password must be more then 7 letters");
                statue.setVisibility(View.VISIBLE);
            }else if (!psw.getText().toString().equals(cpsw.getText().toString())){
                a.setEnabled(false);
                statue.setText("Password must be identical");
                statue.setVisibility(View.VISIBLE);
            }else{
                a.setEnabled(true);
                statue.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}