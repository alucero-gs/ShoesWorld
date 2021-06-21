package com.example.shoesworld;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoesworld.ui.home.HomeFragment;
import com.example.shoesworld.ui.home.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    Button btn_iniciar, btn_regis;
    EditText etxt_correo, etxt_passs;
    String correo, pass, name;
    int tipo;
    SharedPreferences pref;
    SharedPreferences.Editor editor;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        editor = pref.edit();



        btn_iniciar = findViewById(R.id.btn_entrar_log);
        btn_regis = findViewById(R.id.btn_registrar_log);
        etxt_correo = findViewById(R.id.et_correo_log);
        etxt_passs = findViewById(R.id.et_pass_log);

        this.correo = "";
        this.pass = "";
        this.name = "";
        this.etxt_correo.setText("");
        this.etxt_passs.setText("");


        btn_iniciar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String mail = etxt_correo.getText().toString().replaceAll(" ", "");
                String pass = etxt_passs.getText().toString().replaceAll(" ", "");
                FirebaseAuth auth;
                auth = FirebaseAuth.getInstance();
                if (!mail.isEmpty() && !pass.isEmpty()) {
                    auth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {


                                System.out.println("entra con "+mail);



                                FirebaseDatabase firebaseDatabase;
                                DatabaseReference databaseReference;
                                FirebaseApp.initializeApp(MainActivity.this);
                                firebaseDatabase = FirebaseDatabase.getInstance();
                                databaseReference = firebaseDatabase.getReference();
                                Query consulta= databaseReference.child("usuarios").orderByChild("correo").equalTo(mail);


                                consulta.addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                                        if (snapshot.exists()) {

                                            Usuarios usuario = snapshot.getValue(Usuarios.class);
                                            correo=usuario.getCorreo().toString();
                                            name=usuario.getNombre().toString();
                                            tipo= ((Integer) usuario.getTipo());
                                            System.out.println("res: "+name);
                                            enviar(name, correo, tipo);
                                            Intent intent = new Intent(MainActivity.this, Principal.class);

                                            startActivity(intent);
                                            finish();


                                        }else{
                                            System.out.println("no arrojó");
                                        }
                                    }

                                    @Override
                                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                                    }

                                    @Override
                                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }
                                }) ;



                               /*HomeFragment homeFragment = new HomeFragment();
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();*/


                            } else {
                                Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_LONG).show();
                            }

                        }


                    });
                } else {
                    Toast.makeText(MainActivity.this, "Llena los campos", Toast.LENGTH_LONG).show();
                }
            }

        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registro.class);

                startActivity(intent);



                finish();




                //fragmentTransaction.add(homeViewModel, homeFragment).commit();


            }
        });
    }

    private void enviar(String name, String correo, int tipo) {

        editor.putString("correo", correo);
        editor.putString("nombre", name);
        editor.putInt("tipo", tipo);
        editor.commit();

        System.out.println("res: "+correo+" "+correo);

    }
}






