package com.example.nobleemedecin.NonActivityClasses.RecycleViewMessengrie;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleemedecin.MessageActivity;
import com.example.nobleemedecin.MessagrieActivity;
import com.example.nobleemedecin.NonActivityClasses.PassData;
import com.example.nobleemedecin.R;

import java.io.Serializable;
import java.util.List;

public class MessagrieAdapter extends RecyclerView.Adapter<MessagrieHolder> {

    Context context;
    List<ItemMessagrie> messagries;
    Intent toMessagePage;


    public MessagrieAdapter(Context context, List<ItemMessagrie> messagries) {
        this.context = context;
        this.messagries = messagries;
    }

    @NonNull
    @Override
    public MessagrieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessagrieHolder(LayoutInflater.from(context).inflate(R.layout.layout_messagrie,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagrieHolder holder, int position) {
        ItemMessagrie messagrie = messagries.get(position);
try{
        holder.malade.setText("Avec : " + messagrie.getMaladeNomPrenom());

        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                toMessagePage = new Intent((MessagrieActivity)context, MessageActivity.class);
                PassData.itemMessagrie = messagrie;
                ((MessagrieActivity) context).startActivity(toMessagePage);
            }catch(Exception e){Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
            }
        });
    }catch(Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
    }

    @Override
    public int getItemCount() {
        return messagries.size();
    }
}
