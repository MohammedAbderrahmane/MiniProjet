// Generated by view binder compiler. Do not edit!
package com.example.noblee.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.noblee.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCommandeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton commandeAjouter;

  @NonNull
  public final Button commandeAnnuler;

  @NonNull
  public final Button commandeConfirme;

  @NonNull
  public final TextView commandeDescription;

  @NonNull
  public final RecyclerView commandePagnies;

  private ActivityCommandeBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageButton commandeAjouter, @NonNull Button commandeAnnuler,
      @NonNull Button commandeConfirme, @NonNull TextView commandeDescription,
      @NonNull RecyclerView commandePagnies) {
    this.rootView = rootView;
    this.commandeAjouter = commandeAjouter;
    this.commandeAnnuler = commandeAnnuler;
    this.commandeConfirme = commandeConfirme;
    this.commandeDescription = commandeDescription;
    this.commandePagnies = commandePagnies;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCommandeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCommandeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_commande, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCommandeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.commande_ajouter;
      ImageButton commandeAjouter = ViewBindings.findChildViewById(rootView, id);
      if (commandeAjouter == null) {
        break missingId;
      }

      id = R.id.commande_annuler;
      Button commandeAnnuler = ViewBindings.findChildViewById(rootView, id);
      if (commandeAnnuler == null) {
        break missingId;
      }

      id = R.id.commande_confirme;
      Button commandeConfirme = ViewBindings.findChildViewById(rootView, id);
      if (commandeConfirme == null) {
        break missingId;
      }

      id = R.id.commande_description;
      TextView commandeDescription = ViewBindings.findChildViewById(rootView, id);
      if (commandeDescription == null) {
        break missingId;
      }

      id = R.id.commande_pagnies;
      RecyclerView commandePagnies = ViewBindings.findChildViewById(rootView, id);
      if (commandePagnies == null) {
        break missingId;
      }

      return new ActivityCommandeBinding((ConstraintLayout) rootView, commandeAjouter,
          commandeAnnuler, commandeConfirme, commandeDescription, commandePagnies);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}