package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Perfil;

public class PerfilDAO {
    private DatabaseHelper dbHelper;

    public PerfilDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long guardarPerfil(Perfil perfil) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("id", 1);
        valores.put("nombre", perfil.getNombre());
        valores.put("apellidos", perfil.getApellidos());
        valores.put("edad", perfil.getEdad());
        valores.put("imagen_uri", perfil.getImagenUri());

        long id = db.replace("perfil", null, valores); // REPLACE: evita duplicados
        db.close();
        return id;
    }

    public Perfil obtenerPerfil() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM perfil LIMIT 1", null);

        Perfil perfil = null;

        if (c.moveToFirst()) {
            perfil = new Perfil(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getString(c.getColumnIndexOrThrow("nombre")),
                    c.getString(c.getColumnIndexOrThrow("apellidos")),
                    c.getInt(c.getColumnIndexOrThrow("edad")),
                    c.getString(c.getColumnIndexOrThrow("imagen_uri"))
            );
        }
        c.close();
        db.close();
        return perfil;
    }
}
