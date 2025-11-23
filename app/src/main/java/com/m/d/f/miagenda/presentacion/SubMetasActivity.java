package com.m.d.f.miagenda.presentacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.List;

public class SubMetasActivity extends AppCompatActivity {

    private RecyclerView rvSubMetas;
    private Button btnAgregarSubMeta;
    private SubMetaAdapter adapter;
    private SubMetaNegocio subMetaNegocio;

    private int metaId; // recibido desde MetaActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submetas);

        rvSubMetas = findViewById(R.id.rvSubMetas);
        btnAgregarSubMeta = findViewById(R.id.btnAgregarSubMeta);

        // Inicializar negocio
        subMetaNegocio = new SubMetaNegocio(this);

        // Recibir ID de la meta seleccionada
        metaId = getIntent().getIntExtra("metaId", -1);

        cargarSubMetas();

        btnAgregarSubMeta.setOnClickListener(v -> mostrarDialogoNuevaSubMeta());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar sub-metas cuando se vuelve a esta actividad
        cargarSubMetas();
    }

    private void cargarSubMetas() {
        List<SubMeta> subMetas = subMetaNegocio.listarPorMeta(metaId);

        if (adapter == null) {
            adapter = new SubMetaAdapter(subMetas, this, subMetaNegocio);
            rvSubMetas.setLayoutManager(new LinearLayoutManager(this));
            rvSubMetas.setAdapter(adapter);
        } else {
            adapter.actualizarLista(subMetas);
        }
    }

    private void mostrarDialogoNuevaSubMeta() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_nueva_submeta, null);

        EditText txtTituloSub = dialogView.findViewById(R.id.edtTituloSubMeta);

        builder.setView(dialogView);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String titulo = txtTituloSub.getText().toString().trim();
            if (!titulo.isEmpty()) {
                SubMeta nueva = new SubMeta(titulo, false, metaId);
                subMetaNegocio.agregarSubMeta(nueva);
                cargarSubMetas(); // refrescar lista
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.create().show();
    }
}
