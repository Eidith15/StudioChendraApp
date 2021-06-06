// Generated by view binder compiler. Do not edit!
package com.eidith.studiochendraapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.eidith.studiochendraapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemWorkshopBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView imgGambar;

  @NonNull
  public final TextView textId;

  @NonNull
  public final TextView textJudul;

  private ItemWorkshopBinding(@NonNull CardView rootView, @NonNull ImageView imgGambar,
      @NonNull TextView textId, @NonNull TextView textJudul) {
    this.rootView = rootView;
    this.imgGambar = imgGambar;
    this.textId = textId;
    this.textJudul = textJudul;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemWorkshopBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemWorkshopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_workshop, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemWorkshopBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgGambar;
      ImageView imgGambar = rootView.findViewById(id);
      if (imgGambar == null) {
        break missingId;
      }

      id = R.id.textId;
      TextView textId = rootView.findViewById(id);
      if (textId == null) {
        break missingId;
      }

      id = R.id.textJudul;
      TextView textJudul = rootView.findViewById(id);
      if (textJudul == null) {
        break missingId;
      }

      return new ItemWorkshopBinding((CardView) rootView, imgGambar, textId, textJudul);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}