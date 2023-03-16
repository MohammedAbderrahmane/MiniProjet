package com.example.noblee.NonActivityClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.noblee.R;


public class ModifierMotpasseDialoge extends AppCompatDialogFragment {

    private boolean canOK = false;
    private EditText enterPsw,enterCnfrmPsw;
    private TextView statue;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modifier_motpasse,null);

        builder.setView(view)
                .setTitle("Modifier Motpasse")
                .setPositiveButton("accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (canOK){
                            //TODO : modifier motpasse user.updatePassword(String a);
                            Toast.makeText(getActivity().getApplicationContext(), "TODO", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity().getApplicationContext(), "Can't modifier motpasse", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        enterPsw = view.findViewById(R.id.dialog_motpasse_motpasse);
        enterCnfrmPsw = view.findViewById(R.id.dialog_motpasse_confirm_motpasse_ed);
        statue = view.findViewById(R.id.dialog_motpasse_statue);

        enterPsw.addTextChangedListener(modifierMotpassWatcher);
        enterCnfrmPsw.addTextChangedListener(modifierMotpassWatcher);

        return builder.create();
    }

    private TextWatcher modifierMotpassWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (enterPsw.getText().toString().isEmpty()
                ||enterCnfrmPsw.getText().toString().isEmpty()){
                statue.setText("Must be filled");
                statue.setVisibility(View.VISIBLE);
                canOK = false;
                
            }else if (!enterPsw.getText().toString().equals(enterCnfrmPsw.getText().toString())){
                statue.setText("Must be identical");
                statue.setVisibility(View.VISIBLE);
                canOK = false;
            }else {
                canOK = true;
                statue.setVisibility(View.GONE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    };
}
