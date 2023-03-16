// Generated by view binder compiler. Do not edit!
package com.example.noblee.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.noblee.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class LayoutPagnieBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageButton pagnieDelete;

  @NonNull
  public final ImageView pagnieImage;

  @NonNull
  public final TextView pagnieNomProduit;

  @NonNull
  public final TextView pagniePrixProduit;

  @NonNull
  public final TextView pagnieQuentiteProduit;

  private LayoutPagnieBinding(@NonNull ConstraintLayout rootView, @NonNull ImageButton pagnieDelete,
      @NonNull ImageView pagnieImage, @NonNull TextView pagnieNomProduit,
      @NonNull TextView pagniePrixProduit, @NonNull TextView pagnieQuentiteProduit) {
    this.rootView = rootView;
    this.pagnieDelete = pagnieDelete;
    this.pagnieImage = pagnieImage;
    this.pagnieNomProduit = pagnieNomProduit;
    this.pagniePrixProduit = pagniePrixProduit;
    this.pagnieQuentiteProduit = pagnieQuentiteProduit;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static LayoutPagnieBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static LayoutPagnieBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.layout_pagnie, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static LayoutPagnieBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.pagnie_delete;
      ImageButton pagnieDelete = ViewBindings.findChildViewById(rootView, id);
      if (pagnieDelete == null) {
        break missingId;
      }

      id = R.id.pagnie_image;
      ImageView pagnieImage = ViewBindings.findChildViewById(rootView, id);
      if (pagnieImage == null) {
        break missingId;
      }

      id = R.id.pagnie_nom_produit;
      TextView pagnieNomProduit = ViewBindings.findChildViewById(rootView, id);
      if (pagnieNomProduit == null) {
        break missingId;
      }

      id = R.id.pagnie_prix_produit;
      TextView pagniePrixProduit = ViewBindings.findChildViewById(rootView, id);
      if (pagniePrixProduit == null) {
        break missingId;
      }

      id = R.id.pagnie_quentite_produit;
      TextView pagnieQuentiteProduit = ViewBindings.findChildViewById(rootView, id);
      if (pagnieQuentiteProduit == null) {
        break missingId;
      }

      return new LayoutPagnieBinding((ConstraintLayout) rootView, pagnieDelete, pagnieImage,
          pagnieNomProduit, pagniePrixProduit, pagnieQuentiteProduit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}