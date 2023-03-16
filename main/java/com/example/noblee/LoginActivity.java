package com.example.noblee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText login_email,login_motpass;
    TextView toRegisterPage,statue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn=findViewById(R.id.login_btn);
        login_email=findViewById(R.id.login_email_ed);
        login_motpass=findViewById(R.id.login_motpass_ed);
        toRegisterPage=findViewById(R.id.to_registerActivity);
        statue=findViewById(R.id.login_statue);

        login_btn.setEnabled(false);
        login_email.addTextChangedListener(loginWatcher);
        login_motpass.addTextChangedListener(loginWatcher);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(login_email.getText().toString(),login_motpass.getText().toString());
            }
        });

        toRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    void login (String email,String motpasse){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,motpasse)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        finish();
                        startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
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
            if (login_email.getText().toString().isEmpty()||
                    login_motpass.getText().toString().isEmpty()){
                login_btn.setEnabled(false);
                statue.setText("All fields must be filled");
                statue.setVisibility(View.VISIBLE);
            }else {
                login_btn.setEnabled(true);
                statue.setVisibility(View.INVISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };
}