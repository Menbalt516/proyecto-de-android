package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.EventoDAO;
import com.m.d.f.miagenda.datos.SubMetaEventoDAO;
import com.m.d.f.miagenda.modelos.Evento;

import java.util.ArrayList;
import java.util.List;

public class SubMetaEventoNegocio {

    private final SubMetaEventoDAO subMetaEventoDAO;
    private final EventoDAO eventoDAO; // Para obtener objetos Evento completos

    public SubMetaEventoNegocio(Context context) {
        subMetaEventoDAO = new SubMetaEventoDAO(context);
        // Inicializamos el DAO necesario para obtener el objeto completo
        eventoDAO = new EventoDAO(context);
    }
    public long asociar(int subMetaId, int eventoId) {
        return subMetaEventoDAO.vincular(subMetaId, eventoId);
    }

   // public int desvincular(int subMetaId, int eventoId) {
     //   return subMetaEventoDAO.desvincular(subMetaId, eventoId);
    //}
    //public int eliminarAsociacion(int id) {
      //  return subMetaEventoDAO.eliminar(id);
    //}
    //public List<Evento> listarEventosPorSubMeta(int subMetaId) {
        // 1. Obtener solo los IDs de Evento desde la tabla de relación (SubMetaEventoDAO)
       // List<Integer> eventoIds = subMetaEventoDAO.obtenerEventosPorSubMeta(subMetaId);

        //List<Evento> listaEventos = new ArrayList<>();

        // 2. Iterar y obtener el objeto Evento completo usando EventoDAO
        //for (int eventoId : eventoIds) {
           // Evento e = eventoDAO.obtenerPorId(eventoId);
           // if (e != null) {
              //  listaEventos.add(e);
           // }
        //}

        // Nota: La ordenación debe manejarse si es posible a nivel de consulta de IDs
        // o si se requiere, ordenando la lista Java.

       // return listaEventos;
    //}
   // public List<Integer> obtenerSubMetasIdsPorEvento(int eventoId) {
      //  return subMetaEventoDAO.obtenerSubMetasPorEvento(eventoId);
    //}
    //public boolean existeRelacion(int subMetaId, int eventoId) {
     //   return subMetaEventoDAO.existeRelacion(subMetaId, eventoId);
   // }
}