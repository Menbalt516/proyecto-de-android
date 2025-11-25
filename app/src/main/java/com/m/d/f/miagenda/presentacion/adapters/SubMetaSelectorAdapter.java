package com.m.d.f.miagenda.presentacion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMeta;

import java.util.List;

public class SubMetaSelectorAdapter extends RecyclerView.Adapter<SubMetaSelectorAdapter.ViewHolder> {

    public interface OnSubMetaClickListener {
        void onSubMetaSelected(SubMeta subMeta);
    }

    private List<SubMeta> lista;
    private OnSubMetaClickListener listener;

    public SubMetaSelectorAdapter(List<SubMeta> lista, OnSubMetaClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_submeta_selector, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubMeta s = lista.get(position);

        holder.titulo.setText(s.getTitulo());
        holder.estado.setText(s.isCompletada() ? "Completada" : "Pendiente");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onSubMetaSelected(s);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtSubMetaTitulo);
            estado = itemView.findViewById(R.id.txtSubMetaEstado);
        }
    }
}
