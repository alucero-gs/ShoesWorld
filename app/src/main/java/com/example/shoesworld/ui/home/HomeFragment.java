package com.example.shoesworld.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shoesworld.Adaptador_producto;
import com.example.shoesworld.MainActivity;
import com.example.shoesworld.Principal;
import com.example.shoesworld.Productos;
import com.example.shoesworld.R;
import com.example.shoesworld.Usuarios;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends Fragment {
     String name, correo;
    CardView cerrar;
    SharedPreferences prefs;
    int tipo;









    public HomeFragment() {

        this.correo="";


    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        this.prefs =getActivity(). getSharedPreferences("user_details",   Context.MODE_PRIVATE);


        correo= prefs.getString("correo", "");
        tipo=prefs.getInt("tipo",0);

        name=prefs.getString("nombre","");
        System.out.println("recibe "+correo+" nombre: "+name+ "tipo "+ tipo);


    }




    private HomeViewModel homeViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cerrar=root.findViewById(R.id.cv_logout);
         TextView textView = root.findViewById(R.id.tv_saludo);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cierra sesion",Toast.LENGTH_LONG).show();
                FirebaseAuth auth;
                auth=FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent= new Intent(getContext(), MainActivity.class);
                name="";
                correo="";
                textView.setText("");
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                //editor.remove("keyname");
                editor.commit();


                startActivity(intent);


                try {
                    HomeFragment.this.finalize();
                    HomeFragment.this.onDestroy();
                    HomeFragment.this.onDestroyView();


                    System.out.println("si cerrorrrr");
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
        });



        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                System.out.println("despues "+ name);
                textView.setText("hola "+name+" "+correo);


            }
        });





        return root;
    }
    public void setNombre(String nombre) {
        System.out.println("entra al met "+ nombre);
        this.name=nombre;
        System.out.println("sale del met "+ name);
    }


}