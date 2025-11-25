package com.m.d.f.miagenda.presentacion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.datos.EventoMetaDAO;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.presentacion.adapters.EventoAdapter;
import com.m.d.f.miagenda.presentacion.adapters.MetaAdapter;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.ArrayList;
import java.util.List;

public class MetasActivity extends AppCompatActivity {

    public static final String EXTRA_META_ID = "meta_id";

    private TextView txtTitulo, txtDescripcion, txtPorcentajeMeta;
    private ProgressBar progressBar;
    private Button btnCrearMeta, btnAgregarSubMeta, btnVincularEvento;
    private RecyclerView rvSubMetas, rvEventos;

    private MetaDAO metaDAO;
    private SubMetaDAO subMetaDAO;
    private EventoDAO eventoDAO;
    private EventoMetaDAO eventoMetaDAO;

    private Meta meta;
    private int metaId;
    private int idMeta;


    // Adaptadores
    private SubMetaAdapter subMetaAdapter;
    private EventoAdapter eventoAdapter;
    private MetaAdapter metaListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metas);

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
        btnCrearMeta = findViewById(R.id.btnCrearMeta);
        btnAgregarSubMeta = findViewById(R.id.btnAgregarSubMeta);
        btnVincularEvento = findViewById(R.id.btnVincularEvento);
        rvSubMetas = findViewById(R.id.rvSubMetas);
        rvEventos = findViewById(R.id.rvEventos);

        rvSubMetas.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setLayoutManager(new LinearLayoutManager(this));

        metaId = getIntent().getIntExtra(EXTRA_META_ID, -1);

        // Configuración de Listeners
        btnCrearMeta.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearEditarMetaActivity.class);
            startActivity(intent);
        });

        btnAgregarSubMeta.setOnClickListener(v -> {
            // Asegúrate de pasar el metaId si es válido
            if (metaId != -1) {
                Intent i = new Intent(this, CrearEditarSubmetaActivity.class);
                i.putExtra(CrearEditarSubmetaActivity.EXTRA_META_ID, metaId);
                startActivity(i);
            } else {
                Toast.makeText(this, "Meta no cargada. No se puede agregar Sub-Meta.", Toast.LENGTH_SHORT).show();
            }
        });

        btnVincularEvento.setOnClickListener(v -> {
            // Asegúrate de pasar el metaId si es válido
            if (metaId != -1) {
                Intent i = new Intent(this, EventosListActivity.class);
                i.putExtra(EventosListActivity.EXTRA_META_ID, metaId);
                startActivity(i);
            } else {
                Toast.makeText(this, "Meta no cargada. No se puede vincular Evento.", Toast.LENGTH_SHORT).show();
            }
        });
        if (metaId != -1) {
            cargarMeta();
        } else {
            Toast.makeText(this, "Esta Activity se usa para mostrar una Meta específica. ID no encontrado.", Toast.LENGTH_LONG).show();
        }
    }
    private void cargarMeta() {
        // 1. Obtener la meta de la base de datos
        meta = metaDAO.obtenerPorId(metaId);

        // Manejar el caso de que la meta no exista
        if (meta == null) {
            Toast.makeText(this, "Meta no encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 2. Mostrar la información y el progreso en las vistas
        txtTitulo.setText(meta.getTitulo());
        txtDescripcion.setText(meta.getDescripcion());

        // Asegura que el progreso se muestre en el ProgressBar y el TextView
        progressBar.setProgress(Math.round(meta.getProgreso()));
        txtPorcentajeMeta.setText(Math.round(meta.getProgreso()) + "%");

        // 3. Cargar Sub-Metas (lista)
        List<SubMeta> subMetas = subMetaDAO.listarPorMeta(metaId);
        subMetaAdapter = new SubMetaAdapter(subMetas, this, metaId);
        rvSubMetas.setAdapter(subMetaAdapter);

        // 4. Cargar Eventos vinculados (lista)
        List<Evento> eventos = eventoMetaDAO.listarEventosPorMeta(metaId);
        eventoAdapter = new EventoAdapter(eventos, evento -> {
            Toast.makeText(this, "Evento: " + evento.getTitulo(), Toast.LENGTH_SHORT).show();
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

        // La lógica de carga debe ejecutarse aquí también, por si regresamos de CrearEditarMetaActivity
        metaId = getIntent().getIntExtra(EXTRA_META_ID, -1);

        if (metaId != -1) {
            // Modo 1: Ver detalles de una Meta específica
            cargarMeta();
        } else {
            // Modo 2: Ver la lista completa de Metas
            cargarListaDeMetas();
        }
    }
    private void cargarListaDeMetas() {
        // 1. Obtener la lista de todas las metas
        ArrayList<Meta> todasLasMetas = metaDAO.listar();

        // Usaremos el rvSubMetas temporalmente si no tienes uno llamado rvMetas
        metaListAdapter = new MetaAdapter(todasLasMetas, this);
        rvSubMetas.setAdapter(metaListAdapter);
    }
}
