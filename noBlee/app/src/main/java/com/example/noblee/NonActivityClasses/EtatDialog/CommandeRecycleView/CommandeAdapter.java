package com.example.noblee.NonActivityClasses.EtatDialog.CommandeRecycleView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.EtatDialog.LigneCommandeRecycleView.LigneCommandeAdapter;
import com.example.noblee.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeHolder>{

    Context context;
    List<ItemCommande> commandes;

    static private final String STATUE_ENVOYE = "envoye";
    static private final String STATUE_ACCEPTE = "accepte";
    static private final String STATUE_REFUSE = "refuse";

    public CommandeAdapter(Context context, List<ItemCommande> commandes) {
        this.context = context;
        this.commandes = commandes;
    }

    @NonNull
    @Override
    public CommandeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommandeHolder(LayoutInflater.from(context).inflate(R.layout.layout_commande,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommandeHolder holder, int position) {
        ItemCommande commande = commandes.get(position);

        try{
        holder.magazin.setText( "Du magazin : " + commande.getNomMagazin());
        // TODO : fix excepion
        holder.time.setText("Demmandé le " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(commande.getDate().toDate())));
        holder.prixTotal.setText("Le prix total : " + commande.getPrixTotal());


        setUpStatue(commande,holder);
        setUpRecycleView(holder,commande);

    }catch(Exception e){
        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}

    }

    private void setUpStatue(ItemCommande commande, CommandeHolder holder) {
        if (commande.getStatue().equals(STATUE_ENVOYE)){
            holder.statue.setTextColor(Color.parseColor("#FF9900"));
        }else if (commande.getStatue().equals(STATUE_ACCEPTE)){
            holder.statue.setTextColor(Color.parseColor("#00AA00"));
        }else{
            holder.statue.setTextColor(Color.parseColor("#AA0000"));
        }

        holder.statue.setText("La statue de commande : " + commande.getStatue());
    }

    private void setUpRecycleView(CommandeHolder holder, ItemCommande commande) {
        LigneCommandeAdapter ligneCommandeAdapter = new LigneCommandeAdapter(context,commande.ligneCommmandeList);
        holder.ligneRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.ligneRecycleView.setAdapter(ligneCommandeAdapter);
    }


    @Override
    public int getItemCount() {
        return commandes.size();
    }

}

