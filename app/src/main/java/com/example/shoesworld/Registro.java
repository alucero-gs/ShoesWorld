package com.example.shoesworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shoesworld.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
Button btn_regis;
EditText et_correo, et_pass, et_nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btn_regis=findViewById(R.id.btn_registrar);
        et_correo= findViewById(R.id.et_correo);
        et_pass=findViewById(R.id.et_pass);
        et_nombre=findViewById(R.id.et_nombre);
        System.out.println(et_correo.getText().toString());



        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=et_nombre.getText().toString().replaceAll(" ", "");
                String correo= et_correo.getText().toString().replaceAll(" ", "");
                String pass= et_pass.getText().toString().replaceAll(" ", "");
                boolean res= verificar(nombre,correo, pass);

                if(res)
                {
                    try
                    {
                        FirebaseAuth auth;
                        auth=FirebaseAuth.getInstance();
                        auth.createUserWithEmailAndPassword(correo, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseDatabase firebaseDatabase;
                                    DatabaseReference databaseReference;
                                    FirebaseApp.initializeApp(Registro.this);
                                    firebaseDatabase = FirebaseDatabase.getInstance();
                                    databaseReference = firebaseDatabase.getReference();

                                    String key = databaseReference.push().getKey();

                                    Usuarios h= new Usuarios();
                                    h.setId(key);
                                    h.setNombre(nombre);
                                    h.setCorreo(correo);
                                    h.setPass(pass);
                                    h.setTipo(2);
                                    h.setStatus(1);
                                    databaseReference.child("usuarios").child(key).setValue(h);
                                    Toast.makeText(Registro.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                                    SharedPreferences prefs = getSharedPreferences("shared_login_data",   Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("correo", correo);
                                    editor.putString("nombre", nombre);
                                    editor.commit();

                                    Intent intent = new Intent(Registro.this, Principal.class);
                                    startActivity(intent);
                                    finish();


                                }else{
                                    Toast.makeText(Registro.this, "No se puede registrar, consulte a su administrador", Toast.LENGTH_LONG).show();

                                }
                            }
                        });



                    }catch (Exception e){
                        Toast.makeText(Registro.this, "Ocurrió error", Toast.LENGTH_LONG).show();
                        System.out.println(e.getMessage());
                    }


                }else{
                    Toast.makeText(Registro.this, "Escribe un correo válido", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean verificar(String nombre, String correo, String pass) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        boolean resp_correo= pattern.matcher(correo).matches();

        if (nombre.length()>1)
        {
            if (resp_correo)
            {
                if (pass.length()>=6)
                {
                    return true;
                }
                else
                    {
                    Toast.makeText(Registro.this, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_LONG).show();
                    return false;
                    }
            }
            else
                {
                    Toast.makeText(Registro.this, "Correo inválido", Toast.LENGTH_LONG).show();
                    return false;

                }
        }else
            {
                Toast.makeText(Registro.this, "Tu nombre debe tener más de una letra", Toast.LENGTH_LONG).show();
                return false;
            }

    }



}