package com.example.shoesworld.ui.eliminar;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoesworld.Producto;
import com.example.shoesworld.R;
import com.example.shoesworld.ui.busqueda.BuscarViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EliminarFragment extends Fragment implements View.OnClickListener {

    private EliminarViewModel mViewModel;

    private Button btnLimpiar, btnEliminar;
    private EditText etId, etModelo, etPrecio, etCantidad, etStock, etMarca, etTalla, etColor, etStatus, etCategoria;

    ListView lproducto;

    private List<Producto> ListaProducto = new ArrayList<Producto>(100);
    ArrayAdapter<Producto> arrayAdapterProducto;

    Producto productoSelected;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static EliminarFragment newInstance() {
        return new EliminarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eliminar, container, false);
        Componentes(root);

        lproducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSelected = (Producto) parent.getItemAtPosition(position);
                etId.setText(productoSelected.getUid());
                etModelo.setText(productoSelected.getModelo());
                etMarca.setText(productoSelected.getMarca());
                etColor.setText(productoSelected.getColor());
                etCategoria.setText(productoSelected.getCategoria());
                etTalla.setText(productoSelected.getTalla());
                etPrecio.setText(productoSelected.getPrecio());
                etCantidad.setText(productoSelected.getCantidad());
                etStock.setText(productoSelected.getStock());
                etStatus.setText(productoSelected.getStatus());
            }
        });

        return root;
    }

    private void Componentes(View root){
        EditTextComponent(root);
        Botones(root);
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


    private void EditTextComponent(View root){

        etId = root.findViewById(R.id.fe_id);
        etId.setEnabled(false);
        etMarca = root.findViewById(R.id.fe_marca);
        etMarca.setEnabled(false);
        etModelo = root.findViewById(R.id.fe_modelo);
        etModelo.setEnabled(false);
        etCategoria = root.findViewById(R.id.fe_categoria);
        etCategoria.setEnabled(false);
        etTalla = root.findViewById(R.id.fe_talla);
        etTalla.setEnabled(false);
        etPrecio = root.findViewById(R.id.fe_precio);
        etPrecio.setEnabled(false);
        etCantidad = root.findViewById(R.id.fe_cantidad);
        etCantidad.setEnabled(false);
        etStock = root.findViewById(R.id.fe_stock);
        etStock.setEnabled(false);
        etStatus = root.findViewById(R.id.fe_status);
        etStatus.setEnabled(false);
        etColor = root.findViewById(R.id.fe_color);
        etColor.setEnabled(false);


    }
    private void Botones(View root){

        btnLimpiar = root.findViewById(R.id.fe_btnClear);
        btnEliminar = root.findViewById(R.id.fe_btnEliminar);

        btnEliminar.setOnClickListener((View.OnClickListener) this);
        btnLimpiar.setOnClickListener((View.OnClickListener) this);

    }


    private void clearFields(){
        etId.setText("");
        etModelo.setText("");
        etPrecio.setText("");
        etCantidad.setText("");
        etStock.setText("");
        etColor.setText("");
        etTalla.setText("");
        etMarca.setText("");
        etStatus.setText("");
        etCategoria.setText("");
    }

@Override
    public void onClick(View v){
    switch (v.getId()){
        case R.id.fe_btnClear:
            clearFields();
            break;

        case R.id.fe_btnEliminar:
            Producto p = new Producto();
            p.setUid(productoSelected.getUid());
            p.setMarca(etMarca.getText().toString().trim());
            p.setModelo(etModelo.getText().toString().trim());
            p.setCategoria(etCategoria.getText().toString().trim());
            p.setTalla(etTalla.getText().toString().trim());
            p.setColor(etColor.getText().toString().trim());
            p.setPrecio(etPrecio.getText().toString().trim());
            p.setCantidad("0");
            p.setStock("0");
            p.setStatus("INACTIVO");

            databaseReference.child("Producto").child(p.getUid()).setValue(p);
            Toast.makeText(getContext(), "Eliminado correctamente", Toast.LENGTH_SHORT).show();
            clearFields();

            break;
    }
    }


}