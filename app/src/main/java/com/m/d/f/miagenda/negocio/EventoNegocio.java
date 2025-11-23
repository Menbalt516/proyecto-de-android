package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;

import java.util.List;

public class EventoNegocio {
    private EventoDAO eventoDAO;

    public EventoNegocio(Context context) {
        eventoDAO = new EventoDAO(context);
    }

    public long agregarEvento(Evento evento) {

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty())
            throw new IllegalArgumentException("El título es obligatorio");

        // Validación correcta usando el campo real
        if (evento.getFechaMillis() <= 0)
            throw new IllegalArgumentException("Debe seleccionar una fecha válida");

        return eventoDAO.insertar(evento);
    }

    public int actualizarEvento(Evento evento) {
        return eventoDAO.actualizar(evento);
    }

    public int eliminarEvento(int id) {
        return eventoDAO.eliminar(id);
    }

    public Evento obtenerEvento(int id) {
        return eventoDAO.obtenerPorId(id);
    }

    public List<Evento> listarEventos() {
        return eventoDAO.listarTodos();
    }
}
