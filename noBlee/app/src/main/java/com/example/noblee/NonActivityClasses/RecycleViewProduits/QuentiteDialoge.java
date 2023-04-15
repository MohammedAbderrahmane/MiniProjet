package com.example.noblee.NonActivityClasses.RecycleViewProduits;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.noblee.NonActivityClasses.SavedPagniesDb;
import com.example.noblee.R;

public class QuentiteDialoge extends AppCompatDialogFragment {

    SeekBar seekBar;
    EditText quentite;
    TextView statue;
    boolean afterAPILevel26 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_modifier_quentite,null);

        builder.setView(view)
                .setTitle("Selectioner la quentite :")
                .setPositiveButton("ajouter au pagnie", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SavedPagniesDb.getInstance(getContext()).ajouterUnPagnie(
                                getArguments().getString("nom"),
                                getArguments().getString("prix"),
                                quentite.getText().toString().trim()
                        );
                        Toast.makeText(getContext(), "saved pagnie", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        seekBar = view.findViewById(R.id.dialog_quentite_seekbar);
        quentite = view.findViewById(R.id.dialog_quentite_quentite);
        statue = view.findViewById(R.id.dialog_quentite_statue);

        setSeekBar();
        quentite.addTextChangedListener(checkValideQuentite);

        return builder.create();
    }

    void setSeekBar (){
        seekBar.setMax(10);
        if (afterAPILevel26)
            seekBar.setMin(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!afterAPILevel26 && i==0){
                    quentite.setText("1");
                    return;
                }
                quentite.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    private TextWatcher checkValideQuentite = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (quentite.getText().toString().isEmpty()){
                statue.setText("donner une quentite entre 1 et 10");
                statue.setVisibility(View.VISIBLE);
            }else if (Integer.parseInt(quentite.getText().toString())<1 || Integer.parseInt(quentite.getText().toString())>10){
                statue.setText("Invalide quentite");
                statue.setVisibility(View.VISIBLE);
            }else
                statue.setVisibility(View.GONE);
        }
        @Override
        public void afterTextChanged(Editable editable) {}
    };
}
