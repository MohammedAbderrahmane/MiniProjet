package com.example.noblee.NonActivityClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.noblee.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDialog extends AppCompatDialogFragment {

    TextView statue;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_payment_statue,null);

        builder.setView(view)
                .setTitle("Votre donation :")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        statue = view.findViewById(R.id.dialog_payment_statue);

        try {
            // details = {idDePayment , stateDePayment}
            JSONObject details = new JSONObject(getArguments().getString("details"));
            statue.setText(details.getJSONObject("response").getString("state"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return builder.create();
    }
}
