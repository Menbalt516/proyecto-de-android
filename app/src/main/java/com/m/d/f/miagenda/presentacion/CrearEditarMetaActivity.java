package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.datos.EventoMetaDAO;
import com.m.d.f.miagenda.modelos.Meta;


public class CrearEditarMetaActivity extends AppCompatActivity {
    public static final String EXTRA_VINCLUIR_DESDE_META = "vincular_desde_meta";
    public static final String EXTRA_META_ID = "meta_id";

    private EditText edtTitulo, edtDescripcion;
    private SeekBar seekProgreso;
    private TextView txtProgreso;
    private CheckBox chkCompletada;
    private Button btnGuardar, btnCrearEventoVinculado, btnSeleccionarEvento;

    private MetaDAO metaDAO;
    private EventoMetaDAO eventoMetaDAO;

    private int editarId = -1;
    private Meta meta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_meta);

        edtTitulo = findViewById(R.id.edtTituloMeta);
        edtDescripcion = findViewById(R.id.edtDescripcionMeta);
        seekProgreso = findViewById(R.id.seekProgresoMeta);
        txtProgreso = findViewById(R.id.txtProgresoMeta);
        chkCompletada = findViewById(R.id.chkCompletadaMeta);
        btnGuardar = findViewById(R.id.btnGuardarMeta);
        btnCrearEventoVinculado = findViewById(R.id.btnCrearEventoDesdeMeta);
        btnSeleccionarEvento = findViewById(R.id.btnSeleccionarEventoMeta);

        metaDAO = new MetaDAO(this);
        eventoMetaDAO = new EventoMetaDAO(this);

        editarId = getIntent().getIntExtra(EXTRA_META_ID, -1);

        seekProgreso.setMax(100);
        seekProgreso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtProgreso.setText(progress + "%");
                chkCompletada.setChecked(progress >= 100);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        if (editarId != -1) loadMeta(editarId);

        btnGuardar.setOnClickListener(v -> guardarMeta());

        btnCrearEventoVinculado.setOnClickListener(v -> {
            // Abrir crear evento y volver con resultado para vincular
            Intent i = new Intent(this, CrearEditarEventoActivity.class);
            i.putExtra(CrearEditarEventoActivity.EXTRA_VINCLUIR_DESDE_META, true);
            //i.putExtra(CrearEditarEventoActivity.EXTRA_META_ID, metaIdSeleccionada);
            startActivity(i);

        });

        btnSeleccionarEvento.setOnClickListener(v -> {
            // Abrir Activity que liste eventos para seleccionar (implementa EventosListActivity)
            Intent i = new Intent(this, EventosListActivity.class);
            // espera resultado con eventoId seleccionado
            startActivityForResult(i, 1002);
        });
    }

    //private void loadMeta(int id) {
       // meta = metaDAO.obtenerPorId(id);
        //if (meta == null) return;
        //edtTitulo.setText(meta.getTitulo());
        //edtDescripcion.setText(meta.getDescripcion());
        //int p = Math.round(meta.getProgreso());
        //seekProgreso.setProgress(p);
        //txtProgreso.setText(p + "%");
        //chkCompletada.setChecked(meta.getCompletada());
    //}
    private void loadMeta(int id) {
        meta = metaDAO.obtenerPorId(id);

        // 🛑 CORRECCIÓN: Si no se encuentra la meta, notificar, cerrar y evitar el NPE.
        if (meta == null) {
            Toast.makeText(this, "Error: La Meta a editar no fue encontrada.", Toast.LENGTH_LONG).show();
            finish(); // Cierra la Activity inmediatamente.
            return;
        }

        // Si se encuentra, cargar la UI
        edtTitulo.setText(meta.getTitulo());
        edtDescripcion.setText(meta.getDescripcion());
        int p = Math.round(meta.getProgreso());
        seekProgreso.setProgress(p);
        txtProgreso.setText(p + "%");
        chkCompletada.setChecked(meta.getCompletada());
    }

    private void guardarMeta() {
        String t = edtTitulo.getText().toString().trim();
        String d = edtDescripcion.getText().toString().trim();
        if (t.isEmpty()) {
            Toast.makeText(this, "Título requerido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editarId == -1) {
            Meta m = new Meta();
            m.setTitulo(t);
            m.setDescripcion(d);
            m.setProgreso(seekProgreso.getProgress());
            m.setCompletada(chkCompletada.isChecked());
            long id = metaDAO.insertar(m);
            if (id > 0) {
                Toast.makeText(this, "Meta creada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al crear meta", Toast.LENGTH_SHORT).show();
            }
        } else {
            meta.setTitulo(t);
            meta.setDescripcion(d);
            meta.setProgreso(seekProgreso.getProgress());
            meta.setCompletada(chkCompletada.isChecked());
            boolean ok = metaDAO.actualizar(meta);
            if (ok) {
                Toast.makeText(this, "Meta actualizada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 1001 = crear evento y vincular
        // 1002 = seleccionar evento existente desde EventosListActivity que devuelva extra "eventoId"
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1001 || requestCode == 1002) {
                int eventoId = data.getIntExtra("eventoId", -1);
                // si la meta no tiene id (se acaba de crear) pedimos recargar; aquí asumimos que editarId tiene valor
                int metaIdParaVincular = editarId;
                if (metaIdParaVincular == -1) {
                    // Si la meta aún no fue creada -> crearla primero con los campos actuales
                    Meta m = new Meta();
                    m.setTitulo(edtTitulo.getText().toString().trim());
                    m.setDescripcion(edtDescripcion.getText().toString().trim());
                    m.setProgreso(seekProgreso.getProgress());
                    m.setCompletada(chkCompletada.isChecked());
                    long nid = metaDAO.insertar(m);
                    if (nid > 0) {
                        metaIdParaVincular = (int) nid;
                        editarId = metaIdParaVincular;
                    } else {
                        Toast.makeText(this, "No se pudo crear meta para vincular", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (eventoId != -1) {
                    eventoMetaDAO.vincular(eventoId, metaIdParaVincular);
                    Toast.makeText(this, "Evento vinculado a la meta", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
