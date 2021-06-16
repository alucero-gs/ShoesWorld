package com.example.shoesworld.ui.agregar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AgregarFragment extends Fragment implements View.OnClickListener {

    private AgregarViewModel agregarViewModel;


    private Button btnLimpia, btnGuarda;
    private EditText etId, etMarca, etModelo, etTalla, etColor, etPrecio, etCantidad, etStock;

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

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

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
    }

    private void EditTextComponent(View root){

        etId = root.findViewById(R.id.fa_id);
        etMarca = root.findViewById(R.id.fa_marca);
        etModelo = root.findViewById(R.id.fa_modelo);
        etTalla = root.findViewById(R.id.fa_talla);
        etColor = root.findViewById(R.id.fa_color);
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
        etMarca.setText("");
        etModelo.setText("");
        etTalla.setText("");
        etColor.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        etStock.setText("");
    }

    public void validacion(){
        String id = etId.getText().toString();
        String marca = etMarca.getText().toString();
        String modelo = etModelo.getText().toString();
        String talla = etTalla.getText().toString();
        String color = etColor.getText().toString();
        String precio = etPrecio.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String stock = etStock.getText().toString();

        if (marca.equals("")){
            etMarca.setError("Campo obligatorio");
        }
        else if(id.equals("")){
            etId.setError("Campo obligatorio");
        }
        else if(modelo.equals("")){
            etModelo.setError("Campo obligatorio");
        }
        else if(talla.equals("")){
            etTalla.setError("Campo obligatorio");
        }
        else if(color.equals("")){
            etColor.setError("Campo obligatorio");
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
        String marca = etMarca.getText().toString();
        String modelo = etModelo.getText().toString();
        String talla = etTalla.getText().toString();
        String color = etColor.getText().toString();
        String precio = etPrecio.getText().toString();
        String cantidad = etCantidad.getText().toString();
        String stock = etStock.getText().toString();
        switch (v.getId()){
            case R.id.fa_btnClear:
                clearFields();
                break;
            case R.id.fa_btnAdd:
                if(id.equals("")|| marca.equals("") || modelo.equals("") || talla.equals("") || color.equals("") || cantidad.equals("") || precio.equals("") || stock.equals("")){
                    validacion();
                }else{
                    Producto prod = new Producto();
                    prod.setUid(id);
                    prod.setMarca(marca);
                    prod.setModelo(modelo);
                    prod.setTalla(talla);
                    prod.setColor(color);
                    prod.setPrecio(precio);
                    prod.setCantidad(cantidad);
                    prod.setStock(stock);
                    databaseReference.child("Producto").child(prod.getUid()).setValue(prod);
                    Toast.makeText(getContext(), "Agregado correctamente", Toast.LENGTH_SHORT).show();
                    clearFields();
                }
                break;



        }

    }
}