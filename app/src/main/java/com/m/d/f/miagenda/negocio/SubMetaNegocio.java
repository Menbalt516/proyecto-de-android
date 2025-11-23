package com.m.d.f.miagenda.negocio;

import android.content.Context;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.modelos.SubMeta;
import java.util.List;

public class SubMetaNegocio {
    private SubMetaDAO subMetaDAO;
    private MetaNegocio metaNegocio;

    public SubMetaNegocio(Context context) {
        subMetaDAO = new SubMetaDAO(context);
        metaNegocio = new MetaNegocio(context);
    }

    public long agregarSubMeta(SubMeta s) {
        if (s.getTitulo().isEmpty())
            throw new IllegalArgumentException("La sub-meta necesita un título");

        long id = subMetaDAO.insertar(s);

        if (id > 0) {
            metaNegocio.recalcularProgreso(s.getMetaId());
        }

        return id;
    }

    public int actualizarSubMeta(SubMeta s) {
        int filas = subMetaDAO.actualizar(s);
        metaNegocio.recalcularProgreso(s.getMetaId());
        return filas;
    }

    public int eliminarSubMeta(int id, int metaId) {
        int filas = subMetaDAO.eliminar(id);
        metaNegocio.recalcularProgreso(metaId);
        return filas;
    }

    public List<SubMeta> listarPorMeta(int metaId) {
        return subMetaDAO.listarPorMeta(metaId);
    }
    public int calcularPorcentajeCompletado(int metaId) {
        List<SubMeta> subMetas = subMetaDAO.listarPorMeta(metaId);

        if (subMetas.isEmpty()) return 0; // evitar división por cero

        int completadas = 0;
        for (SubMeta s : subMetas) {
            if (s.isCompletada()) completadas++;
        }

        return (completadas * 100) / subMetas.size();
    }
}

