package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;

public class EventoDetalleActivity extends AppCompatActivity {

    private TextView txtTitulo, txtDescripcion, txtFecha;
    private Button btnAsociar, btnEliminar, btnEditar;
    private EventoDAO dao;
    private int eventoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);

        // UI
        txtTitulo = findViewById(R.id.tvTituloDetalle);
        txtDescripcion = findViewById(R.id.tvDescripcionDetalle);
        txtFecha = findViewById(R.id.tvFechaDetalle);

        btnAsociar = findViewById(R.id.btnAsociarMeta);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEditar = findViewById(R.id.btnEditar);

        // DAO
        dao = new EventoDAO(this);

        // Obtención del ID enviado desde la lista
        eventoId = getIntent().getIntExtra("eventoId", -1);

        // Cargar datos
        cargarDatos();

        // Asociar Meta/SubMeta
        btnAsociar.setOnClickListener(v -> {
            Intent i = new Intent(this, MetaSelectorActivity.class);
            i.putExtra("eventoId", eventoId);
            startActivity(i);
        });

        // Eliminar
        btnEliminar.setOnClickListener(v -> eliminar());

        // Editar
        btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(this, EventoCrearActivity.class);
            i.putExtra("eventoId", eventoId);
            startActivity(i);
        });
    }

    private void cargarDatos() {
        Evento e = dao.obtenerPorId(eventoId);

        if (e != null) {
            txtTitulo.setText(e.getTitulo());
            txtDescripcion.setText(e.getDescripcion());
            txtFecha.setText(e.getFecha());
        } else {
            Toast.makeText(this, "Evento no encontrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void eliminar() {
        if (eventoId == -1) {
            Toast.makeText(this, "Evento no válido", Toast.LENGTH_SHORT).show();
            return;
        }

        int filas = dao.eliminar(eventoId);

        if (filas > 0) {
            Toast.makeText(this, "Evento eliminado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
