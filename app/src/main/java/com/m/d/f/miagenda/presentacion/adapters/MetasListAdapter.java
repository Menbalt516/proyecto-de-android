package com.m.d.f.miagenda.presentacion.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Meta;

import java.util.List;

public class MetasListAdapter extends RecyclerView.Adapter<MetasListAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Meta meta, boolean esSubMeta);
    }

    private List<Meta> metaList;
    private OnItemClickListener listener;
    private boolean mostrarSubMetas;

    public MetasListAdapter(List<Meta> metaList, OnItemClickListener listener, boolean mostrarSubMetas) {
        this.metaList = metaList;
        this.listener = listener;
        this.mostrarSubMetas = mostrarSubMetas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta_simple, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meta m = metaList.get(position);
        holder.txtTitulo.setText(m.getTitulo());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(m, mostrarSubMetas));
    }

    @Override
    public int getItemCount() {
        return metaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloItem);
        }
    }
}
