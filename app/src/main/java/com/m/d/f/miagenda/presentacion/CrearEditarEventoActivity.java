package com.m.d.f.miagenda.presentacion;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.notificaciones.RecordatorioManager;

import java.util.Calendar;

public class CrearEditarEventoActivity extends AppCompatActivity {

    public static final String EXTRA_VINCLUIR_DESDE_META = "extra_vincular_desde_meta";
    public static final String EXTRA_META_ID = "extra_meta_id";
    private static final int REQUEST_VINCULAR_META = 2001;

    public static final String EXTRA_VINCLUIR_DESDE_SUBMETA = "extra_vincular_desde_submeta";
    public static final String EXTRA_SUBMETA_ID = "extra_submeta_id";

    private EditText edtTitulo, edtDescripcion;
    private Button btnFecha, btnHora, btnGuardar, btnVincularMeta;
    private TextView txtFecha, txtHora, txtMetaVinculada;
    private Spinner spinnerCategoria;
    private CheckBox chkRecordatorio;

    private EventoDAO eventoDAO;
    private long fechaMillis = 0;

    private int metaIdVincular = -1; // ID de Meta o Sub-Meta
    private String metaTituloVinculada = ""; // Título para mostrar

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_evento);

        edtTitulo = findViewById(R.id.edtTituloEvento);
        edtDescripcion = findViewById(R.id.edtDescripcionEvento);
        btnFecha = findViewById(R.id.btnFechaEvento);
        btnHora = findViewById(R.id.btnHoraEvento);
        txtFecha = findViewById(R.id.txtFechaSeleccionada);
        txtHora = findViewById(R.id.txtHoraSeleccionada);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        chkRecordatorio = findViewById(R.id.chkRecordatorio);
        btnGuardar = findViewById(R.id.btnGuardarEvento);
        btnVincularMeta = findViewById(R.id.btnVincularMeta);
        txtMetaVinculada = findViewById(R.id.txtMetaVinculada);

        eventoDAO = new EventoDAO(this);

        // Spinner categorías
        String[] categorias = {"Estudio","Ocio","Entretenimiento","Trabajo","Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        // Fecha
        btnFecha.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        fechaMillis = cal.getTimeInMillis();
                        txtFecha.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    },
                    c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });

        // Hora
        btnHora.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            TimePickerDialog tpd = new TimePickerDialog(this,
                    (view, hourOfDay, minute) -> txtHora.setText(String.format("%02d:%02d", hourOfDay, minute)),
                    c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
            tpd.show();
        });

        // Guardar
        btnGuardar.setOnClickListener(v -> guardarEvento());
        // Vincular Meta/SubMeta
        btnVincularMeta.setOnClickListener(v -> {
            Intent i = new Intent(this, MetasListActivity.class);
            startActivityForResult(i, REQUEST_VINCULAR_META);
        });
    }

    private void guardarEvento() {
        String titulo = edtTitulo.getText().toString().trim();
        if (titulo.isEmpty()) {
            Toast.makeText(this, "Título obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fechaMillis <= 0) {
            Toast.makeText(this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el evento con los datos del formulario
        Evento evento = new Evento();
        evento.setTitulo(titulo);
        evento.setDescripcion(edtDescripcion.getText().toString().trim());
        evento.setFecha(fechaMillis);
        evento.setHora(txtHora.getText().toString().trim());
        evento.setCategoria(spinnerCategoria.getSelectedItem().toString());
        evento.setRecordatorio(chkRecordatorio.isChecked());
        evento.setMetaId(metaIdVincular);

        // Guardar en SQLite
        long idGenerado = eventoDAO.insertar(evento);
        evento.setId((int) idGenerado);

        if (idGenerado > 0) {

            // 🔔 Programar notificación SOLO si el usuario activó recordatorio
            if (evento.isRecordatorio()) {
                RecordatorioManager.programarRecordatorio(
                        this,
                        evento.getFechaEnMilisegundos(),
                        evento.getId(),
                        evento.getTitulo()
                );
            }

            Toast.makeText(this, "Evento guardado", Toast.LENGTH_SHORT).show();

            // Devolver el ID al activity padre
            Intent data = new Intent();
            data.putExtra("eventoId", (int) idGenerado);
            setResult(RESULT_OK, data);

            finish();
        } else {
            Toast.makeText(this, "Error al guardar evento", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VINCULAR_META && resultCode == RESULT_OK && data != null) {
            int subMetaId = data.getIntExtra("subMetaId", -1);
            int metaId = data.getIntExtra("metaId", -1);
            String titulo = data.getStringExtra("titulo");

            if (subMetaId != -1) {
                metaIdVincular = subMetaId;
                metaTituloVinculada = titulo;
                txtMetaVinculada.setText("Sub-Meta vinculada: " + titulo);
            } else if (metaId != -1) {
                metaIdVincular = metaId;
                metaTituloVinculada = titulo;
                txtMetaVinculada.setText("Meta vinculada: " + titulo);
            }
        }
    }
}
