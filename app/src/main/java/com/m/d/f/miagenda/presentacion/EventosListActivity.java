package com.m.d.f.miagenda.presentacion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.presentacion.adapters.EventoSelectorAdapter;

import java.util.Calendar;
import java.util.List;

public class EventosListActivity extends AppCompatActivity {

    private RecyclerView rvEventos;
    private EventoDAO eventoDAO;
    private Button btnCrearEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos_list);

        rvEventos = findViewById(R.id.rvEventos);
        eventoDAO = new EventoDAO(this);

        btnCrearEvento = findViewById(R.id.btnCrearEvento);
        btnCrearEvento.setOnClickListener(v -> {
            startActivity(new Intent(this, CrearEditarEventoActivity.class));
        });

        cargarEventos();
    }

    private void cargarEventos() {
        // Recibir fecha del calendario
        String fechaSeleccionada = getIntent().getStringExtra("fecha");

        List<Evento> listaEventos;

        if (fechaSeleccionada != null) {
            // Convertir fecha a milisegundos
            String[] parts = fechaSeleccionada.split("/");
            int dia = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]) - 1; // meses empiezan en 0
            int anio = Integer.parseInt(parts[2]);

            Calendar cal = Calendar.getInstance();
            cal.set(anio, mes, dia, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            long fechaInicio = cal.getTimeInMillis();

            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            long fechaFin = cal.getTimeInMillis();

            listaEventos = eventoDAO.obtenerEventosPorFecha(fechaInicio, fechaFin);
        } else {
            listaEventos = eventoDAO.listar();
        }

        if (listaEventos.isEmpty()) {
            Toast.makeText(this, "No hay eventos registrados", Toast.LENGTH_SHORT).show();
        }

        EventoSelectorAdapter adapter = new EventoSelectorAdapter(listaEventos, this::devolverEvento);
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(adapter);
    }

    private void devolverEvento(Evento evento) {
        Intent data = new Intent();
        data.putExtra("eventoId", evento.getId());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
    public static final String EXTRA_META_ID = "extra_meta_id";
}
