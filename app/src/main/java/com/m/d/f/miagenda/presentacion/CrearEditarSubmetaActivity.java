package com.m.d.f.miagenda.presentacion;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.datos.SubMetaEventoDAO;
import com.m.d.f.miagenda.modelos.SubMeta;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMetaEvento;

public class CrearEditarSubmetaActivity extends AppCompatActivity {

    public static final String EXTRA_META_ID = "metaId";
    public static final String EXTRA_SUBMETA_ID = "subMetaId";

    private EditText edtTitulo, edtDescripcion;
    private CheckBox chkCompletada;
    private Button btnGuardar, btnCrearEvento, btnSeleccionarEvento;
    private SubMetaDAO subMetaDAO;
    private SubMetaEventoDAO eventoSubMetaDAO;

    private int metaId;
    private int editarId = -1;
    private SubMeta subMeta;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_submeta);

        edtTitulo = findViewById(R.id.edtTituloSubMeta);
        edtDescripcion = findViewById(R.id.edtDescripcionSubMeta);
        chkCompletada = findViewById(R.id.chkCompletadaSubMeta);
        btnGuardar = findViewById(R.id.btnGuardarSubMeta);
        btnCrearEvento = findViewById(R.id.btnCrearEventoDesdeSubMeta);
        btnSeleccionarEvento = findViewById(R.id.btnSeleccionarEventoSubMeta);

        subMetaDAO = new SubMetaDAO(this);
        eventoSubMetaDAO = new SubMetaEventoDAO(this);

        metaId = getIntent().getIntExtra(EXTRA_META_ID, -1);
        editarId = getIntent().getIntExtra(EXTRA_SUBMETA_ID, -1);

        if (editarId != -1) cargarSubMeta(editarId);

        btnGuardar.setOnClickListener(v -> guardarSubMeta());

        btnCrearEvento.setOnClickListener(v -> {
            Intent i = new Intent(this, CrearEditarEventoActivity.class);
            i.putExtra(CrearEditarEventoActivity.EXTRA_VINCLUIR_DESDE_SUBMETA, true);
            i.putExtra(CrearEditarEventoActivity.EXTRA_SUBMETA_ID, editarId);
            i.putExtra(CrearEditarEventoActivity.EXTRA_META_ID, metaId);
            startActivityForResult(i, 2001);
        });

        btnSeleccionarEvento.setOnClickListener(v -> {
            Intent i = new Intent(this, EventosListActivity.class);
            startActivityForResult(i, 2002);
        });
    }

    private void cargarSubMeta(int id) {
        subMeta = subMetaDAO.obtener(id);
        if (subMeta == null) return;
        edtTitulo.setText(subMeta.getTitulo());
        edtDescripcion.setText(subMeta.getDescripcion());
        chkCompletada.setChecked(subMeta.isCompletada());
        //subMeta.setCompletada(chkCompletada.isChecked());

    }

    private void guardarSubMeta() {
        String t = edtTitulo.getText().toString().trim();
        String d = edtDescripcion.getText().toString().trim();
        if (t.isEmpty()) {
            Toast.makeText(this, "Título requerido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editarId == -1) {
            SubMeta s = new SubMeta();
            s.setMetaId(metaId);
            s.setTitulo(t);
            s.setDescripcion(d);
            s.setCompletada(chkCompletada.isChecked());
            long id = subMetaDAO.insertar(s);
            if (id > 0) {
                Toast.makeText(this, "SubMeta creada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al crear submeta", Toast.LENGTH_SHORT).show();
            }
        } else {
            subMeta.setTitulo(t);
            subMeta.setDescripcion(d);
            subMeta.setCompletada(chkCompletada.isChecked());
            boolean ok = subMetaDAO.actualizar(subMeta);
            if (ok) {
                Toast.makeText(this, "SubMeta actualizada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar submeta", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 2001 = crear evento y vincular → devuelve eventoId
        // 2002 = seleccionar evento existente
        if (resultCode == RESULT_OK && data != null) {
            int eventoId = data.getIntExtra("eventoId", -1);
            if (eventoId != -1) {
                int subMetaIdParaVincular = editarId;
                if (subMetaIdParaVincular == -1) {
                    // crear submeta primero
                    SubMeta s = new SubMeta();
                    s.setMetaId(metaId);
                    s.setTitulo(edtTitulo.getText().toString().trim());
                    s.setDescripcion(edtDescripcion.getText().toString().trim());
                    s.setCompletada(chkCompletada.isChecked());
                    long nid = subMetaDAO.insertar(s);
                    if (nid > 0) subMetaIdParaVincular = (int) nid;
                    else {
                        Toast.makeText(this, "No se pudo crear submeta para vincular", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                eventoSubMetaDAO.vincular(eventoId, subMetaIdParaVincular);
                Toast.makeText(this, "Evento vinculado a la submeta", Toast.LENGTH_SHORT).show();
            }
        }
    }
}