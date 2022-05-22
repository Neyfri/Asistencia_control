package com.example.asistencia_control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="control_asistencia";
    private final static int DATABASE_VERSION=1;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE reg_participantes(cedula INTEGER PRIMARY KEY,nombre TEXT,apellido TEXT,activo INTEGER)";
        db.execSQL(query);
        query="CREATE TABLE lista_asistencia(id INTEGER PRIMARY KEY AUTOINCREMENT,fecha TEXT,nombre TEXT,apellido TEXT,cedula INTEGER,entrada TEXT,salida TEXT,presencia TEXT,FOREIGN KEY (cedula) REFERENCES reg_participantes(cedula))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS reg_participantes");
        db.execSQL("DROP TABLE IF EXISTS lista_asistencia");
    }
}
