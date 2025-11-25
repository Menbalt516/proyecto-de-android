package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.SubMetaDAO;
import com.m.d.f.miagenda.modelos.SubMeta;

import java.util.ArrayList;

public class SubMetaNegocio {
    private final SubMetaDAO subMetaDAO;

    public SubMetaNegocio(Context ctx) {
        this.subMetaDAO = new SubMetaDAO(ctx);
    }

    public long crearSubMeta(SubMeta s){
        return subMetaDAO.insertar(s);
    }

    public boolean actualizarSubMeta(SubMeta s){
        return subMetaDAO.actualizar(s);
    }

    public boolean eliminarSubMeta(int id, int metaId){
        return subMetaDAO.eliminar(id, metaId);
    }

    public ArrayList<SubMeta> listarPorMeta(int metaId){
        return subMetaDAO.listarPorMeta(metaId);
    }

    public SubMeta obtener(int id){
        return subMetaDAO.obtener(id);
    }
}
