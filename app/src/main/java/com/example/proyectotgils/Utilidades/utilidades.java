package com.example.proyectotgils.Utilidades;

public class utilidades {
    //BD
    public static final String NOMBRE_DB = "bdils";
    public static final int VERSION_DB = 5;

    //Campos de la tabla usuario
    public static final String TBL_USUARIO = "tbl_usuario";
    public static final String CAMPO_ID_USUARIO = "id_usuario";
    public static final String CAMPO_USUARIO = "usuario";
    public static final String CAMPO_CONTRASENIA= "contrasenia";

    //usuarios admin quemado
    public static final String USER = "admin";
    public static final String PASS = "admin";

    //Campos de la tabla palabra interpretada
    public static final String TBL_PALABRA = "tbl_palabra";
    public static final String CAMPO_ID_PALABRA = "id_palabra";
    public static final String CAMPO_PALABRA = "palabra";
    public static final String CAMPO_NOMBREIMGARCHIVO = "nombre_img_archivo";
    public static final String CAMPO_NOMBREIMGARCHIVOEQUIPO = "nombre_img_archivo_equipo";


    //Creación tabla usuario
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " +
            ""+TBL_USUARIO+" ("+CAMPO_ID_USUARIO+" " +
            "INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_USUARIO+
            " TEXT NOT NULL,"+CAMPO_CONTRASENIA+" TEXT NOT NULL)";

    //Creación tabla palabra
    public static final String CREAR_TABLA_PALABRA = "CREATE TABLE " + TBL_PALABRA+" (" +
            CAMPO_ID_PALABRA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            CAMPO_PALABRA+" TEXT NOT NULL,"+
            CAMPO_NOMBREIMGARCHIVO+" TEXT NOT NULL,"+
            CAMPO_NOMBREIMGARCHIVOEQUIPO+" TEXT NOT NULL)";

    //Insertando usuario
    public static final String INSERTAR_USUARIO = "INSERT INTO " + TBL_USUARIO
            +" ("
            +CAMPO_USUARIO+","+CAMPO_CONTRASENIA+")" +
            " VALUES ('"+USER+"','"+PASS+"')";
}