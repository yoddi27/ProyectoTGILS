package com.example.proyectotgils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.proyectotgils.Utilidades.utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(utilidades.CREAR_TABLA_PALABRA);
        db.execSQL(utilidades.INSERTAR_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TBL_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+utilidades.TBL_PALABRA);
        onCreate(db);
    }

    //Cursor me permite recorrer los datos
    public Cursor VerificarLogin(String user, String password) throws SQLException {
        Cursor mcursor=null;
        //llenamos el cursor
        mcursor=this.getReadableDatabase().query(utilidades.TBL_USUARIO, new String[]{utilidades.CAMPO_ID_USUARIO,
               utilidades.CAMPO_USUARIO, utilidades.CAMPO_CONTRASENIA}, "usuario like '" + user+"'"+"and contrasenia like'"+
                password+"'", null, null, null,null);
                //Nota buscar como haria las comillas a partir de usuario like
        return mcursor;
    }

    public void InsertarDatos(String palabra_gif, byte[] Image_GIF) throws SQLException{
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(utilidades.CAMPO_PALABRA, palabra_gif);
        valores.put(utilidades.CAMPO_GIF, Image_GIF);
        db.insert(utilidades.TBL_PALABRA, null, valores);
    }

    public byte[] ObtenerDatos(String palabra_gif){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] seleccionar = {utilidades.CAMPO_GIF};

        qb.setTables(utilidades.TBL_PALABRA);

        Cursor cursor = qb.query(db, seleccionar, "palabra = ?", new String[]{palabra_gif},null,null,null);
        byte[] resultado = null;
        if (cursor.moveToFirst()) {
            do {
                resultado = cursor.getBlob(cursor.getColumnIndex(utilidades.CAMPO_GIF));
            }while (cursor.moveToNext());
        }
        return resultado;
    }

    //Validar si la palabra existe, no permita guardarla
    public String validarPalabra(String palabra){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] campoPalabra = {utilidades.CAMPO_PALABRA};
        qb.setTables(utilidades.TBL_PALABRA);

        Cursor cursor = qb.query(db, campoPalabra, "palabra = ?", new String[]{palabra},null,null,null);
        String resultado = null;

        if (cursor.moveToFirst()) {
            do {
                resultado = cursor.getColumnName(cursor.getColumnIndex(utilidades.CAMPO_PALABRA));
            }while (cursor.moveToNext());
        }
        return resultado;
    }

}
