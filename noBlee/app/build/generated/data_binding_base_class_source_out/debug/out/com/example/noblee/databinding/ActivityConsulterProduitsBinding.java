// Generated by view binder compiler. Do not edit!
package com.example.noblee.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ActivityConsulterProduitsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView recycleViewStatue;

  @NonNull
  public final RecyclerView recycleview;

  private ActivityConsulterProduitsBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView recycleViewStatue, @NonNull RecyclerView recycleview) {
    this.rootView = rootView;
    this.recycleViewStatue = recycleViewStatue;
    this.recycleview = recycleview;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityConsulterProduitsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityConsulterProduitsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_consulter_produits, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityConsulterProduitsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recycleView_statue;
      TextView recycleViewStatue = ViewBindings.findChildViewById(rootView, id);
      if (recycleViewStatue == null) {
        break missingId;
      }

      id = R.id.recycleview;
      RecyclerView recycleview = ViewBindings.findChildViewById(rootView, id);
      if (recycleview == null) {
        break missingId;
      }

      return new ActivityConsulterProduitsBinding((ConstraintLayout) rootView, recycleViewStatue,
          recycleview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
