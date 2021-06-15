package com.example.shoesworld;

public class Productos {
    String nombre, id;
    int imagen;

    public Productos() {
    }

    public Productos(String nombre, String id, int imagen) {
        this.nombre = nombre;
        this.id = id;
        this.imagen=imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
