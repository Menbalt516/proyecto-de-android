package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.modelos.SubMeta;
import com.m.d.f.miagenda.negocio.SubMetaNegocio;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.List;

public class MetaDetalleActivity extends AppCompatActivity {

    private TextView txtTituloMeta, txtDescripcionMeta, txtProgreso;
    private RecyclerView rvSubMetas;
    private Button btnAsociar;
    private Button btnGuardarSubMeta;

    private MetaDAO metaDAO;
    private SubMetaNegocio subMetaNegocio;

    private int metaId; // AHORA SE RECIBE POR INTENT

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta_detalle);

        txtTituloMeta = findViewById(R.id.txtTituloMeta);
        txtDescripcionMeta = findViewById(R.id.txtDescripcionMeta);
        txtProgreso = findViewById(R.id.txtProgreso);
        rvSubMetas = findViewById(R.id.rvSubMetas);

        btnAsociar = findViewById(R.id.btnAsociar);
        btnGuardarSubMeta = findViewById(R.id.btnGuardarSubMeta); // ← ESTE FALTABA

        rvSubMetas.setLayoutManager(new LinearLayoutManager(this));

        metaId = getIntent().getIntExtra("metaId", -1);

        metaDAO = new MetaDAO(this);
        subMetaNegocio = new SubMetaNegocio(this);

        cargarMeta();
        cargarSubMetas();


        btnAsociar.setOnClickListener(v -> {
            Intent intent = new Intent(MetaDetalleActivity.this, EventoListActivity.class);
            startActivityForResult(intent, 100);
        });

        btnGuardarSubMeta.setOnClickListener(v -> mostrarDialogNuevaSubMeta());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {

            int eventoId = data.getIntExtra("eventoId", -1);

            if (eventoId != -1) {
                Toast.makeText(this, "Evento asociado: " + eventoId, Toast.LENGTH_SHORT).show();

                // 🔥 Aquí guardas en BD que esta meta tiene ese evento
                metaDAO.asociarEvento(metaId, eventoId);

                // Opcional: mostrar en pantalla
                txtProgreso.setText("Evento asociado (ID " + eventoId + ")");
            }
        }
    }


    private void cargarMeta() {
        Meta m = metaDAO.obtenerPorId(metaId);

        if (m != null) {
            txtTituloMeta.setText(m.getTitulo());
            txtDescripcionMeta.setText(m.getDescripcion());
            txtProgreso.setText("Progreso: " + m.getProgreso() + "%");
        }
    }

    private void cargarSubMetas() {
        List<SubMeta> lista = subMetaNegocio.listarPorMeta(metaId);

        SubMetaAdapter adapter = new SubMetaAdapter(lista, this, subMetaNegocio);
        rvSubMetas.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nueva SubMeta");

        // >>> ESTE ES EL CAMBIO IMPORTANTE <<<
        EditText input = new EditText(this);
        input.setHint("Título de la SubMeta");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {

            String titulo = input.getText().toString().trim();

            if (titulo.isEmpty()) {
                Toast.makeText(this, "Ingrese un título", Toast.LENGTH_SHORT).show();
                return;
            }

            SubMeta s = new SubMeta(titulo, false, metaId);

            long id = subMetaNegocio.agregarSubMeta(s);

            if (id > 0) {
                Toast.makeText(this, "Agregado", Toast.LENGTH_SHORT).show();
                cargarSubMetas();
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void mostrarDialogNuevaSubMeta() {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_nueva_submeta, null);
        builder.setView(dialogView);

        android.app.AlertDialog dialog = builder.create();
        dialog.show();

        EditText edtTitulo = dialogView.findViewById(R.id.edtTituloSubMeta);
        EditText edtDescripcion = dialogView.findViewById(R.id.edtDescripcionSubMeta);
        Switch switchCompletada = dialogView.findViewById(R.id.switchCompletada);
        Button btnGuardarSubMeta = dialogView.findViewById(R.id.btnGuardarSubMeta);

        btnGuardarSubMeta.setOnClickListener(v -> {

            String titulo = edtTitulo.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();
            boolean completada = switchCompletada.isChecked();

            if (titulo.isEmpty()) {
                edtTitulo.setError("El título es obligatorio");
                return;
            }

            // Ajusta tu constructor según tu clase SubMeta
            SubMeta nueva = new SubMeta(titulo, descripcion, completada, metaId);

            long id = subMetaNegocio.agregarSubMeta(nueva);

            if (id > 0) {
                Toast.makeText(this, "SubMeta guardada correctamente", Toast.LENGTH_SHORT).show();
                cargarSubMetas();
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Error al guardar la SubMeta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
