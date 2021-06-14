package com.example.shoesworld.ui.agregar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoesworld.R;

public class AgregarFragment extends Fragment {

    private AgregarViewModel agregarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agregarViewModel =
                new ViewModelProvider(this).get(AgregarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agregar, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        agregarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}