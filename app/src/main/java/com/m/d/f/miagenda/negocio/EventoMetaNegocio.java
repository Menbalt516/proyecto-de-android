package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.EventoMetaDAO;

import java.util.List;

/**
 * Clase de Negocio para manejar la lógica de interconexión entre Eventos y Metas Principales.
 * Actúa como intermediario entre la UI/Lógica de Servicio y EventoMetaDAO.
 */
public class EventoMetaNegocio {

    private final EventoMetaDAO dao;

    public EventoMetaNegocio(Context context) {
        dao = new EventoMetaDAO(context);
    }

    // ------------------------------------------------------
    // ASOCIACIÓN
    // ------------------------------------------------------
    /**
     * Intenta asociar una Meta Principal con un Evento.
     * La lógica de no duplicación se maneja internamente en el DAO.
     * @param metaId ID de la Meta Principal.
     * @param eventoId ID del Evento.
     * @return ID de la relación o -1 si ya existía.
     */
    public long asociar(int metaId, int eventoId) {
        return dao.vincular(metaId, eventoId);
    }

    public boolean desvincular(int metaId, int eventoId) {
        return dao.desvincular(metaId, eventoId);
    }

    //public int eliminarTodasLasRelacionesDeMeta(int metaId) {
       // return dao.eliminarRelacionesDeMeta(metaId);
    //}

   // public boolean existeRelacion(int metaId, int eventoId) {
       // return dao.existeRelacion(metaId, eventoId);
   // }

    //public List<Integer> obtenerEventosPorMeta(int metaId) {
       // return dao.obtenerEventosPorMeta(metaId);
    //}

    //public List<Integer> obtenerMetasPorEvento(int eventoId) {
       // return dao.obtenerMetasPorEvento(eventoId);
    //}
}