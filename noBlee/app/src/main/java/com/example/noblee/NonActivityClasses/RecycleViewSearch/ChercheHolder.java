package com.example.noblee.NonActivityClasses.RecycleViewSearch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

public class ChercheHolder extends RecyclerView.ViewHolder {

    TextView magazin;
    RecyclerView resultRecycleView;

    public ChercheHolder(@NonNull View itemView) {
        super(itemView);

        magazin = itemView.findViewById(R.id.search_item_magazin);
        resultRecycleView = itemView.findViewById(R.id.search_item_produits);
    }
}
