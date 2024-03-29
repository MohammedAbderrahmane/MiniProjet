package com.example.noblee;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.noblee.NonActivityClasses.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public static final int TO_MAIN = -1;
    public static final int TO_PROFILE = 0;
    public static final int TO_COMMANDE = 1;
    public static final int TO_PUBLICATION = 2;
    public static final int TO_CONSULTER = 3;
    public static final int TO_MESSAGE = 4;

    AppCompatButton login;
    EditText email,motpass;
    TextView toRegisterPage,statue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=findViewById(R.id.login_btn);
        email=findViewById(R.id.login_email_ed);
        motpass=findViewById(R.id.login_motpass_ed);
        toRegisterPage=findViewById(R.id.to_registerActivity);
        statue=findViewById(R.id.login_statue);

        login.setEnabled(false);
        email.addTextChangedListener(loginWatcher);
        motpass.addTextChangedListener(loginWatcher);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(email.getText().toString(),motpass.getText().toString());
            }
        });

        toRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    void login (String email,String motpasse){
        int currentPage = getIntent().getIntExtra("currentPage",TO_MAIN);
        Intent goBack;

        switch (currentPage){
            case TO_PROFILE:
                goBack = new Intent(LoginActivity.this,ProfileActivity.class);
                break;
            case TO_COMMANDE:
                goBack = new Intent(LoginActivity.this,CommandeActivity.class);
                break;
            case TO_PUBLICATION:
                goBack = new Intent(LoginActivity.this,PublicationActivity.class);
                break;
            case TO_CONSULTER:
                goBack = new Intent(LoginActivity.this, ConsulterActivity.class);
                break;
            case TO_MESSAGE:
                goBack = new Intent(LoginActivity.this, MessageActivity.class);
                break;
            default:
                goBack = new Intent(LoginActivity.this, MainActivity.class);
        }

        Intent finalGoBack = goBack;
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,motpasse)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User.createInstance();
                        finish();
                        startActivity(finalGoBack);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this,"failed to login",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private TextWatcher loginWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (email.getText().toString().isEmpty()|| motpass.getText().toString().isEmpty()){
                login.setEnabled(false);
                statue.setText("All fields must be filled");
                statue.setVisibility(View.VISIBLE);
            }else {
                login.setEnabled(true);
                statue.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };
}