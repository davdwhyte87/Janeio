// Generated by view binder compiler. Do not edit!
package com.app.janeio.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.app.janeio.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button cancelAlarm;

  @NonNull
  public final LinearLayout container;

  @NonNull
  public final TextView selectedTime;

  @NonNull
  public final Button setAlarm;

  @NonNull
  public final Button setTime;

  private ActivityMainBinding(@NonNull LinearLayout rootView, @NonNull Button cancelAlarm,
      @NonNull LinearLayout container, @NonNull TextView selectedTime, @NonNull Button setAlarm,
      @NonNull Button setTime) {
    this.rootView = rootView;
    this.cancelAlarm = cancelAlarm;
    this.container = container;
    this.selectedTime = selectedTime;
    this.setAlarm = setAlarm;
    this.setTime = setTime;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cancelAlarm;
      Button cancelAlarm = ViewBindings.findChildViewById(rootView, id);
      if (cancelAlarm == null) {
        break missingId;
      }

      LinearLayout container = (LinearLayout) rootView;

      id = R.id.selectedTime;
      TextView selectedTime = ViewBindings.findChildViewById(rootView, id);
      if (selectedTime == null) {
        break missingId;
      }

      id = R.id.setAlarm;
      Button setAlarm = ViewBindings.findChildViewById(rootView, id);
      if (setAlarm == null) {
        break missingId;
      }

      id = R.id.setTime;
      Button setTime = ViewBindings.findChildViewById(rootView, id);
      if (setTime == null) {
        break missingId;
      }

      return new ActivityMainBinding((LinearLayout) rootView, cancelAlarm, container, selectedTime,
          setAlarm, setTime);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
