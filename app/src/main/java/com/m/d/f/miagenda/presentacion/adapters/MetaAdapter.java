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
import com.m.d.f.miagenda.negocio.SubMetaNegocio;
import com.m.d.f.miagenda.presentacion.SubMetasActivity;

import java.util.List;

public class MetaAdapter extends RecyclerView.Adapter<MetaAdapter.MetaViewHolder> {

    private Context context;
    private List<Meta> metaList;

    // Constructor
    public MetaAdapter(Context context, List<Meta> metaList) {
        this.context = context;
        this.metaList = metaList;
    }

    @NonNull
    @Override
    public MetaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meta, parent, false);
        return new MetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetaViewHolder holder, int position) {
        Meta meta = metaList.get(position);
        //int metaId = meta.getId();

        holder.txtTitulo.setText(meta.getTitulo());
        holder.txtDescripcion.setText(meta.getDescripcion());
        holder.progresoMeta.setProgress((int) meta.getProgreso());
        holder.txtPorcentaje.setText((int) meta.getProgreso() + "%");

        // Click en el item para abrir SubMetasActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SubMetasActivity.class);
            intent.putExtra("metaId", meta.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return metaList.size();
    }

    // Actualiza la lista completa
    public void actualizarLista(List<Meta> nuevasMetas) {
        metaList.clear();
        metaList.addAll(nuevasMetas);
        notifyDataSetChanged();
    }

    // Actualiza solo el progreso de una meta
    public void actualizarProgreso(int metaId, int porcentaje) {
        for (Meta m : metaList) {
            if (m.getId() == metaId) {
                m.setProgreso(porcentaje);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public static class MetaViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtDescripcion, txtPorcentaje;
        ProgressBar progresoMeta;

        public MetaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTituloMeta);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionMeta);
            progresoMeta = itemView.findViewById(R.id.progresoMeta);
            txtPorcentaje = itemView.findViewById(R.id.txtPorcentaje);
        }
    }
}
