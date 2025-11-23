package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.SubMetaEventoDAO;

public class SubMetaEventoNegocio {
    private SubMetaEventoDAO dao;

    public SubMetaEventoNegocio(Context context) {
        dao = new SubMetaEventoDAO(context);
    }

    public long asociar(int subMetaId, int eventoId) {
        return dao.asociar(subMetaId, eventoId);
    }
}

