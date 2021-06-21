package com.example.shoesworld.ui.inactivos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoesworld.Adaptador_producto;
import com.example.shoesworld.Producto;
import com.example.shoesworld.Productos;
import com.example.shoesworld.R;
import com.example.shoesworld.ui.productos.ProductosViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InactivosFragment extends Fragment {

    private InactivosViewModel inactivosViewModel;

    Adaptador_producto adaptador_producto;
    RecyclerView rv ;
    ArrayList<Productos> lista_productos;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView.LayoutManager layoutManager;

    ListView lproducto;

    private List<Producto> ListaProducto = new ArrayList<Producto>(100);
    ArrayAdapter<Producto> arrayAdapterProducto;


    public static InactivosFragment newInstance() {
        return new InactivosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inactivosViewModel = new ViewModelProvider(this).get(InactivosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inactivos, container, false);
        TextView identificador,nombre;
        ImageButton info, editar, borrar;

        identificador = root.findViewById(R.id.tv_id_item);
        nombre = root.findViewById(R.id.tv_nombre_item);
        info = root.findViewById(R.id.ib_info_item);
        editar = root.findViewById(R.id.ib_editar_item);
        borrar = root.findViewById(R.id.ib_borrar_item);
        rv =root.findViewById(R.id.rv_stock);
        lista_productos=new ArrayList<>();

        iniciarFB();
        cargar();

        inactivosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s)
            {


            }
        });
        return root;
    }

    private void iniciarFB() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiar() {

        //Toast.makeText(getContext(), "limpia", Toast.LENGTH_LONG).show();

        lista_productos.clear();
        adaptador_producto= new Adaptador_producto(getContext(), lista_productos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adaptador_producto);
    }

    private void cargar(){

        //Toast.makeText(getContext(), "carga", Toast.LENGTH_LONG).show();
        Query consulta= databaseReference.child("Producto").orderByKey();
        consulta.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                limpiar();
                if(snapshot.exists()){


                    for(DataSnapshot ds: snapshot.getChildren()){
                        String nombre=ds.child("modelo").getValue().toString();
                        String id=ds.child("uid").getValue().toString();
                        //String id="a1";
                        lista_productos.add(new Productos(nombre,id, R.drawable.ic_menu_camera));

                    }
                    adaptador_producto= new Adaptador_producto(getContext(),lista_productos);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rv.setAdapter(adaptador_producto);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



    }
}