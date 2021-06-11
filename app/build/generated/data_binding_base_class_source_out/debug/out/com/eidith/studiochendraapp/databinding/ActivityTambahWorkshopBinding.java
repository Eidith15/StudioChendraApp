// Generated by view binder compiler. Do not edit!
package com.eidith.studiochendraapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.eidith.studiochendraapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTambahWorkshopBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final Button btnDatePickerWorkshop;

  @NonNull
  public final Button btnSelectImageWorkshop;

  @NonNull
  public final Button btnSelectVideoWorkshop;

  @NonNull
  public final Button btnTambahWorkshop;

  @NonNull
  public final EditText etDeskripsiWorkshop;

  @NonNull
  public final EditText etJudulWorkshop;

  private ActivityTambahWorkshopBinding(@NonNull ScrollView rootView,
      @NonNull Button btnDatePickerWorkshop, @NonNull Button btnSelectImageWorkshop,
      @NonNull Button btnSelectVideoWorkshop, @NonNull Button btnTambahWorkshop,
      @NonNull EditText etDeskripsiWorkshop, @NonNull EditText etJudulWorkshop) {
    this.rootView = rootView;
    this.btnDatePickerWorkshop = btnDatePickerWorkshop;
    this.btnSelectImageWorkshop = btnSelectImageWorkshop;
    this.btnSelectVideoWorkshop = btnSelectVideoWorkshop;
    this.btnTambahWorkshop = btnTambahWorkshop;
    this.etDeskripsiWorkshop = etDeskripsiWorkshop;
    this.etJudulWorkshop = etJudulWorkshop;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTambahWorkshopBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTambahWorkshopBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_tambah_workshop, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTambahWorkshopBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnDatePickerWorkshop;
      Button btnDatePickerWorkshop = rootView.findViewById(id);
      if (btnDatePickerWorkshop == null) {
        break missingId;
      }

      id = R.id.btnSelectImageWorkshop;
      Button btnSelectImageWorkshop = rootView.findViewById(id);
      if (btnSelectImageWorkshop == null) {
        break missingId;
      }

      id = R.id.btnSelectVideoWorkshop;
      Button btnSelectVideoWorkshop = rootView.findViewById(id);
      if (btnSelectVideoWorkshop == null) {
        break missingId;
      }

      id = R.id.btnTambahWorkshop;
      Button btnTambahWorkshop = rootView.findViewById(id);
      if (btnTambahWorkshop == null) {
        break missingId;
      }

      id = R.id.etDeskripsiWorkshop;
      EditText etDeskripsiWorkshop = rootView.findViewById(id);
      if (etDeskripsiWorkshop == null) {
        break missingId;
      }

      id = R.id.etJudulWorkshop;
      EditText etJudulWorkshop = rootView.findViewById(id);
      if (etJudulWorkshop == null) {
        break missingId;
      }

      return new ActivityTambahWorkshopBinding((ScrollView) rootView, btnDatePickerWorkshop,
          btnSelectImageWorkshop, btnSelectVideoWorkshop, btnTambahWorkshop, etDeskripsiWorkshop,
          etJudulWorkshop);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
