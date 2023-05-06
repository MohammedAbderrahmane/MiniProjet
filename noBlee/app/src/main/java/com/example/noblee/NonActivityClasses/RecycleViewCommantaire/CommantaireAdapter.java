package com.example.noblee.NonActivityClasses.RecycleViewCommantaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.User;
import com.example.noblee.R;
import com.google.firebase.firestore.DocumentReference;

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

        setUpModirateur(holder);

        holder.modirateurSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCommantaire(commantaire.reference,position);
            }
        });

    }

    private void setUpModirateur(CommantaireHolder holder) {
        if (User.getInstance().isMod()){
            holder.modirateurSupprimer.setVisibility(View.VISIBLE);
            return;
        }
        holder.modirateurSupprimer.setVisibility(View.INVISIBLE);
    }

    private void deleteCommantaire(DocumentReference reference, int position) {
        commantaires.remove(position);
        notifyDataSetChanged();
        reference.delete();
    }


    @Override
    public int getItemCount() {
        return commantaires.size();
    }
}
