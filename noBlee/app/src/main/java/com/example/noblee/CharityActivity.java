package com.example.noblee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.noblee.NonActivityClasses.PaymentDialog;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class CharityActivity extends AppCompatActivity {

    private PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AYOrF9Rjc4ntL1PRIsrJDAHslQeRtRXVc2kCLWd8QloKOplFpcKo6nVcnK2uCaQiaKGnBjkxMTmzSsNw");

    EditText sommeArgent;
    Button paymentBtn;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this , PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity);

        // start PayPal Service :
        Intent intent = new Intent(this , PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
        startService(intent);

        sommeArgent = findViewById(R.id.charity_somme_argent);
        paymentBtn = findViewById(R.id.charity_payment);

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proccessPayment(sommeArgent.getText().toString());
            }
        });

    }

    private void proccessPayment(String somme) {
        PayPalPayment payment = new PayPalPayment
                (
                        new BigDecimal(String.valueOf(somme)),
                        "DA",
                        "Donate for gluten free product",
                        PayPalPayment.PAYMENT_INTENT_SALE
                );


        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION , config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT , payment);

        startActivityForResult(intent , 1299);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1299) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {

                    Bundle argsOfDialog = new Bundle();
                    try {
                        String details = confirmation.toJSONObject().toString(4);
                        argsOfDialog.putString("details", details);
                        argsOfDialog.putString("somme", sommeArgent.getText().toString());
                        showPaymentDoneDialog(argsOfDialog);
                    } catch (JSONException e) {
                        Toast.makeText(this, "error creating dialog", Toast.LENGTH_SHORT).show();
                    }


                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "invalide", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPaymentDoneDialog(Bundle argsOfDialog) {

        PaymentDialog paymentDialog = new PaymentDialog();

        paymentDialog.setArguments(argsOfDialog);
        paymentDialog.show( ((AppCompatActivity) this ).getSupportFragmentManager(), "payment");
    }
}