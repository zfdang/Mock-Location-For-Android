package com.zfdang.mocklocation.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.zfdang.mocklocation.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_LOCATION = 1241241;
    private FragmentHomeBinding binding;
    private EditText editTextLongitude;
    private EditText editTextLatitude;
    private Button buttonApply;
    private Button pickButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextLongitude = binding.editTextLongitude;
        editTextLatitude = binding.editTextLatitude;

        pickButton = binding.buttonPickLocation;
        pickButton.setOnClickListener(v -> pickLocation());

        buttonApply = binding.buttonApply;
        buttonApply.setOnClickListener(v -> {
            double latitude = Double.parseDouble(editTextLatitude.getText().toString());
            double longitude = Double.parseDouble(editTextLongitude.getText().toString());
//            homeViewModel.setMockLocation(latitude, longitude);
        });

        return root;
    }

    private void pickLocation() {
        Uri geoUri = Uri.parse("geo:0,0?q=loc:0,0");
        Intent intent = new Intent(Intent.ACTION_VIEW, geoUri);
        startActivityForResult(intent, REQUEST_CODE_PICK_LOCATION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_LOCATION && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                Uri locationUri = data.getData();
                if (locationUri != null) {
                    String[] latLong = locationUri.getLastPathSegment().split(",");
                    editTextLatitude.setText(latLong[0]);
                    editTextLongitude.setText(latLong[1]);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}