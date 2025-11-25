package com.m.d.f.miagenda.presentacion.adapters;

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

    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }

    private final List<Evento> eventoList;
    private final OnItemClickListener listener;

    public EventoAdapter(List<Evento> eventoList, OnItemClickListener listener) {
        this.eventoList = eventoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evento evento = eventoList.get(position);
        holder.bind(evento, listener);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtFechaHora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloEvento);
            txtFechaHora = itemView.findViewById(R.id.txtFechaHoraEvento);
        }

        public void bind(final Evento evento, final OnItemClickListener listener) {
            txtTitulo.setText(evento.getTitulo());
            txtFechaHora.setText(evento.getFechaString() + " " + evento.getHora());

            itemView.setOnClickListener(v -> listener.onItemClick(evento));
        }
    }
}
