package com.example.groupbschedulingsoftwaresefall2022;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.groupbschedulingsoftwaresefall2022.databinding.FragmentSecondBinding;
import com.example.groupbschedulingsoftwaresefall2022.databinding.SchedulenamepopupBinding;

public class ScheduleNamePopup extends Fragment {
  private SchedulenamepopupBinding binding;
  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState
  ) {

    binding = SchedulenamepopupBinding.inflate(inflater, container, false);
    return binding.getRoot();

  }
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    binding.finalizeSched.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        NavHostFragment.findNavController(ScheduleNamePopup.this)
            .navigate(R.id.action_ScheduleNamePopup_to_SecondFragment);
      }
    });
  }
  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
