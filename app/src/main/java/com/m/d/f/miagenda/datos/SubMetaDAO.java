package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.SubMeta;

import java.util.ArrayList;

public class SubMetaDAO {
    private final DatabaseHelper dbHelper;
    private final MetaDAO metaDAO;

    public SubMetaDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.metaDAO = new MetaDAO(context);
    }

    public long insertar(SubMeta s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("metaId", s.getMetaId());
        values.put("titulo", s.getTitulo());
        values.put("descripcion", s.getDescripcion());
        values.put("completada", s.isCompletada());

        long id = db.insert("submetas", null, values);
        db.close();
        metaDAO.recalcularProgreso(s.getMetaId());
        return id;
    }

    public boolean actualizar(SubMeta s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", s.getTitulo());
        values.put("descripcion", s.getDescripcion());
        values.put("completada", s.isCompletada());
        int filas = db.update("submetas", values, "id=?", new String[]{String.valueOf(s.getId())});
        db.close();
        metaDAO.recalcularProgreso(s.getMetaId());
        return filas > 0;
    }

    public boolean eliminar(int id, int metaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("submetas", "id=?", new String[]{String.valueOf(id)});
        db.close();
        metaDAO.recalcularProgreso(metaId);
        return filas > 0;
    }

    public ArrayList<SubMeta> listarPorMeta(int metaId) {
        ArrayList<SubMeta> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,metaId,titulo,descripcion,completada FROM submetas WHERE metaId=? ORDER BY id ASC",
                new String[]{String.valueOf(metaId)});
        if (c.moveToFirst()) {
            do {
                SubMeta s = new SubMeta();
                s.setId(c.getInt(0));
                s.setMetaId(c.getInt(1));
                s.setTitulo(c.getString(2));
                s.setDescripcion(c.getString(3));
                s.setCompletada(c.getInt(4) == 1);
                lista.add(s);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return lista;
    }

    public SubMeta obtener(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,metaId,titulo,descripcion,completada FROM submetas WHERE id=?",
                new String[]{String.valueOf(id)});
        if (!c.moveToFirst()) {
            c.close();
            db.close();
            return null;
        }
        SubMeta s = new SubMeta();
        s.setId(c.getInt(0));
        s.setMetaId(c.getInt(1));
        s.setTitulo(c.getString(2));
        s.setDescripcion(c.getString(3));
        s.setCompletada(c.getInt(4) == 1);
        c.close();
        db.close();
        return s;
    }
}
