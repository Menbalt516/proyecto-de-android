package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.MetaEventoDAO;

public class MetaEventoNegocio {
    private MetaEventoDAO dao;

    public MetaEventoNegocio(Context context) {
        dao = new MetaEventoDAO(context);
    }

    public long asociar(int metaId, int eventoId) {
        return dao.asociar(metaId, eventoId);
    }
}

