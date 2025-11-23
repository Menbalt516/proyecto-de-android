package com.m.d.f.miagenda.negocio;

import android.content.Context;

import com.m.d.f.miagenda.datos.PerfilDAO;
import com.m.d.f.miagenda.modelos.Perfil;

public class PerfilNegocio {
    private PerfilDAO perfilDAO;

    // Constructor
    public PerfilNegocio(Context context) {
        perfilDAO = new PerfilDAO(context);
    }

    // Guardar perfil con validación
    public long guardarPerfil(Perfil p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return perfilDAO.guardarPerfil(p);
    }

    // Obtener perfil (puede devolver null si no existe)
    public Perfil obtenerPerfil() {
        return perfilDAO.obtenerPerfil();
    }

    // Método adicional para obtener el perfil actual (igual que obtenerPerfil)
    public Perfil getPerfil() {
        // Simplemente delega al DAO
        return perfilDAO.obtenerPerfil();
    }
}
