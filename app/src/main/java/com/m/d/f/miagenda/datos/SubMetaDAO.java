package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.SubMeta;
import java.util.ArrayList;
import java.util.List;

public class SubMetaDAO {
    private DatabaseHelper dbHelper;

    public SubMetaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertar(SubMeta s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("metaId", s.getMetaId());
        v.put("titulo", s.getTitulo());
        v.put("descripcion", s.getDescripcion());
        v.put("completada", s.getCompletada());
        long id = db.insert("submetas", null, v);
        db.close();
        return id;
    }

    public int actualizar(SubMeta s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("titulo", s.getTitulo());
        v.put("descripcion", s.getDescripcion());
        //v.put("completada", s.getCompletada());
        int filas = db.update("submetas", v, "id = ?", new String[]{String.valueOf(s.getId())});
        db.close();
        return filas;
    }

    public int eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("submetas", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas;
    }

    public List<SubMeta> listarPorMeta(int metaId) {
        List<SubMeta> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM submetas WHERE metaId = ?", new String[]{String.valueOf(metaId)});
        while (c.moveToNext()) {
            lista.add(new SubMeta(
                    c.getInt(c.getColumnIndexOrThrow("id")),
                    c.getInt(c.getColumnIndexOrThrow("metaId")),
                    c.getString(c.getColumnIndexOrThrow("titulo")),
                    c.getString(c.getColumnIndexOrThrow("descripcion")),
                    c.getInt(c.getColumnIndexOrThrow("completada"))
            ));
        }
        c.close();
        db.close();
        return lista;
    }

    public int contarSubMetasCompletadas(int metaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM submetas WHERE metaId=? AND completada=1",
                new String[]{String.valueOf(metaId)});
        int total = 0;
        if (cursor.moveToFirst()) total = cursor.getInt(0);
        cursor.close();
        db.close();
        return total;
    }

    public int contarSubMetas(int metaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM submetas WHERE metaId=?",
                new String[]{String.valueOf(metaId)});
        int total = 0;
        if (cursor.moveToFirst()) total = cursor.getInt(0);
        cursor.close();
        db.close();
        return total;
    }
}
