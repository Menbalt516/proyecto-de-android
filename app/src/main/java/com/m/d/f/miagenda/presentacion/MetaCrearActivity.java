package com.m.d.f.miagenda.presentacion;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.negocio.MetaNegocio;

public class MetaCrearActivity extends AppCompatActivity {

    EditText txtTitulo, txtDescripcion;
    MetaNegocio metaNegocio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_meta);

        metaNegocio = new MetaNegocio(this);

        txtTitulo = findViewById(R.id.edtTituloMeta);
        txtDescripcion = findViewById(R.id.edtDescripcionMeta);

        findViewById(R.id.btnGuardarMeta).setOnClickListener(v -> guardar());
    }

    private void guardar() {
        String titulo = txtTitulo.getText().toString().trim();
        String descripcion = txtDescripcion.getText().toString().trim();

        if (titulo.isEmpty()) {
            txtTitulo.setError("El título es obligatorio");
            txtTitulo.requestFocus();
            return;
        }

        Meta m = new Meta();
        m.setTitulo(titulo);
        m.setDescripcion(descripcion);
        m.setProgreso(0);   // ← IMPORTANTE: para evitar error
        m.setCompletada(false);

        try {
            long id = metaNegocio.agregarMeta(m);
            if (id > 0) {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

