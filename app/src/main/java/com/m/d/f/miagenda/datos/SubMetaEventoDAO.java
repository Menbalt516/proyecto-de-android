package com.m.d.f.miagenda.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SubMetaEventoDAO {
    private DatabaseHelper dbHelper;

    public SubMetaEventoDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long asociar(int subMetaId, int eventoId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();

        v.put("subMetaId", subMetaId);
        v.put("eventoId", eventoId);

        long id = db.insert("SubMetaEvento", null, v);
        db.close();
        return id;
    }
}

