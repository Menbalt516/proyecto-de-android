package com.m.d.f.miagenda.presentacion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.presentacion.MetaListItem.MetaListItem;

import java.util.List;

public class MetasSeccionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MetaListItem> items;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(MetaListItem item);
    }

    public MetasSeccionAdapter(List<MetaListItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).tipo == MetaListItem.Tipo.SECCION ? 0 : 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seccion, parent, false);
            return new SeccionViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta_simple, parent, false);
            return new ItemViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MetaListItem item = items.get(position);
        if (item.tipo == MetaListItem.Tipo.SECCION) {
            ((SeccionViewHolder) holder).txtSeccion.setText(item.titulo);
        } else {
            ((ItemViewHolder) holder).txtTitulo.setText(item.titulo);
            holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SeccionViewHolder extends RecyclerView.ViewHolder {
        TextView txtSeccion;
        SeccionViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSeccion = itemView.findViewById(R.id.txtSeccion);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;
        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloItem);
        }
    }
}
