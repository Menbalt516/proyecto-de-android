package com.m.d.f.miagenda.presentacion;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;

public class CrearSubMetaActivity extends AppCompatActivity {

    private EditText edtTitulo, edtDescripcion;
    private Button btnGuardar;
    private SubMetaNegocio subMetaNegocio;
    private int metaId;
    private int editarId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_submeta); // crea layout con edtTitulo, edtDescripcion, btnGuardar

        edtTitulo = findViewById(R.id.edtTituloSubMeta);
        edtDescripcion = findViewById(R.id.edtDescripcionSubMeta);
        btnGuardar = findViewById(R.id.btnGuardarSubMeta);

        subMetaNegocio = new SubMetaNegocio(this);
        metaId = getIntent().getIntExtra("metaId", -1);

        editarId = getIntent().getIntExtra("subMetaId", -1);
        if (editarId != -1) {
            SubMeta s = subMetaNegocio.obtener(editarId);
            if (s != null) {
                edtTitulo.setText(s.getTitulo());
                edtDescripcion.setText(s.getDescripcion());
            }
        }

        btnGuardar.setOnClickListener(v -> {
            String t = edtTitulo.getText().toString().trim();
            String d = edtDescripcion.getText().toString().trim();
            if (t.isEmpty()) { Toast.makeText(this, "Título requerido", Toast.LENGTH_SHORT).show(); return; }

            if (editarId != -1) {
                SubMeta s = subMetaNegocio.obtener(editarId);
                s.setTitulo(t);
                s.setDescripcion(d);
                subMetaNegocio.actualizarSubMeta(s);
                finish();
            } else {
                SubMeta s = new SubMeta();
                s.setMetaId(metaId);
                s.setTitulo(t);
                s.setDescripcion(d);
                s.setCompletada(false);
                subMetaNegocio.crearSubMeta(s);
                finish();
            }
        });
    }
}
