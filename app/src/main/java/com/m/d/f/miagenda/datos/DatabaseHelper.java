package com.m.d.f.miagenda.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "miagenda_db";
    private static final int DATABASE_VERSION = 7;

    // Tabla PERFIL (App monousuario)
    private static final String CREATE_TABLE_PERFIL =
            "CREATE TABLE perfil (" +
                    "id INTEGER PRIMARY KEY DEFAULT 1," +
                    "nombre TEXT NOT NULL," +
                    "apellidos TEXT," +
                    "edad INTEGER," +
                    "imagen_uri TEXT" +
                    ");";

    private static final String CREATE_TABLE_EVENTOS =
            "CREATE TABLE eventos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "titulo TEXT NOT NULL," +
                    "fecha INTEGER NOT NULL," +
                    "hora TEXT NOT NULL," +
                    "descripcion TEXT," +
                    "categoria TEXT NOT NULL," +
                    "recordatorio INTEGER NOT NULL DEFAULT 0" +
                    ");";

    // Tabla METAS
    private static final String CREATE_TABLE_METAS =
            "CREATE TABLE metas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titulo TEXT NOT NULL, " +
                    "descripcion TEXT, " +
                    "progreso REAL DEFAULT 0, " +
                    "completada INTEGER DEFAULT 0" +
                    ");";

    // Tabla SUBMETAS
    private static final String CREATE_TABLE_SUBMETAS =
            "CREATE TABLE submetas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "metaId INTEGER NOT NULL, " +
                    "titulo TEXT NOT NULL, " +
                    "descripcion TEXT, " +
                    "completada INTEGER DEFAULT 0, " +
                    "FOREIGN KEY(metaId) REFERENCES metas(id) ON DELETE CASCADE" +
                    ");";

    // Relación EVENTO ↔ META (Muchos a muchos)
    private static final String CREATE_TABLE_EVENTO_META =
            "CREATE TABLE evento_meta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "eventoId INTEGER NOT NULL, " +
                    "metaId INTEGER NOT NULL, " +
                    "FOREIGN KEY(eventoId) REFERENCES eventos(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(metaId) REFERENCES metas(id) ON DELETE CASCADE" +
                    ");";

    // Relación EVENTO ↔ SUBMETA (Muchos a muchos)
    private static final String CREATE_TABLE_EVENTO_SUBMETA =
            "CREATE TABLE evento_submeta (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "eventoId INTEGER NOT NULL, " +
                    "subMetaId INTEGER NOT NULL, " +
                    "FOREIGN KEY(eventoId) REFERENCES eventos(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY(subMetaId) REFERENCES submetas(id) ON DELETE CASCADE" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PERFIL);
        db.execSQL(CREATE_TABLE_EVENTOS);
        db.execSQL(CREATE_TABLE_METAS);
        db.execSQL(CREATE_TABLE_SUBMETAS);
        db.execSQL(CREATE_TABLE_EVENTO_META);
        db.execSQL(CREATE_TABLE_EVENTO_SUBMETA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS perfil");
        db.execSQL("DROP TABLE IF EXISTS eventos");
        db.execSQL("DROP TABLE IF EXISTS metas");
        db.execSQL("DROP TABLE IF EXISTS submetas");
        db.execSQL("DROP TABLE IF EXISTS evento_meta");
        db.execSQL("DROP TABLE IF EXISTS evento_submeta");
        onCreate(db);
    }
}
