package com.m.d.f.miagenda.presentacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.m.d.f.miagenda.R;
import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;
import com.m.d.f.miagenda.presentacion.adapters.EventoAdapter;
import java.util.List;

public class EventoListActivity extends AppCompatActivity {

    private RecyclerView rvEventos;
    private EventoAdapter adapter;
    private EventoDAO dao;
    private Button btnNuevoEvento;
    private static final int REQUEST_CREAR_EVENTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        // Inicializar vistas
        rvEventos = findViewById(R.id.rvEventos);
        btnNuevoEvento = findViewById(R.id.btnNuevoEvento);
        FloatingActionButton fabCrear = findViewById(R.id.fabCrearEvento);

        // Cargar lista de eventos
        dao = new EventoDAO(this);
        List<Evento> eventos = dao.listarTodos();

        adapter = new EventoAdapter(this, eventos);
        rvEventos.setLayoutManager(new LinearLayoutManager(this));
        rvEventos.setAdapter(adapter);

        // Botón superior para crear evento
        btnNuevoEvento.setOnClickListener(v -> {
            Intent i = new Intent(this, EventoCrearActivity.class);
            startActivity(i);

        });

        // FAB para crear evento
        fabCrear.setOnClickListener(v -> {
            Intent i = new Intent(this, EventoCrearActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CREAR_EVENTO) {
            // Recargar lista de eventos al volver de crear
            List<Evento> eventosActualizados = dao.listarTodos();
            adapter.actualizarLista(eventosActualizados);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // recargar siempre la lista al volver a la Activity
        List<Evento> eventosActualizados = dao.listarTodos(); // o dao.obtenerTodos()
        adapter.actualizarLista(eventosActualizados);
    }
}
