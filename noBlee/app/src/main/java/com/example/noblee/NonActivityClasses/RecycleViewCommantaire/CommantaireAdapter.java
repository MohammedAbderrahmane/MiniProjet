package com.example.noblee.NonActivityClasses.RecycleViewCommantaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

import java.util.List;

public class CommantaireAdapter extends RecyclerView.Adapter<CommantaireHolder> {
    private Context context;
    private List<ItemCommantaire> commantaires;

    public CommantaireAdapter(Context context, List<ItemCommantaire> commantaires) {
        this.context = context;
        this.commantaires = commantaires;
    }

    @NonNull
    @Override
    public CommantaireHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommantaireHolder(LayoutInflater.from(context).inflate(R.layout.layout_commantaire,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommantaireHolder holder, int position) {
        ItemCommantaire commantaire = commantaires.get(position);

        holder.user.setText(commantaire.getUser());
        holder.contenu.setText(commantaire.getContenu());

    }


    @Override
    public int getItemCount() {
        return commantaires.size();
    }
}
