package com.m.d.f.miagenda.negocio;

import android.content.Context;
import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.modelos.SubMeta;
import java.util.List;

public class MetaNegocio {
    private MetaDAO metaDAO;
    private SubMetaDAO subMetaDAO;

    public MetaNegocio(Context context) {
        metaDAO = new MetaDAO(context);
        subMetaDAO = new SubMetaDAO(context);
    }

    public long agregarMeta(Meta m) {
        if (m.getTitulo().isEmpty()) throw new IllegalArgumentException("El título es obligatorio");
        m.setProgreso(0);
        return metaDAO.insertar(m);
    }

    // ✔ ESTE ES EL MÉTODO CORRECTO
    public List<Meta> obtenerMetas() {
        return metaDAO.listarTodos();
    }

    public void recalcularProgreso(int metaId) {
        Meta meta = metaDAO.obtenerPorId(metaId);
        List<SubMeta> subMetas = subMetaDAO.listarPorMeta(metaId);
        if (subMetas.isEmpty()) return;

        int completadas = 0;
        for (SubMeta s : subMetas)
            if (s.getCompletada() == 1) completadas++;

        double progreso = (completadas * 100.0) / subMetas.size();
        meta.setProgreso(progreso);
        metaDAO.actualizar(meta);
    }

    public Meta listarPorMeta(int metaId) {
        return metaDAO.obtenerPorId(metaId);
    }
}
