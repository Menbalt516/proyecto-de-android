package com.m.d.f.miagenda.presentacion.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.presentacion.MetasDetalleActivity;

import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.ViewHolder> {

    private List<Meta> metaList;
    private Context context;

    public MetaAdapter(List<Meta> metaList, Context context) {
        this.metaList = metaList;
        this.context = context;
    }

    @NonNull
    @Override
    public MetaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaAdapter.ViewHolder holder, int position) {
        Meta m = metaList.get(position);
        holder.txtTitulo.setText(m.getTitulo());
        holder.txtDescripcion.setText(m.getDescripcion());

        int progreso = Math.round(m.getProgreso()); // 0..100
        holder.progressBar.setProgress(progreso);
        holder.txtPorcentaje.setText(progreso + "%");

        if (m.getCompletada()) {
            holder.progressBar.setProgress(100);
            holder.txtPorcentaje.setText("100%");
        }

        //holder.itemView.setOnClickListener(v -> {
            //Intent i = new Intent(context, MetasDetalleActivity.class);
           // i.putExtra("metaId", m.getId());
            //context.startActivity(i);
        //});
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), MetasDetalleActivity.class);
            i.putExtra("metaId", m.getId());
            v.getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return metaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtPorcentaje;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloMeta);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionMeta);
            txtPorcentaje = itemView.findViewById(R.id.txtProgresoMeta);
            progressBar = itemView.findViewById(R.id.progressMeta);
        }
    }
}
