package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Meta;

import java.util.ArrayList;
import java.util.List;

public class MetaDAO {
    private DatabaseHelper dbHelper;

    public MetaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Meta m) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("titulo", m.getTitulo());
        v.put("descripcion", m.getDescripcion());
        v.put("progreso", m.getProgreso());
        v.put("completada", m.isCompletada() ? 1 : 0);

        long id = db.insert("metas", null, v);
        db.close();
        return id;
    }

    public int actualizar(Meta m) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("titulo", m.getTitulo());
        v.put("descripcion", m.getDescripcion());
        v.put("progreso", m.getProgreso());
        v.put("completada", m.isCompletada() ? 1 : 0);

        int filas = db.update("metas", v, "id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return filas;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("metas", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas;
    }

    public Meta obtenerPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, titulo, descripcion, progreso, completada FROM metas WHERE id=?",
                new String[]{String.valueOf(id)}
        );

        Meta m = null;

        if (cursor.moveToFirst()) {
            m = new Meta();
            m.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            m.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
            m.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow("descripcion")));
            m.setProgreso(cursor.getDouble(cursor.getColumnIndexOrThrow("progreso")));
            m.setCompletada(cursor.getInt(cursor.getColumnIndexOrThrow("completada")) == 1);
        }

        cursor.close();
        db.close();
        return m;
    }

    public List<Meta> listarTodos() {
        List<Meta> lista = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, titulo, descripcion, progreso, completada FROM metas", null);

        while (c.moveToNext()) {
            Meta m = new Meta();
            m.setId(c.getInt(0));
            m.setTitulo(c.getString(1));
            m.setDescripcion(c.getString(2));
            m.setProgreso(c.getDouble(3));
            m.setCompletada(c.getInt(4) == 1);

            lista.add(m);
        }

        c.close();
        db.close();
        return lista;
    }

    public void actualizarProgreso(int metaId, double progreso) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("progreso", progreso);

        db.update("metas", values, "id=?", new String[]{String.valueOf(metaId)});
        db.close();
    }

    public void asociarEvento(int metaId, int eventoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("eventoId", eventoId);
        values.put("metaId", metaId);

        db.insert("evento_meta", null, values);
        db.close();
    }
}
