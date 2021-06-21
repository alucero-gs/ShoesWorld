package com.example.shoesworld.ui.buscar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shoesworld.Producto;
import com.example.shoesworld.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BusquedaFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private BusquedaViewModel mViewModel;
    private Button btnLimpia, btnBusqueda;
    private Spinner spnCategoria, spnTalla, spnColor, spnMarca;

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
        iniciarFirebase();
        //listarDatos();
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
        ArrayAdapter<CharSequence> estatusAdapter;
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

    public void validacion(){
                String marca = spnMarca.getSelectedItem().toString();
        String color = spnColor.getSelectedItem().toString();
        String talla = spnTalla.getSelectedItem().toString();
        String categoria = spnCategoria.getSelectedItem().toString();

        if (marca.equals("Seleccionar marca")){
            Toast.makeText(getContext(), "Para seguir debe seleccionar una marca", Toast.LENGTH_SHORT).show();
        }
        else if(color.equals("Seleccionar color")){
            Toast.makeText(getContext(), "Para seguir debe seleccionar un color", Toast.LENGTH_SHORT).show();
        }
        else if(talla.equals("Seleccionar talla")){
            Toast.makeText(getContext(), "Para seguir debe seleccionar una talla", Toast.LENGTH_SHORT).show();
        }
        else if(categoria.equals("Seleccionar categoria")){
            Toast.makeText(getContext(), "Para seguir debe seleccionar una categoria", Toast.LENGTH_SHORT).show();
        }
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


    @Override
    public void onClick(View v) {
        String marca = spnMarca.getSelectedItem().toString();
        String talla = spnTalla.getSelectedItem().toString();
        String color = spnColor.getSelectedItem().toString();
        String categoria = spnCategoria.getSelectedItem().toString();

        switch (v.getId()){
            case R.id.fbu_btnClear:
                clearFields();
                break;
            case R.id.fbu_btnBusca:

                break;



        }
    }
}