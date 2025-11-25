package com.m.d.f.miagenda.presentacion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;
import com.m.d.f.miagenda.negocio.MetaNegocio;

import java.util.List;

public class SubMetaAdapter extends RecyclerView.Adapter<SubMetaAdapter.ViewHolder> {

    private List<SubMeta> lista;
    private Context context;
    private SubMetaNegocio subMetaNegocio;
    private MetaNegocio metaNegocio;
    private int parentMetaId;

    public SubMetaAdapter(List<SubMeta> lista, Context context, int parentMetaId) {
        this.lista = lista;
        this.context = context;
        this.parentMetaId = parentMetaId;
        this.subMetaNegocio = new SubMetaNegocio(context);
        this.metaNegocio = new MetaNegocio(context);
    }

    @NonNull
    @Override
    public SubMetaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_submeta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubMetaAdapter.ViewHolder holder, int position) {
        SubMeta s = lista.get(position);
        holder.txtTitulo.setText(s.getTitulo());
        holder.chkCompletada.setChecked(s.isCompletada());

        holder.chkCompletada.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // actualizar objeto y persistir
            s.setCompletada(isChecked);
            boolean ok = subMetaNegocio.actualizarSubMeta(s);
            if (!ok) {
                Toast.makeText(context, "No se pudo actualizar sub-meta", Toast.LENGTH_SHORT).show();
            } else {
                // recalcular progreso de la meta (SubMetaDAO ya lo hace, pero reforzamos recarga si queremos UI inmediata)
                metaNegocio.recalcularProgreso(parentMetaId);
            }
        });

        holder.btnEditar.setOnClickListener(v -> {
            // aquí podrías abrir CrearSubMetaActivity en modo edición
            Toast.makeText(context, "Editar SubMeta: " + s.getTitulo(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox chkCompletada;
        TextView txtTitulo;
        ImageButton btnEditar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chkCompletada = itemView.findViewById(R.id.chkCompletada);
            txtTitulo = itemView.findViewById(R.id.txtTituloSubMeta);
            btnEditar = itemView.findViewById(R.id.btnEditarSubMeta);
        }
    }
}
