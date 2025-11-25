package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoMetaDAO {

    private final DatabaseHelper dbHelper;

    public EventoMetaDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // AGREGAR VÍNCULO
    public long vincular(int eventoId, int metaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("eventoId", eventoId);
        values.put("metaId", metaId);

        return db.insert("evento_meta", null, values);
    }

    // ELIMINAR VÍNCULO
    public boolean desvincular(int eventoId, int metaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("evento_meta", "eventoId=? AND metaId=?", new String[]{
                String.valueOf(eventoId),
                String.valueOf(metaId)
        });
        return filas > 0;
    }

    // SABER SI ESTA VINCULADO
    public boolean estaVinculado(int eventoId, int metaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id FROM evento_meta WHERE eventoId=? AND metaId=?",
                new String[]{String.valueOf(eventoId), String.valueOf(metaId)}
        );

        boolean existe = c.moveToFirst();
        c.close();
        return existe;
    }

    public List<Evento> listarEventosPorMeta(int metaId) {
        List<Evento> eventos = new ArrayList<>();
        String query = "SELECT e.* FROM Evento e " +
                "INNER JOIN Evento_Meta em ON e.id = em.eventoId " +
                "WHERE em.metaId = ?";

        SQLiteDatabase db = getReadable();
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(metaId)});

        if (cursor.moveToFirst()) {
            do {
                Evento e = new Evento();
                e.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                e.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
                e.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
                e.setFecha(cursor.getLong(cursor.getColumnIndexOrThrow("fecha")));
                e.setHora(cursor.getString(cursor.getColumnIndexOrThrow("hora")));
                e.setRecordatorio(cursor.getInt(cursor.getColumnIndexOrThrow("recordatorio")) == 1);
                eventos.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return eventos;
    }

    private SQLiteDatabase getReadable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();  // obtiene la base de datos en modo lectura
        db.execSQL("PRAGMA foreign_keys = ON;");            // asegura que se respeten las claves foráneas
        return db;
    }
}

