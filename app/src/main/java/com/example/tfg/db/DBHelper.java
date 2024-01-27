package com.example.tfg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.tfg.model.Jugador;
import com.example.tfg.model.Partida;

public class DBHelper extends SQLiteOpenHelper {

        // Constantes para la base de datos
        private static final String DATABASE_NAME = "aventura_db";
        private static final int DATABASE_VERSION = 1;

        // Tablas
        private static final String TABLE_JUGADOR = "jugador";
        private static final String TABLE_PARTIDA = "partida";
        private static final String TABLE_DECISION = "decision";

        // Columnas comunes
        private static final String KEY_ID = "id";
        private static final String KEY_ID_PARTIDA = "id_partida";
        private static final String KEY_DECISION = "decision";
        private static final String KEY_ESTADO = "estado";

        // Columnas específicas para cada tabla
        private static final String KEY_NOMBRE = "nombre";
        private static final String KEY_FECHA_CREACION = "fecha_creacion";

        // Sentencias SQL para crear tablas
        private static final String CREATE_TABLE_JUGADOR = "CREATE TABLE " + TABLE_JUGADOR + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOMBRE + " TEXT" + ")";

        private static final String CREATE_TABLE_PARTIDA = "CREATE TABLE " + TABLE_PARTIDA + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_PARTIDA + " INTEGER,"
                + KEY_FECHA_CREACION + " DATETIME,"
                + " FOREIGN KEY(" + KEY_ID_PARTIDA + ") REFERENCES " + TABLE_JUGADOR + "(" + KEY_ID + "))";

        private static final String CREATE_TABLE_DECISION = "CREATE TABLE " + TABLE_DECISION + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID_PARTIDA + " INTEGER,"
                + KEY_DECISION + " TEXT,"
                + KEY_ESTADO + " TEXT,"
                + " FOREIGN KEY(" + KEY_ID_PARTIDA + ") REFERENCES " + TABLE_PARTIDA + "(" + KEY_ID + "))";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas
        db.execSQL(CREATE_TABLE_JUGADOR);
        db.execSQL(CREATE_TABLE_PARTIDA);
        db.execSQL(CREATE_TABLE_DECISION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizaciones de la base de datos
    }

    // Método para registrar un nuevo jugador
    public long registrarJugador(Jugador jugador) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, jugador.getNombre());

        // Insertar fila
        long jugadorId = db.insert(TABLE_JUGADOR, null, values);

        // Cierra la conexión
        db.close();

        return jugadorId;
    }

    // Método para registrar una nueva partida
    public long registrarPartida(Partida partida) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PARTIDA, partida.getIdJugador());
        values.put(KEY_FECHA_CREACION, partida.getFechaCreacion());

        // Insertar fila
        long partidaId = db.insert(TABLE_PARTIDA, null, values);

        // Cierra la conexión
        db.close();

        return partidaId;
    }

    // Método para guardar partida en cualquier momento
    public void guardarPartidaEnCualquierMomento(int idPartida, String decision, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PARTIDA, idPartida);
        values.put(KEY_DECISION, decision);
        values.put(KEY_ESTADO, estado);

        // Insertar fila
        db.insert(TABLE_DECISION, null, values);

        // Cierra la conexión
        db.close();
    }

    // Método para cargar partida
    public String cargarPartida(int idPartida) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Definir las columnas que deseas recuperar
        String[] columnas = {KEY_DECISION, KEY_ESTADO};

        // Definir la cláusula WHERE
        String whereClause = KEY_ID_PARTIDA + "=?";
        String[] whereArgs = {String.valueOf(idPartida)};

        // Realizar la consulta
        Cursor cursor = db.query(TABLE_DECISION, columnas, whereClause, whereArgs, null, null, null);

        String decision = "";
        String estado = "";

        // Verificar las columnas disponibles
        String[] columnNames = cursor.getColumnNames();
        for (String columnName : columnNames) {
            Log.d("Columnas", "Nombre de la columna: " + columnName);
        }

// Verificar si se encontraron datos
        if (cursor != null && cursor.moveToFirst()) {
            decision = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DECISION));
            estado = cursor.getString(cursor.getColumnIndexOrThrow(KEY_ESTADO));
            cursor.close();
        }

        // Cierra la conexión
        db.close();

        // Devuelve el estado recuperado
        return estado;
    }
}

