package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.MetaNegocio;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.ArrayList;

public class MetasDetalleActivity extends AppCompatActivity {

    private TextView txtTitulo, txtDescripcion, txtPorcentaje;
    private ProgressBar progressBar;
    private RecyclerView rvSubMetas;
    private Button btnAgregarSubMeta, btnAjustarProgreso, btnVincularEvento;
    private MetaNegocio metaNegocio;
    private SubMetaNegocio subMetaNegocio;
    private int metaId;
    private Meta meta;
    private ArrayList<SubMeta> subMetas;
    private SubMetaAdapter subMetaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_detalle); // usa el xml que definimos

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPorcentaje = findViewById(R.id.txtPorcentajeMeta);
        progressBar = findViewById(R.id.progressBar);
        rvSubMetas = findViewById(R.id.rvSubMetas);
        btnAgregarSubMeta = findViewById(R.id.btnAgregarSubMeta);
        btnAjustarProgreso = findViewById(R.id.btnEditarProgreso);
        btnVincularEvento = findViewById(R.id.btnVincularEvento);

        metaNegocio = new MetaNegocio(this);
        subMetaNegocio = new SubMetaNegocio(this);
        subMetas = new ArrayList<>();

        rvSubMetas.setLayoutManager(new LinearLayoutManager(this));

        metaId = getIntent().getIntExtra("metaId", -1);

        btnAgregarSubMeta.setOnClickListener(v -> {
            Intent i = new Intent(this, CrearSubMetaActivity.class);
            i.putExtra("metaId", metaId);
            startActivity(i);
        });

        btnAjustarProgreso.setOnClickListener(v -> {
            // abrir dialogo seekbar para ajustar progreso manual (impleméntalo o reutiliza progress_manual_dialog.xml)
            android.app.AlertDialog.Builder b = new android.app.AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.progress_manual_dialog, null);
            android.widget.SeekBar seek = view.findViewById(R.id.seekProgreso);
            android.widget.TextView txt = view.findViewById(R.id.txtPorcentajeSeek);
            seek.setProgress(Math.round(meta != null ? meta.getProgreso() : 0));
            txt.setText(Math.round(seek.getProgress()) + "%");
            seek.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
                @Override public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) { txt.setText(progress + "%"); }
                @Override public void onStartTrackingTouch(android.widget.SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(android.widget.SeekBar seekBar) {}
            });
            b.setView(view);
            b.setPositiveButton("Aplicar", (dialog, which) -> {
                int p = seek.getProgress();
                meta.setProgreso(p);
                meta.setCompletada(p >= 100);
                metaNegocio.actualizarMeta(meta);
                refrescar();
            });
            b.setNegativeButton("Cancelar", null);
            b.show();
        });

        btnVincularEvento.setOnClickListener(v -> {
            // implementar selector de eventos o crear evento prellenado
            startActivity(new Intent(this, EventosListActivity.class)); // placeholder
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescar();
    }

    private void refrescar() {
        if (metaId == -1) return;
        // recalcular por si cambió alguna submeta
        metaNegocio.recalcularProgreso(metaId);
        meta = metaNegocio.obtener(metaId);
        if (meta == null) return;

        txtTitulo.setText(meta.getTitulo());
        txtDescripcion.setText(meta.getDescripcion());
        int p = Math.round(meta.getProgreso());
        progressBar.setProgress(p);
        txtPorcentaje.setText(p + "%");

        // cargar submetas
        subMetas.clear();
        subMetas.addAll(subMetaNegocio.listarPorMeta(metaId));
        subMetaAdapter = new SubMetaAdapter(subMetas, this, metaId);
        rvSubMetas.setAdapter(subMetaAdapter);
    }
}
