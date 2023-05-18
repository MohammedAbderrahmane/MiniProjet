package com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleemedecin.R;

public class MessagrieHolder extends RecyclerView.ViewHolder {

    TextView malade;
    Button check;

    public MessagrieHolder(@NonNull View itemView) {
        super(itemView);

        malade = itemView.findViewById(R.id.messagrie_malade);
        check = itemView.findViewById(R.id.messagrie_check);
    }
}
