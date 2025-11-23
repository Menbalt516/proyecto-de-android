package com.m.d.f.miagenda.presentacion.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;

import java.util.List;

public class SubMetaAdapter extends RecyclerView.Adapter<SubMetaAdapter.ViewHolder> {

    private List<SubMeta> lista;
    private Context ctx;
    private SubMetaNegocio negocio;


    public SubMetaAdapter(List<SubMeta> lista, Context ctx, SubMetaNegocio negocio) {
        this.lista = lista;
        this.ctx = ctx;
        this.negocio = negocio;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_submeta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int pos) {
        SubMeta s = lista.get(pos);

        h.titulo.setText(s.getTitulo());
        h.chk.setChecked(s.isCompletada());


        h.chk.setOnClickListener(v -> {
            s.setCompletada(h.chk.isChecked());
            negocio.actualizarSubMeta(s);
        });

        h.btnEliminar.setOnClickListener(v -> {
            negocio.eliminarSubMeta(s.getId(), s.getMetaId());
            lista.remove(pos);
            notifyItemRemoved(pos);
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        CheckBox chk;
        Button btnEliminar;

        public ViewHolder(View item) {
            super(item);
            titulo = item.findViewById(R.id.txtTituloSubmeta);
            chk = item.findViewById(R.id.chkCompletado);
            btnEliminar = item.findViewById(R.id.btnEliminarSubMeta);
        }
    }
    public void actualizarLista(List<SubMeta> nuevasSubMetas) {
        this.lista.clear();
        this.lista.addAll(nuevasSubMetas);
        notifyDataSetChanged();
    }

}
