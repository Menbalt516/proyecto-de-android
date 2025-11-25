package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    private final DatabaseHelper dbHelper;

    public EventoDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // INSERTAR
    public long insertar(Evento e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", e.getTitulo());
        values.put("fecha", e.getFecha());
        values.put("hora", e.getHora());
        values.put("descripcion", e.getDescripcion());
        values.put("categoria", e.getCategoria());
        values.put("recordatorio", e.isRecordatorio() ? 1 : 0);
        return db.insert("eventos", null, values);
    }

    // ACTUALIZAR
    public boolean actualizar(Evento e) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", e.getTitulo());
        values.put("fecha", e.getFecha());
        values.put("hora", e.getHora());
        values.put("descripcion", e.getDescripcion());
        values.put("categoria", e.getCategoria());
        values.put("recordatorio", e.isRecordatorio() ? 1 : 0);
        int filas = db.update("eventos", values, "id=?", new String[]{String.valueOf(e.getId())});
        return filas > 0;
    }

    // ELIMINAR
    public boolean eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("eventos", "id=?", new String[]{String.valueOf(id)});
        return filas > 0;
    }

    // OBTENER UNO
    public Evento obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM eventos WHERE id=?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            Evento e = cursorToEvento(c);
            c.close();
            return e;
        }
        c.close();
        return null;
    }

    // LISTAR TODOS
    public List<Evento> listar() {
        List<Evento> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM eventos ORDER BY fecha ASC", null);

        while (c.moveToNext()) {
            lista.add(cursorToEvento(c));
        }
        c.close();
        return lista;
    }

    // LISTAR EVENTOS VINCULADOS A UNA META
    public List<Evento> listarPorMeta(int metaId) {
        List<Evento> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT e.* FROM eventos e " +
                        "INNER JOIN evento_meta em ON e.id = em.eventoId " +
                        "WHERE em.metaId = ?",
                new String[]{String.valueOf(metaId)}
        );

        while (c.moveToNext()) lista.add(cursorToEvento(c));
        c.close();
        return lista;
    }

    // LISTAR EVENTOS VINCULADOS A UNA SUBMETA
    public List<Evento> listarPorSubMeta(int subMetaId) {
        List<Evento> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT e.* FROM eventos e " +
                        "INNER JOIN evento_submeta es ON e.id = es.eventoId " +
                        "WHERE es.subMetaId = ?",
                new String[]{String.valueOf(subMetaId)}
        );

        while (c.moveToNext()) lista.add(cursorToEvento(c));
        c.close();
        return lista;
    }

    private Evento cursorToEvento(Cursor c) {
        Evento e = new Evento();
        e.setId(c.getInt(c.getColumnIndexOrThrow("id")));
        e.setTitulo(c.getString(c.getColumnIndexOrThrow("titulo")));
        e.setFecha(c.getLong(c.getColumnIndexOrThrow("fecha")));
        e.setHora(c.getString(c.getColumnIndexOrThrow("hora")));
        e.setDescripcion(c.getString(c.getColumnIndexOrThrow("descripcion")));
        e.setCategoria(c.getString(c.getColumnIndexOrThrow("categoria")));
        e.setRecordatorio(c.getInt(c.getColumnIndexOrThrow("recordatorio")) == 1);
        return e;
    }
    public List<Evento> obtenerEventosPorFecha(long fechaInicio, long fechaFin) {
        List<Evento> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Selecciona eventos cuya fecha esté entre fechaInicio y fechaFin
        Cursor c = db.rawQuery(
                "SELECT * FROM eventos WHERE fecha BETWEEN ? AND ? ORDER BY hora ASC",
                new String[]{String.valueOf(fechaInicio), String.valueOf(fechaFin)}
        );

        while (c.moveToNext()) {
            lista.add(cursorToEvento(c));
        }
        c.close();
        return lista;
    }
}
