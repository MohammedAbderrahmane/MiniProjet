package com.example.noblee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.api.BraintreeFragment;

public class CharityActivity extends AppCompatActivity {

    EditText sommeArgent;
    Button paymentBtn;


    BraintreeFragment mBraintreeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity);

        sommeArgent = findViewById(R.id.charity_somme_argent);
        paymentBtn  = findViewById(R.id.charity_payment);


        String mAuthorization = "YOUR_CLIENT_TOKEN_FROM_SERVER";
        //mBraintreeFragment = BraintreeFragment.newInstance(this, mAuthorization);


    }
}