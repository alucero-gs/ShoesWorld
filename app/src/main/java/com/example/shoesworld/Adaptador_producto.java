package com.example.shoesworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adaptador_producto extends RecyclerView.Adapter<Adaptador_producto.ViewHolder>  {
    private ArrayList<Productos> model;
    LayoutInflater inflater;


    public Adaptador_producto(Context context, ArrayList<Productos> model){
        this.inflater=LayoutInflater.from(context);
        this.model=model;

    }

    @NonNull
    @NotNull

    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.item_producto, parent, false);

        return new ViewHolder(view);
    }




    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String nombre=model.get(position).getNombre();
        String id=model.get(position).getId();
        holder.nombre.setText(nombre);
        holder.id.setText(id);


    }


    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre;
        TextView id;
        ImageButton ib_info, ib_editar, ib_borrar;
        ImageView foto;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            nombre=itemView.findViewById(R.id.tv_nombre_item);
            id=itemView.findViewById(R.id.tv_id_item);
            ib_info=itemView.findViewById(R.id.ib_info_item);
            ib_editar=itemView.findViewById(R.id.ib_editar_item);
            ib_borrar=itemView.findViewById(R.id.ib_borrar_item);
        }
    }

}
