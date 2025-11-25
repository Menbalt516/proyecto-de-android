package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.modelos.Evento;

import java.util.List;

public class EventoNegocio {
    private final EventoDAO eventoDAO;

    public EventoNegocio(Context context) {
        eventoDAO = new EventoDAO(context);
    }

    // AGREGAR EVENTO
    public long agregarEvento(Evento evento) {

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty())
            throw new IllegalArgumentException("El título es obligatorio.");

        if (evento.getFecha() <= 0)
            throw new IllegalArgumentException("Debe seleccionar una fecha válida.");

        return eventoDAO.insertar(evento);
    }

    // ACTUALIZAR
    public boolean actualizarEvento(Evento evento) {
        if (evento.getId() <= 0)
            throw new IllegalArgumentException("ID del evento no válido.");

        if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty())
            throw new IllegalArgumentException("El título es obligatorio.");

        return eventoDAO.actualizar(evento);
    }

    // ELIMINAR
    public boolean eliminarEvento(int id) {
        if (id <= 0)
            throw new IllegalArgumentException("ID no válido.");

        return eventoDAO.eliminar(id);
    }

    // OBTENER UNO
    public Evento obtenerEvento(int id) {
        return eventoDAO.obtenerPorId(id);
    }

    // LISTAR TODOS
    public List<Evento> listarEventos() {
        return eventoDAO.listar();
    }

    // LISTAR POR DÍA
    //public List<Evento> obtenerEventosPorDia(long inicioDia, long finDia) {
       // if (inicioDia < 0 || finDia < 0 || inicioDia > finDia)
         //   throw new IllegalArgumentException("Rango de fecha no válido.");

       // return eventoDAO.obtenerEventosPorDia(inicioDia, finDia);
    //}
}
