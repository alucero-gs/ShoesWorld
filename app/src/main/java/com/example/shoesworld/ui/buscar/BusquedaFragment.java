package com.example.shoesworld.ui.buscar;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class BusquedaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private BusquedaViewModel mViewModel;
    private Button btnLimpia, btnBusqueda;
    private Spinner spnCategoria, spnTalla, spnColor, spnMarca;

    Adaptador_producto adaptador_producto;
    RecyclerView rv;
    ArrayList<Productos> lista_productos;
    RecyclerView.LayoutManager layoutManager;


    public static String Scategoria, Stalla, Scolor, Smarca;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static BusquedaFragment newInstance() {
        return new BusquedaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_busqueda, container, false);

        TextView identificador,nombre;
        ImageButton info, editar, borrar;

        identificador = root.findViewById(R.id.tv_id_item);
        nombre = root.findViewById(R.id.tv_nombre_item);
        info = root.findViewById(R.id.ib_info_item);
        editar = root.findViewById(R.id.ib_editar_item);
        borrar = root.findViewById(R.id.ib_borrar_item);
        rv =root.findViewById(R.id.rv_marca);
        lista_productos=new ArrayList<>();

        iniciarFirebase();
        Componentes(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BusquedaViewModel.class);
        // TODO: Use the ViewModel
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void Componentes(View root){
        Botones(root);
        SpinnerComponent(root);
    }

    private void SpinnerComponent(View root){

        ArrayAdapter<CharSequence> marcaAdapter;
        ArrayAdapter<CharSequence> categoriaAdapter;
        ArrayAdapter<CharSequence> tallaAdapter;
        ArrayAdapter<CharSequence> colorAdapter;

        marcaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.marcas, android.R.layout.simple_spinner_item);
        spnMarca = root.findViewById(R.id.fbu_marca);
        spnMarca.setAdapter(marcaAdapter);
        spnMarca.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        categoriaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        spnCategoria = root.findViewById(R.id.fbu_categoria);
        spnCategoria.setAdapter(categoriaAdapter);
        spnCategoria.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        tallaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tallas, android.R.layout.simple_spinner_item);
        spnTalla = root.findViewById(R.id.fbu_talla);
        spnTalla.setAdapter(tallaAdapter);
        spnTalla.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.colores, android.R.layout.simple_spinner_item);
        spnColor = root.findViewById(R.id.fbu_color);
        spnColor.setAdapter(colorAdapter);
        spnColor.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }

    private void Botones(View root){

        btnLimpia = root.findViewById(R.id.fbu_btnClear);
        btnBusqueda = root.findViewById(R.id.fbu_btnBusca);

        btnBusqueda.setOnClickListener((View.OnClickListener) this);
        btnLimpia.setOnClickListener((View.OnClickListener) this);

    }

    private void clearFields(){
        ArrayAdapter<CharSequence> marcaAdapter;
        marcaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.marcas, android.R.layout.simple_spinner_item);
        Smarca = "";
        spnMarca.setAdapter(marcaAdapter);

        ArrayAdapter<CharSequence> colorAdapter;
        colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.colores, android.R.layout.simple_spinner_item);
        Scolor = "";
        spnColor.setAdapter(colorAdapter);

        ArrayAdapter<CharSequence> categoriaAdapter;
        categoriaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        Scategoria = "";
        spnCategoria.setAdapter(categoriaAdapter);

        ArrayAdapter<CharSequence> tallaAdapter;
        tallaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tallas, android.R.layout.simple_spinner_item);
        Stalla = "";
        spnTalla.setAdapter(tallaAdapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.fbu_categoria:
                if (position!=0){
                    Scategoria = parent.getItemAtPosition(position).toString();
                }else{
                    Scategoria ="";
                }
                break;
            case R.id.fbu_talla:
                if (position!=0){
                    Stalla = parent.getItemAtPosition(position).toString();
                }else{
                    Stalla ="";
                }
                break;
            case R.id.fbu_marca:
                if (position!=0){
                    Smarca = parent.getItemAtPosition(position).toString();
                }else{
                    Smarca ="";
                }
                break;
            case R.id.fbu_color:
                if (position!=0){
                    Scolor = parent.getItemAtPosition(position).toString();
                }else{
                    Scolor ="";
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void cargar(){
        String marca = spnMarca.getSelectedItem().toString();
        String talla = spnTalla.getSelectedItem().toString();
        String color = spnColor.getSelectedItem().toString();
        String categoria = spnCategoria.getSelectedItem().toString();


        Query buscarm = databaseReference.child("Producto").orderByChild("marca").equalTo(marca);
        Query buscarcat = databaseReference.child("Producto").orderByChild("categoria").equalTo(categoria);
        Query buscart = databaseReference.child("Producto").orderByChild("talla").equalTo(talla);
        Query buscarcol = databaseReference.child("Producto").orderByChild("color").equalTo(color);

        buscarm.addValueEventListener(new ValueEventListener() {
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

        buscarcat.addValueEventListener(new ValueEventListener() {
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

        buscart.addValueEventListener(new ValueEventListener() {
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

        buscarcol.addValueEventListener(new ValueEventListener() {
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

    private void limpiar() {

        //Toast.makeText(getContext(), "limpia", Toast.LENGTH_LONG).show();

        lista_productos.clear();
        adaptador_producto= new Adaptador_producto(getContext(), lista_productos);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adaptador_producto);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fbu_btnClear:
                clearFields();
                limpiar();
                break;
            case R.id.fbu_btnBusca:
                cargar();
                break;



        }
    }
}