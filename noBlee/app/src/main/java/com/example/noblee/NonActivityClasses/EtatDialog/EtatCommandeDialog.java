package com.example.noblee.NonActivityClasses.EtatDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.NonActivityClasses.EtatDialog.CommandeRecycleView.CommandeAdapter;
import com.example.noblee.NonActivityClasses.EtatDialog.CommandeRecycleView.ItemCommande;
import com.example.noblee.NonActivityClasses.User;
import com.example.noblee.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EtatCommandeDialog extends AppCompatDialogFragment {

    RecyclerView recyclerView;
    List<ItemCommande> commandeList;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_etat_commandes, null);
        try{
        builder.setView(view)
                .setTitle("Etat des commandes")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        recyclerView = view.findViewById(R.id.dialog_etat_commande_recycle_view);

        setUpRecycleView();

    }catch(Exception e){Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();}
        return builder.create();
    }

    private void setUpRecycleView() {
        commandeList = new ArrayList<>();
        CommandeAdapter commandeAdapter = new CommandeAdapter(getContext(),commandeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(commandeAdapter);

        FirebaseFirestore.getInstance().collection("Magazin")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentMagazin : queryDocumentSnapshots){
                        documentMagazin.getReference()
                                .collection("Commandes")
                                .whereEqualTo("maladePath", User.getInstance().getReference().getPath())
                                .get()
                                .addOnSuccessListener(queryDocumentSnapshots1 -> {
                                    try {

                                    for (DocumentSnapshot document : queryDocumentSnapshots1){
                                        commandeList.add(
                                                new ItemCommande(
                                                        document.getTimestamp("date"),
                                                        document.getString("maladePath"),
                                                        document.getString("prixTotal"),
                                                        document.getString("statue"),
                                                        documentMagazin.getString("nom"),
                                                        document.getReference()
                                                )
                                        );
                                    }
                                    commandeAdapter.notifyDataSetChanged();

                                }catch(Exception e){Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();}
                                });
                    }
                });
    }
}
