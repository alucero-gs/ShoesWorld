package com.example.shoesworld;

public class Usuarios {
    String nombre, correo, pass, id;
    int tipo, status;

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Usuarios(String nombre, String correo, String pass, int status) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
        this.status = status;
    }

    public Usuarios() {
    }
}
