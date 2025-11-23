package com.m.d.f.miagenda.presentacion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventoCrearActivity extends AppCompatActivity {

    private EditText etTitulo, etDescripcion;
    private TextView txtHora, txtMetaSeleccionada;
    private Button btnElegirFecha, btnGuardar, btnElegirHora, btnSeleccionarMeta;
    private Spinner spCategoria;
    private Switch switchRecordatorio;

    private EventoDAO dao;
    private int eventoId = -1; // -1 = nuevo evento
    private long fechaMillis;   // fecha seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        // Inicializar campos
        etTitulo = findViewById(R.id.edtTitulo);
        etDescripcion = findViewById(R.id.edtDescripcion);
        txtHora = findViewById(R.id.txtHoraSeleccionada);
        txtMetaSeleccionada = findViewById(R.id.txtMetaSeleccionada);
        btnElegirFecha = findViewById(R.id.btnElegirFecha);
        btnGuardar = findViewById(R.id.btnGuardarEvento);
        btnElegirHora = findViewById(R.id.btnElegirHora);
        btnSeleccionarMeta = findViewById(R.id.btnSeleccionarMeta);
        spCategoria = findViewById(R.id.spCategoria);
        switchRecordatorio = findViewById(R.id.switchRecordatorio);

        // Spinner categorías
        String[] categorias = {"Trabajo", "Personal", "Salud", "Estudio"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);

        // Inicializar DAO
        dao = new EventoDAO(this);

        // Fecha por defecto
        fechaMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        btnElegirFecha.setText(sdf.format(new Date(fechaMillis)));

        // Hora por defecto
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
        txtHora.setText(sdfHora.format(new Date()));

        // Ver si es edición
        eventoId = getIntent().getIntExtra("eventoId", -1);
        if (eventoId != -1) {
            cargarDatosEditar();
            btnGuardar.setText("Actualizar Evento");
        }

        // Selección de fecha
        btnElegirFecha.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(fechaMillis);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(this, (view, y, m, d) -> {
                cal.set(y, m, d);
                fechaMillis = cal.getTimeInMillis();
                btnElegirFecha.setText(sdf.format(new Date(fechaMillis)));
            }, year, month, day);
            dpd.show();
        });

        // Selección de hora
        btnElegirHora.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(fechaMillis);
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);

            TimePickerDialog tpd = new TimePickerDialog(this, (view, h, m) -> {
                txtHora.setText(String.format("%02d:%02d", h, m));
            }, hour, minute, true);
            tpd.show();
        });

        // Selección de meta (ejemplo)
        btnSeleccionarMeta.setOnClickListener(v -> {
            // Aquí normalmente abrirías un dialog o actividad para elegir meta
            // Por ejemplo, seleccionamos meta con ID=3 y título "Comprar materiales"
            int metaId = 3;
            String metaTitulo = "Comprar materiales";
            txtMetaSeleccionada.setText(metaTitulo); // mostrar al usuario
            txtMetaSeleccionada.setTag(metaId);      // almacenar ID real
        });

        // Guardar evento
        btnGuardar.setOnClickListener(v -> guardarEvento());
    }

    private void cargarDatosEditar() {
        Evento e = dao.obtenerPorId(eventoId);
        if (e == null) return;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        etTitulo.setText(e.getTitulo());
        etDescripcion.setText(e.getDescripcion());

        fechaMillis = e.getFechaMillis();
        btnElegirFecha.setText(sdf.format(new Date(fechaMillis)));

        txtHora.setText(e.getHora());

        // Spinner categoría
        ArrayAdapter adapter = (ArrayAdapter) spCategoria.getAdapter();
        int position = adapter.getPosition(e.getCategoria());
        if (position >= 0) spCategoria.setSelection(position);

        // Switch recordatorio
        switchRecordatorio.setChecked(e.getRecordatorio() == 1);

        // Meta asociada
        if (e.getMetaAsociadaId() > 0) {
            txtMetaSeleccionada.setText("Meta ID: " + e.getMetaAsociadaId());
            txtMetaSeleccionada.setTag(e.getMetaAsociadaId());
        } else {
            txtMetaSeleccionada.setText("Ninguna");
            txtMetaSeleccionada.setTag(null);
        }
    }

    private void guardarEvento() {
        try {
            String titulo = etTitulo.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String hora = txtHora.getText().toString().trim();
            String categoria = spCategoria.getSelectedItem().toString().trim();
            int recordatorio = switchRecordatorio.isChecked() ? 1 : 0;

            int metaAsociadaId = 0;
            if (txtMetaSeleccionada.getTag() != null) {
                metaAsociadaId = (int) txtMetaSeleccionada.getTag();
            }

            if (titulo.isEmpty() || hora.isEmpty()) {
                Toast.makeText(this, "Título y Hora son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Evento evento = new Evento(
                    eventoId,
                    titulo,
                    fechaMillis,
                    hora,
                    descripcion,
                    categoria,
                    recordatorio,
                    metaAsociadaId
            );

            if (eventoId == -1) {
                long id = dao.insertar(evento);
                if (id > 0) Toast.makeText(this, "Evento creado", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Error al crear evento", Toast.LENGTH_SHORT).show();
            } else {
                int filas = dao.actualizar(evento);
                if (filas > 0) Toast.makeText(this, "Evento actualizado", Toast.LENGTH_SHORT).show();
                else Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
