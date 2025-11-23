package com.m.d.f.miagenda.presentacion.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Evento;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private Context ctx;
    private List<Evento> lista;

    public EventoAdapter(Context ctx, List<Evento> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_evento, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evento evento = lista.get(position);

        holder.titulo.setText(evento.getTitulo());
        holder.fecha.setText(evento.getFecha());
        holder.descripcion.setText(evento.getDescripcion());

        holder.itemView.setOnClickListener(v -> {
            // Devolver ID a la Activity que llamó
            Intent data = new Intent();
            data.putExtra("eventoId", evento.getId());

            ((Activity) ctx).setResult(Activity.RESULT_OK, data);
            ((Activity) ctx).finish();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void actualizarLista(List<Evento> nuevaLista) {
        lista = nuevaLista;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, fecha, descripcion;

        public ViewHolder(@NonNull View item) {
            super(item);
            titulo = item.findViewById(R.id.txtTituloEvento);
            fecha = item.findViewById(R.id.txtFecha);
            descripcion = item.findViewById(R.id.txtDescripcionEvento);
        }
    }
}
