package com.example.shoesworld.ui.creditos;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shoesworld.R;

public class CreditosFragment extends Fragment implements View.OnClickListener{
    Button btnUbicacion;

    private CreditosViewModel mViewModel;

    public static CreditosFragment newInstance() {
        return new CreditosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_creditos, container, false);

        mapa(root);
        return root;

    }

    public void mapa(View root){
        btnUbicacion = root.findViewById(R.id.btnUbicacion);
        btnUbicacion.setOnClickListener((View.OnClickListener) this);


    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreditosViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUbicacion:
                Intent nuevoI = new Intent(getContext(), MapsActivity.class);
                startActivity(nuevoI);
                onStop();
                break;
        }
    }
}