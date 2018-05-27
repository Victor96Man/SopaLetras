package com.example.sopadeletras;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ListaViewHolder> implements View.OnClickListener{

    private ArrayList<ListaItem> items;
    private View.OnClickListener listener;
    public static class ListaViewHolder extends RecyclerView.ViewHolder {

        private TextView TextView_categoria;


        public ListaViewHolder(View itemView) {
            super(itemView);

            TextView_categoria = itemView.findViewById(R.id.TextCategoria);


        }

        public void FormularioBind(ListaItem item) {

            TextView_categoria.setText(item.getNombre());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public Adaptador(@NonNull ArrayList < ListaItem > items) {
        this.items = items;
    }



    // Se encarga de crear los nuevos objetos ViewHolder necesarios para los elementos de la colección.
    // Infla la vista del layout y crea y devuelve el objeto ViewHolder
    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowlistasopas, parent, false);
        row.setOnClickListener(this);
        ListaViewHolder lvh = new ListaViewHolder(row);
        return lvh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, int position) {
        ListaItem item = items.get(position);
        viewHolder.FormularioBind(item);
    }
    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return  items.size();
    }
    // Asigna un listener
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }
}

