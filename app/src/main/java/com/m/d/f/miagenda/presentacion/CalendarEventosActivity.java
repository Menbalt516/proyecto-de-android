package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.presentacion.adapters.EventoAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarEventosActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private RecyclerView rvEventosDia;
    private FloatingActionButton fabAgregarEvento;
    private TextView tvEventosDia;

    private List<Evento> eventosDelDia;
    private EventoAdapter eventoAdapter;
    private EventoDAO eventoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_eventos);

        // --- Inicializar DAO ---
        eventoDAO = new EventoDAO(this);

        // --- Referencias a la UI ---
        calendarView = findViewById(R.id.calendar_view);
        rvEventosDia = findViewById(R.id.rv_eventos_dia);
        fabAgregarEvento = findViewById(R.id.fab_agregar_evento);
        tvEventosDia = findViewById(R.id.tv_eventos_dia);

        // --- Configurar RecyclerView ---
        eventosDelDia = new ArrayList<>();
        eventoAdapter = new EventoAdapter(this, eventosDelDia);
        rvEventosDia.setLayoutManager(new LinearLayoutManager(this));
        rvEventosDia.setAdapter(eventoAdapter);

        // --- Listener del CalendarView ---
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cargarEventosDelDia(year, month, dayOfMonth);

                // Actualizar título con la fecha
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                tvEventosDia.setText(getString(R.string.eventos_para_la_fecha) +
                        new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(cal.getTime()));
            }
        });

        // --- Carga inicial (día de hoy) ---
        Calendar today = Calendar.getInstance();
        long todayMillis = calendarView.getDate();
        today.setTimeInMillis(todayMillis);
        cargarEventosDelDia(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));

        // --- Listener del FAB para crear evento ---
        fabAgregarEvento.setOnClickListener(view -> {
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.setTimeInMillis(calendarView.getDate());

            Intent intent = new Intent(CalendarEventosActivity.this, EventoCrearActivity.class);
            intent.putExtra("selected_year", selectedCal.get(Calendar.YEAR));
            intent.putExtra("selected_month", selectedCal.get(Calendar.MONTH));
            intent.putExtra("selected_day", selectedCal.get(Calendar.DAY_OF_MONTH));
            startActivity(intent);
        });
    }

    // --- Método para cargar eventos de SQLite ---
    private void cargarEventosDelDia(int year, int month, int day) {
        eventosDelDia.clear();

        // Crear rango de inicio y fin del día en milisegundos
        Calendar calStart = Calendar.getInstance();
        calStart.set(year, month, day, 0, 0, 0);
        calStart.set(Calendar.MILLISECOND, 0);
        long inicioDia = calStart.getTimeInMillis();

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(year, month, day, 23, 59, 59);
        calEnd.set(Calendar.MILLISECOND, 999);
        long finDia = calEnd.getTimeInMillis();

        // Obtener eventos del DAO
        List<Evento> eventos = eventoDAO.obtenerEventosPorDia(inicioDia, finDia);
        eventosDelDia.addAll(eventos);

        eventoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recargar eventos por si se agregaron nuevos
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTimeInMillis(calendarView.getDate());
        cargarEventosDelDia(selectedCal.get(Calendar.YEAR), selectedCal.get(Calendar.MONTH), selectedCal.get(Calendar.DAY_OF_MONTH));
    }
}
