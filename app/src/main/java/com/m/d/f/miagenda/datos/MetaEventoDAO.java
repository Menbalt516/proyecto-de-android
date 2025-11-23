package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MetaEventoDAO {
    private DatabaseHelper dbHelper;

    public MetaEventoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long asociar(int metaId, int eventoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("metaId", metaId);
        v.put("eventoId", eventoId);

        long id = db.insert("MetaEvento", null, v);
        db.close();
        return id;
    }
}

