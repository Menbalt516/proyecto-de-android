package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Perfil;

public class PerfilDAO {

    private final DatabaseHelper dbHelper;

    public PerfilDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    private SQLiteDatabase getWritable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        return db;
    }

    private SQLiteDatabase getReadable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");
        return db;
    }
    public long guardarPerfil(Perfil perfil) {
        SQLiteDatabase db = getWritable();

        ContentValues valores = new ContentValues();
        valores.put("id", 1);
        valores.put("nombre", perfil.getNombre());
        valores.put("apellidos", perfil.getApellidos());
        valores.put("edad", perfil.getEdad());
        valores.put("imagen_uri", perfil.getImagenUri());

        long id = db.replace("perfil", null, valores);
        db.close();
        return id;
    }
    public Perfil obtenerPerfil() {
        SQLiteDatabase db = getReadable();
        Cursor cursor = db.rawQuery("SELECT * FROM perfil WHERE id = 1", null);

        Perfil perfil = null;

        if (cursor.moveToFirst()) {
            perfil = new Perfil(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                    cursor.getString(cursor.getColumnIndexOrThrow("apellidos")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("edad")),
                    cursor.getString(cursor.getColumnIndexOrThrow("imagen_uri"))
            );
        }

        cursor.close();
        db.close();

        // **Si no existe, crear uno vacío automáticamente**
        if (perfil == null) {
            Perfil nuevo = new Perfil(1, "", "", 0, null);
            guardarPerfil(nuevo);
            return nuevo;
        }

        return perfil;
    }
}
