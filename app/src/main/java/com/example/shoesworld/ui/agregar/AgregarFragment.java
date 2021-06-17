package com.example.shoesworld.ui.agregar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoesworld.Producto;
import com.example.shoesworld.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AgregarFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private AgregarViewModel agregarViewModel;


    private Button btnLimpia, btnGuarda;
    private EditText etId, etModelo, etPrecio, etCantidad, etStock;
    private Spinner spnCategoria, spnTalla, spnColor, spnMarca, spnStatus;

    public static String Sstatus, Scategoria, Stalla, Scolor, Smarca;

    private List<Producto> ListaProducto = new ArrayList<Producto>();
    ArrayAdapter<Producto> arrayAdapterPersona;

    Producto productoSelected;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        agregarViewModel =
                new ViewModelProvider(this).get(AgregarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agregar, container, false);
        iniciarFirebase();
        //listarDatos();
        Componentes(root);


        return root;
    }

    public void iniciarFirebase(){
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void Componentes(View root){
        EditTextComponent(root);
        Botones(root);
        SpinnerComponent(root);
    }

    private void SpinnerComponent(View root){

        ArrayAdapter<CharSequence> estatusAdapter;
        ArrayAdapter<CharSequence> marcaAdapter;
        ArrayAdapter<CharSequence> categoriaAdapter;
        ArrayAdapter<CharSequence> tallaAdapter;
        ArrayAdapter<CharSequence> colorAdapter;

        estatusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        spnStatus = root.findViewById(R.id.fa_status);
        spnStatus.setAdapter(estatusAdapter);
        spnStatus.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        marcaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.marcas, android.R.layout.simple_spinner_item);
        spnMarca = root.findViewById(R.id.fa_marca);
        spnMarca.setAdapter(marcaAdapter);
        spnMarca.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        categoriaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.categorias, android.R.layout.simple_spinner_item);
        spnCategoria = root.findViewById(R.id.fa_categoria);
        spnCategoria.setAdapter(categoriaAdapter);
        spnCategoria.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        tallaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tallas, android.R.layout.simple_spinner_item);
        spnTalla = root.findViewById(R.id.fa_talla);
        spnTalla.setAdapter(tallaAdapter);
        spnTalla.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        colorAdapter = ArrayAdapter.createFromResource(getContext(), R.array.colores, android.R.layout.simple_spinner_item);
        spnColor = root.findViewById(R.id.fa_color);
        spnColor.setAdapter(colorAdapter);
        spnColor.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

    }

    private void EditTextComponent(View root){

        etId = root.findViewById(R.id.fa_id);
        etModelo = root.findViewById(R.id.fa_modelo);
        etPrecio = root.findViewById(R.id.fa_precio);
        etCantidad = root.findViewById(R.id.fa_cantidad);
        etStock = root.findViewById(R.id.fa_stock);

    }
    private void Botones(View root){

        btnLimpia = root.findViewById(R.id.fa_btnClear);
        btnGuarda = root.findViewById(R.id.fa_btnAdd);

        btnGuarda.setOnClickListener((View.OnClickListener) this);
        btnLimpia.setOnClickListener((View.OnClickListener) this);

    }

    private void clearFields(){
        etId.setText("");
        etModelo.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        etStock.setText("");

        ArrayAdapter<CharSequence> estatusAdapter;
        estatusAdapter = ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        Sstatus = "";
        spnStatus.setAdapter(estatusAdapter);

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
        String id = etId.getText().toString();
        String modelo = etModelo.getText().toString();
        String precio = etPrecio.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String stock = etStock.getText().toString();
        String status = spnStatus.getSelectedItem().toString();
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
        else if(status.equals("Seleccionar status")){
            Toast.makeText(getContext(), "Para seguir debe seleccionar un status", Toast.LENGTH_SHORT).show();
        }
        else if(id.equals("")){
            etId.setError("Campo obligatorio");
        }
        else if(modelo.equals("")){
            etModelo.setError("Campo obligatorio");
        }else if(cantidad.equals("")){
            etCantidad.setError("Campo obligatorio");
        }
        else if(precio.equals("")){
            etPrecio.setError("Campo obligatorio");
        }
        else if(stock.equals("")){
            etStock.setError("Campo obligatorio");
        }

    }

    @Override
    public void onClick(View v) {
        String id = etId.getText().toString();
        String marca = spnMarca.getSelectedItem().toString();
        String modelo = etModelo.getText().toString();
        String talla = spnTalla.getSelectedItem().toString();
        String color = spnColor.getSelectedItem().toString();
        String precio = etPrecio.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String stock = etStock.getText().toString();
        String categoria = spnCategoria.getSelectedItem().toString();
        String status = spnStatus.getSelectedItem().toString();

        switch (v.getId()){
            case R.id.fa_btnClear:
                clearFields();
                break;
            case R.id.fa_btnAdd:
                if(id.equals("")|| marca.equals("Seleccionar marca") || modelo.equals("") || talla.equals("Seleccionar talla") || color.equals("Seleccionar color") || cantidad.equals("") || precio.equals("") || stock.equals("") || categoria.equals("Seleccionar categoria") || status.equals("Seleccionar status")){
                    validacion();
                }else{
                    Producto prod = new Producto();
                    prod.setUid(id);
                    prod.setMarca(marca);
                    prod.setModelo(modelo);
                    prod.setCategoria(categoria);
                    prod.setTalla(talla);
                    prod.setColor(color);
                    prod.setPrecio(precio);
                    prod.setCantidad(cantidad);
                    prod.setStock(stock);
                    prod.setStatus(status);
                    databaseReference.child("Producto").child(prod.getUid()).setValue(prod);
                    Toast.makeText(getContext(), "Agregado correctamente", Toast.LENGTH_SHORT).show();
                    clearFields();
                }
                break;



        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.fa_status:
                if (position!=0){
                    Sstatus = parent.getItemAtPosition(position).toString();
                }else{
                    Sstatus ="";
                }
                break;
            case R.id.fa_categoria:
                if (position!=0){
                    Scategoria = parent.getItemAtPosition(position).toString();
                }else{
                    Scategoria ="";
                }
                break;
            case R.id.fa_talla:
                if (position!=0){
                    Stalla = parent.getItemAtPosition(position).toString();
                }else{
                    Stalla ="";
                }
                break;
            case R.id.fa_marca:
                if (position!=0){
                    Smarca = parent.getItemAtPosition(position).toString();
                }else{
                    Smarca ="";
                }
                break;
            case R.id.fa_color:
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
}