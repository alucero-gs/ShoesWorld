package com.example.shoesworld.ui.inactivos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shoesworld.Producto;
import com.example.shoesworld.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InactivosFragment extends Fragment {

    private InactivosViewModel mViewModel;

    ListView lproducto;

    private List<Producto> ListaProducto = new ArrayList<Producto>(100);
    ArrayAdapter<Producto> arrayAdapterProducto;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static InactivosFragment newInstance() {
        return new InactivosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_inactivos, container, false);
        Componentes(root);
        return root;
    }

    private void Componentes(View root){

        lproducto = root.findViewById(R.id.lvElementos);
        iniciarFirebase();
        listarDatos();


    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public void listarDatos(){
        databaseReference.child("Producto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ListaProducto.clear();
                for(DataSnapshot objSnapsot: snapshot.getChildren()){
                    Producto prod =objSnapsot.getValue(Producto.class);
                    ListaProducto.add(prod);
                    arrayAdapterProducto= new ArrayAdapter<Producto>(getContext(), android.R.layout.simple_list_item_1, ListaProducto);
                    lproducto.setAdapter(arrayAdapterProducto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}