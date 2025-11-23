package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Evento;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    private DatabaseHelper dbHelper;

    public EventoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("titulo", evento.getTitulo());
        contentValues.put("fecha", evento.getFechaMillis());
        contentValues.put("hora", evento.getHora());
        contentValues.put("descripcion", evento.getDescripcion());
        contentValues.put("categoria", evento.getCategoria());
        contentValues.put("recordatorio", evento.getRecordatorio());
        contentValues.put("meta_asociada_id", evento.getMetaAsociadaId());

        long id = db.insert("eventos", null, contentValues);
        db.close();
        return id;
    }

    public int actualizar(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("titulo", evento.getTitulo());
        contentValues.put("fecha", evento.getFechaMillis());
        contentValues.put("hora", evento.getHora());
        contentValues.put("descripcion", evento.getDescripcion());
        contentValues.put("categoria", evento.getCategoria());
        contentValues.put("recordatorio", evento.getRecordatorio());
        contentValues.put("meta_asociada_id", evento.getMetaAsociadaId());

        int filas = db.update("eventos", contentValues, "id = ?", new String[]{String.valueOf(evento.getId())});
        db.close();
        return filas;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("eventos", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas;
    }

    public Evento obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM eventos WHERE id = ?", new String[]{String.valueOf(id)});

        Evento evento = null;

        if (cursor.moveToFirst()) {
            evento = new Evento(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            );
        }

        cursor.close();
        db.close();
        return evento;
    }

    public List<Evento> listarTodos() {
        List<Evento> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM eventos ORDER BY fecha, hora", null);

        while (cursor.moveToNext()) {
            lista.add(new Evento(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getInt(7)
            ));
        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Evento> obtenerEventosPorDia(long inicioDia, long finDia) {
        List<Evento> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, titulo, descripcion, fecha FROM eventos " +
                        "WHERE fecha BETWEEN ? AND ? ORDER BY fecha ASC",
                new String[]{String.valueOf(inicioDia), String.valueOf(finDia)}
        );

        if (cursor.moveToFirst()) {
            do {
                Evento evento = new Evento();
                evento.setId(cursor.getInt(0));
                evento.setTitulo(cursor.getString(1));
                evento.setDescripcion(cursor.getString(2));
                evento.setFechaMillis(cursor.getLong(3)); // <- usa getLong, no getString

                lista.add(evento);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }
}


