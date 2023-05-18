package com.example.nobleemedecin.NonActivityClasses.RecycleViewPublication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nobleemedecin.NonActivityClasses.Medecin;
import com.example.nobleemedecin.NonActivityClasses.PublicationKeyWords;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewwCommantaire.CommantaireAdapter;
import com.example.nobleemedecin.NonActivityClasses.RecycleViewwCommantaire.ItemCommantaire;
import com.example.nobleemedecin.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationHolder> {

    Context context;
    List<ItemPublication> publications;
    List<ItemCommantaire> commantaires;

    public PublicationAdapter(Context context, List<ItemPublication> publications) {
        this.context = context;
        this.publications = publications;
    }

    @NonNull
    @Override
    public PublicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PublicationHolder(LayoutInflater.from(context).inflate(R.layout.layout_publication,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationHolder holder, int position) {
        try{
        ItemPublication publication = publications.get(position);

        holder.auteur.setText(publication.getAuteur());
        holder.date.setText(publication.calculerDate());
        holder.contenu.setText(publication.getContenu());
        holder.like.setText(publication.getNumLike());
        holder.dislike.setText(publication.getNumDislike());

        //updateLikeDislike(publication);

        holder.modirateurSupprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePublication(publication.getReference(),position);
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLike(publication,true);
            }
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLike(publication,false);
            }
        });

        holder.commanter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.commantaireLayout.getVisibility() == View.GONE){
                    holder.commantaireLayout.setVisibility(View.VISIBLE);
                    setUpCommantaires(publication,holder);
                }else {
                    holder.commantaireLayout.setVisibility(View.GONE);
                }
            }
        });

        holder.ajouterNewCommantaire.setEnabled(false);
        holder.newCommantaire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                holder.ajouterNewCommantaire.setEnabled(!holder.newCommantaire.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.ajouterNewCommantaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterUnCommantaire(holder,holder.newCommantaire.getText().toString(),publication);
            }
        });


    }catch(Exception e){
        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
    }


    private void deletePublication(DocumentReference reference, int position) {
        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                publications.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    private void setUpCommantaires(ItemPublication publication, PublicationHolder holder) {
        CommantaireAdapter commantaireAdapter = new CommantaireAdapter(context,commantaires = new ArrayList<>());
        holder.commantaireRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.commantaireRecycleView.setAdapter(commantaireAdapter);
        publication.getReference()
                .collection(PublicationKeyWords.PUB_COMMANTAIRE)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots){
                            commantaires.add(
                                    new ItemCommantaire(
                                            document.getString("user"),
                                            document.getString("contenu"),
                                            document.getReference()
                                    )
                            );
                        }
                        commantaireAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void ajouterUnCommantaire(PublicationHolder holder, String newCommantaire, ItemPublication publication) {
        try {


        publication.getReference()
                .collection(PublicationKeyWords.PUB_COMMANTAIRE)
                .add(
                        new ItemCommantaire(
                                Medecin.getInstance().getNom() + " " + Medecin.getInstance().getPrenom(),
                                newCommantaire
                        )
                ).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        try {
                        holder.ajouterNewCommantaire.setEnabled(false);
                        holder.newCommantaire.setText("");
                        holder.commantaireRecycleView.getAdapter().notifyDataSetChanged();
                        Toast.makeText(context, "commantaire ajouté", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
                    }
                });
        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
    }

    void attemptLike(ItemPublication publication, boolean isItLike){
        String currentUser = Medecin.getInstance().getReference().getId();

        publication.getReference()
                .collection((isItLike ? PublicationKeyWords.PUB_LIKE : PublicationKeyWords.PUB_DISLIKE))
                .whereEqualTo(PublicationKeyWords.REACTOR, currentUser)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.getDocuments().isEmpty()){
                            // nouveau aime (en suprimant dislike)
                            addLike(publication,isItLike);
                        }else {
                            // pub est deja aimee
                            inverseLike(publication,isItLike,queryDocumentSnapshots);
                        }
                    }
                });
    }

    private void inverseLike(ItemPublication publication, boolean isItLike, QuerySnapshot queryDocumentSnapshots) {
        if (isItLike) {
            queryDocumentSnapshots.getDocuments().get(0).getReference().delete();
            updateLikeDislike(publication);
            return;
        }
        queryDocumentSnapshots.getDocuments().get(0).getReference().delete();
        updateLikeDislike(publication);
    }

    private void addLike(ItemPublication publication, boolean isItLike) {
        String currentUser = Medecin.getInstance().getReference().getId();

        // delleting confilct like/dislike

        publication.getReference()
                .collection((isItLike ? PublicationKeyWords.PUB_DISLIKE : PublicationKeyWords.PUB_LIKE))
                .whereEqualTo(PublicationKeyWords.REACTOR, currentUser)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.getDocuments().isEmpty()){
                            queryDocumentSnapshots.getDocuments().get(0).getReference().delete();
                            updateLikeDislike(publication);
                        }
                    }
                });


        // add like/dislike
        publication.getReference()
                .collection(isItLike ? PublicationKeyWords.PUB_LIKE : PublicationKeyWords.PUB_DISLIKE)
                .add(new Like(currentUser,true))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        updateLikeDislike(publication);
                    }
                });

    }

    private void updateLikeDislike(ItemPublication publication){
        Map<String,Object> update = new HashMap<>();
        publication.getReference().collection(PublicationKeyWords.PUB_LIKE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                publication.setNumLike(String.valueOf(queryDocumentSnapshots.size()));
                update.put(PublicationKeyWords.PUB_NUM_LIKE,publication.getNumLike());
                publication.getReference().collection(PublicationKeyWords.PUB_DISLIKE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        publication.setNumDislike(String.valueOf(queryDocumentSnapshots.size()));
                        update.put(PublicationKeyWords.PUB_NUM_DISLIKE,publication.getNumDislike());
                        publication.getReference().update(update);
                        notifyDataSetChanged();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return publications.size();
    }
}
