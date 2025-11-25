package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.m.d.f.miagenda.modelos.Meta;
import com.m.d.f.miagenda.presentacion.adapters.EventoAdapter;
import com.m.d.f.miagenda.presentacion.adapters.MetaAdapter;
import com.m.d.f.miagenda.presentacion.adapters.SubMetaAdapter;

import java.util.ArrayList;

public class MetaDAO {
    private final DatabaseHelper dbHelper;
    // En MetasActivity.java
// Adaptadores
    private MetaAdapter metaListAdapter;
    private SubMetaAdapter subMetaAdapter;
    private EventoAdapter eventoAdapter;
// ...

    public MetaDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    public long insertar(Meta m) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", m.getTitulo());
        values.put("descripcion", m.getDescripcion());
        values.put("progreso", m.getProgreso()); // 0..100
        values.put("completada", m.getCompletada() ? 1 : 0);
        long id = db.insert("metas", null, values);
        db.close();
        return id;
    }

    public boolean actualizar(Meta m) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", m.getTitulo());
        values.put("descripcion", m.getDescripcion());
        values.put("progreso", m.getProgreso());
        values.put("completada", m.getCompletada() ? 1 : 0);
        int filas = db.update("metas", values, "id=?", new String[]{String.valueOf(m.getId())});
        db.close();
        return filas > 0;
    }

    public boolean eliminar(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("metas", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return filas > 0;
    }

    public ArrayList<Meta> listar() {
        ArrayList<Meta> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,titulo,descripcion,progreso,completada FROM metas ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Meta m = new Meta();
                m.setId(cursor.getInt(0));
                m.setTitulo(cursor.getString(1));
                m.setDescripcion(cursor.getString(2));
                m.setProgreso(cursor.getFloat(3));
                m.setCompletada(cursor.getInt(4) == 1);
                lista.add(m);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    public Meta obtenerPorId(int idMeta) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id,titulo,descripcion,progreso,completada FROM metas WHERE id=?",
                new String[]{String.valueOf(idMeta)});
        if (!c.moveToFirst()) {
            c.close();
            db.close();
            return null;
        }
        Meta m = new Meta();
        m.setId(c.getInt(0));
        m.setTitulo(c.getString(1));
        m.setDescripcion(c.getString(2));
        m.setProgreso(c.getFloat(3));
        m.setCompletada(c.getInt(4) == 1);
        c.close();
        db.close();
        return m;
    }

    public void recalcularProgreso(int metaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor total = db.rawQuery("SELECT COUNT(*) FROM submetas WHERE metaId=?", new String[]{String.valueOf(metaId)});
        Cursor completadas = db.rawQuery("SELECT COUNT(*) FROM submetas WHERE metaId=? AND completada=1", new String[]{String.valueOf(metaId)});

        int t = 0, c = 0;
        if (total.moveToFirst()) t = total.getInt(0);
        if (completadas.moveToFirst()) c = completadas.getInt(0);

        total.close();
        completadas.close();
        db.close();

        float progresoPerc = (t == 0) ? 0f : ((float) c / (float) t) * 100f;
        actualizarProgreso(metaId, progresoPerc);
    }

    private void actualizarProgreso(int metaId, float progreso) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("progreso", progreso);
        values.put("completada", progreso >= 100f ? 1 : 0);
        db.update("metas", values, "id=?", new String[]{String.valueOf(metaId)});
        db.close();
    }
}
