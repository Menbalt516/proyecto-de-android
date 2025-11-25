package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SubMetaEventoDAO {

    private final DatabaseHelper dbHelper;

    public SubMetaEventoDAO(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // AGREGAR VÍNCULO
    public long vincular(int eventoId, int subMetaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("eventoId", eventoId);
        values.put("subMetaId", subMetaId);

        return db.insert("evento_submeta", null, values);
    }

    // ELIMINAR VÍNCULO
    public boolean desvincular(int eventoId, int subMetaId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("evento_submeta", "eventoId=? AND subMetaId=?", new String[]{
                String.valueOf(eventoId),
                String.valueOf(subMetaId)
        });
        return filas > 0;
    }

    // SABER SI ESTA VINCULADO
    public boolean estaVinculado(int eventoId, int subMetaId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id FROM evento_submeta WHERE eventoId=? AND subMetaId=?",
                new String[]{String.valueOf(eventoId), String.valueOf(subMetaId)}
        );

        boolean existe = c.moveToFirst();
        c.close();
        return existe;
    }
}
