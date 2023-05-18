package com.example.noblee.NonActivityClasses.RecycleViewMessage;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;


public class MessageHolder extends RecyclerView.ViewHolder {

    TextView date,contenu;
    public MessageHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.message_date);
        contenu = itemView.findViewById(R.id.message_contenu);
    }
}
