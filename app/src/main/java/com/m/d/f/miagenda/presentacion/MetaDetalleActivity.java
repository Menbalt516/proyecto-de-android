package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.datos.EventoMetaDAO;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.presentacion.adapters.EventoAdapter;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.List;

public class MetaDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_META_ID = "meta_id";

    private TextView txtTitulo, txtDescripcion, txtPorcentajeMeta;
    private ProgressBar progressBar;
    private Button btnEditarProgreso, btnAgregarSubMeta, btnVincularEvento;
    private RecyclerView rvSubMetas, rvEventos;

    private MetaDAO metaDAO;
    private SubMetaDAO subMetaDAO;
    private EventoDAO eventoDAO;
    private EventoMetaDAO eventoMetaDAO;

    private Meta meta;
    private int metaId;

    // Adaptadores
    private SubMetaAdapter subMetaAdapter;
    private EventoAdapter eventoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_detalle);

        // Inicializar DAOs
        metaDAO = new MetaDAO(this);
        subMetaDAO = new SubMetaDAO(this);
        eventoDAO = new EventoDAO(this);
        eventoMetaDAO = new EventoMetaDAO(this);

        // Inicializar vistas
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtPorcentajeMeta = findViewById(R.id.txtPorcentajeMeta);
        progressBar = findViewById(R.id.progressBar);
        btnEditarProgreso = findViewById(R.id.btnEditarProgreso);
        btnAgregarSubMeta = findViewById(R.id.btnAgregarSubMeta);
        btnVincularEvento = findViewById(R.id.btnVincularEvento);
        rvSubMetas = findViewById(R.id.rvSubMetas);
        rvEventos = findViewById(R.id.rvEventos);

        // Configurar RecyclerViews
        rvSubMetas.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setLayoutManager(new LinearLayoutManager(this));

        // Obtener ID de la Meta
        metaId = getIntent().getIntExtra(EXTRA_META_ID, -1);
        if (metaId == -1) {
            Toast.makeText(this, "Meta no encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarMeta();

        // Botón para ajustar progreso manualmente
        btnEditarProgreso.setOnClickListener(v -> mostrarDialogoProgreso());

        // Botón para agregar Sub-Meta
        btnAgregarSubMeta.setOnClickListener(v -> {
            Intent i = new Intent(this, CrearEditarSubmetaActivity.class);
            i.putExtra(CrearEditarSubmetaActivity.EXTRA_META_ID, metaId);
            startActivity(i);
        });

        // Botón para vincular Evento
        btnVincularEvento.setOnClickListener(v -> {
            Intent i = new Intent(this, EventosListActivity.class);
            i.putExtra(EventosListActivity.EXTRA_META_ID, metaId);
            startActivity(i);
        });
    }

    private void cargarMeta() {
        meta = metaDAO.obtenerPorId(metaId);
        if (meta == null) return;

        txtTitulo.setText(meta.getTitulo());
        txtDescripcion.setText(meta.getDescripcion());
        progressBar.setProgress(Math.round(meta.getProgreso()));
        txtPorcentajeMeta.setText(Math.round(meta.getProgreso()) + "%");

        // Cargar Sub-Metas
        List<SubMeta> subMetas = subMetaDAO.listarPorMeta(metaId);
        subMetaAdapter = new SubMetaAdapter(subMetas, this, metaId);

        rvSubMetas.setAdapter(subMetaAdapter);

        // Cargar Eventos vinculados
        List<Evento> eventos = eventoMetaDAO.listarEventosPorMeta(metaId);
        eventoAdapter = new EventoAdapter(eventos, new EventoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Evento evento) {
                // Aquí manejas el clic (si quieres abrir detalles)
            }
        });
        rvEventos.setAdapter(eventoAdapter);
    }

    private void mostrarDialogoProgreso() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajustar Progreso Manual");

        final SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(100);
        seekBar.setProgress(Math.round(meta.getProgreso()));
        builder.setView(seekBar);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            int nuevoProgreso = seekBar.getProgress();
            meta.setProgreso(nuevoProgreso);
            metaDAO.actualizar(meta);
            progressBar.setProgress(nuevoProgreso);
            txtPorcentajeMeta.setText(nuevoProgreso + "%");
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar Sub-Metas y Eventos por si hubo cambios
        if (meta != null) cargarMeta();
    }
}
