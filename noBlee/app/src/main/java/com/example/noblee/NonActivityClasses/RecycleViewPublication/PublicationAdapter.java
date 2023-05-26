package com.example.noblee.NonActivityClasses.RecycleViewPublication;

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

import com.example.noblee.NonActivityClasses.KeyWord;
import com.example.noblee.NonActivityClasses.RecycleViewCommantaire.CommantaireAdapter;
import com.example.noblee.NonActivityClasses.User;
import com.example.noblee.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicationAdapter extends RecyclerView.Adapter<PublicationHolder> {

    Context context;
    List<ItemPublication> publications;

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
        ItemPublication publication = publications.get(position);
try{
        holder.auteur.setText(publication.getAuteur());
        holder.date.setText(publication.calculerDate());
        holder.contenu.setText(publication.getContenu());
        holder.like.setText(publication.getNumLike());
        holder.dislike.setText(publication.getNumDislike());

        setUpDoctorBadge(publication,holder);

        setUpModirateur(holder);

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
    }catch (Exception e){Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}
    }

    private void setUpDoctorBadge(ItemPublication publication, PublicationHolder holder) {
        try {
            if (publication.isCreeParMedecin()){
                holder.isDoctorImage.setVisibility(View.VISIBLE);
                return;
            }
            holder.isDoctorImage.setVisibility(View.GONE);
        }catch (Exception e){Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();}

    }

    private void setUpModirateur(PublicationHolder holder) {
        if (User.getInstance().isMod()){
            holder.modirateurSupprimer.setVisibility(View.VISIBLE);
            return;
        }
        holder.modirateurSupprimer.setVisibility(View.INVISIBLE);
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
        CommantaireAdapter commantaireAdapter = new CommantaireAdapter(context,publication.getCommantaireList());
        holder.commantaireRecycleView.setLayoutManager(new LinearLayoutManager(context));
        holder.commantaireRecycleView.setAdapter(commantaireAdapter);

    }

    private void ajouterUnCommantaire(PublicationHolder holder, String newCommantaire, ItemPublication publication) {
        publication.ajouterCommantaire(newCommantaire)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        holder.ajouterNewCommantaire.setEnabled(false);
                        holder.newCommantaire.setText("");
                        holder.commantaireRecycleView.getAdapter().notifyDataSetChanged();
                        Toast.makeText(context, "commantaire ajouté", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void attemptLike(ItemPublication publication, boolean isItLike){
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        publication.getReference()
                .collection((isItLike ? KeyWord.PUB_LIKE : KeyWord.PUB_DISLIKE))
                .whereEqualTo(KeyWord.REACTOR, currentUser)
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
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // delleting confilct like/dislike

        publication.getReference()
                .collection((isItLike ? KeyWord.PUB_DISLIKE : KeyWord.PUB_LIKE))
                .whereEqualTo(KeyWord.REACTOR, currentUser)
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
                .collection(isItLike ? KeyWord.PUB_LIKE : KeyWord.PUB_DISLIKE)
                .add(new Like(currentUser))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference reference) {
                        updateLikeDislike(publication);
                    }
                });

    }

    private void updateLikeDislike(ItemPublication publication){
        Map<String,Object> update = new HashMap<>();
        // TODO : contineue
        publication.getReference().collection(KeyWord.PUB_LIKE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                publication.setNumLike(String.valueOf(queryDocumentSnapshots.size()));
                update.put(KeyWord.PUB_NUM_LIKE,publication.getNumLike());
                publication.getReference().collection(KeyWord.PUB_DISLIKE).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        publication.setNumDislike(String.valueOf(queryDocumentSnapshots.size()));
                        update.put(KeyWord.PUB_NUM_DISLIKE,publication.getNumDislike());
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
