package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.MetaDAO;
import com.m.d.f.miagenda.modelos.Meta;

import java.util.ArrayList;

public class MetaNegocio {
    private final MetaDAO metaDAO;

    public MetaNegocio(Context ctx) {
        this.metaDAO = new MetaDAO(ctx);
    }

    public long crearMeta(Meta m){
        return metaDAO.insertar(m);
    }

    public boolean actualizarMeta(Meta m){
        return metaDAO.actualizar(m);
    }

    public boolean eliminarMeta(int id){
        return metaDAO.eliminar(id);
    }

    public ArrayList<Meta> listarMetas(){
        // recalcular progreso para todas (por seguridad) => opcional iterar
        ArrayList<Meta> list = metaDAO.listar();
        return list;
    }

    public Meta obtener(int id){
        return metaDAO.obtenerPorId(id);
    }

    public void recalcularProgreso(int metaId){
        metaDAO.recalcularProgreso(metaId);
    }
}
