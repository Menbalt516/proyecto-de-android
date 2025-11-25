package com.m.d.f.miagenda.presentacion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Evento;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventoSelectorAdapter extends RecyclerView.Adapter<EventoSelectorAdapter.ViewHolder> {

    public interface OnEventoClickListener {
        void onEventoSelected(Evento evento);
    }

    private List<Evento> lista;
    private OnEventoClickListener listener;

    public EventoSelectorAdapter(List<Evento> lista, OnEventoClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_evento_selector, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evento e = lista.get(position);

        holder.titulo.setText(e.getTitulo());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.fecha.setText(sdf.format(e.getFecha()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEventoSelected(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, fecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtTitulo);
            fecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
