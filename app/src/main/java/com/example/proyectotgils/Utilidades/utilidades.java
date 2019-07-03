package com.example.proyectotgils.Utilidades;

public class utilidades {
    //BD
    public static final String NOMBRE_DB = "bdils";
    public static final int VERSION_DB = 12;

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
            +CAMPO_USUARIO+","
            +CAMPO_CONTRASENIA+")"
            +"VALUES('"+USER+"','"+PASS+"')";

    //Insertando palabrs e imagenes (42 palabras, abecedario y números del 1-10)
    public static final String INSERTAR_PALABRA = "INSERT INTO " + TBL_PALABRA +
            "("
            +CAMPO_PALABRA +","
            +CAMPO_NOMBREIMGARCHIVO+","
            +CAMPO_NOMBREIMGARCHIVOEQUIPO+")"
            +"VALUES('a', 'a', 'a.gif')"+","
            +"('b', 'b', 'b.gif')"+","
            +"('c', 'c', 'c.gif')"+","
            +"('d', 'd', 'd.gif')"+","
            +"('e', 'e', 'e.gif')"+","
            +"('f', 'f', 'f.gif')"+","
            +"('g', 'g', 'g.gif')"+","
            +"('h', 'h', 'h.gif')"+","
            +"('i', 'i', 'i.gif')"+","
            +"('j', 'j', 'j.gif')"+","
            +"('k', 'k', 'k.gif')"+","
            +"('l', 'l', 'l.gif')"+","
            +"('m', 'm', 'm.gif')"+","
            +"('n', 'n', 'n.gif')"+","
            +"('ñ', 'ñ', 'ñ.gif')"+","
            +"('o', 'o', 'o.gif')"+","
            +"('p', 'p', 'p.gif')"+","
            +"('q', 'q', 'q.gif')"+","
            +"('r', 'r', 'r.gif')"+","
            +"('s', 's', 's.gif')"+","
            +"('t', 't', 't.gif')"+","
            +"('u', 'u', 'u.gif')"+","
            +"('v', 'v', 'v.gif')"+","
            +"('w', 'w', 'w.gif')"+","
            +"('x', 'x', 'x.gif')"+","
            +"('y', 'y', 'y.gif')"+","
            +"('z', 'z', 'z.gif')"+","
            +"('uno', '1', '1.gif')"+","
            +"('dos', '2', '2.gif')"+","
            +"('tres', '3', '3.gif')"+","
            +"('cuatro', '4', '4.gif')"+","
            +"('cinco', '5', '5.gif')"+","
            +"('seis', '6', '6.gif')"+","
            +"('siete', '7', '7.gif')"+","
            +"('ocho', '8', '8.gif')"+","
            +"('nueve', '9', '9.gif')"+","
            +"('diez', '10', '10.gif')"+","
            +"('hola', 'hola', 'hola.gif')"+","
            +"('cómo estás', 'comoestas', 'comoestas.gif')"+","
            +"('hola cómo estás', 'holacomoestas', 'holacomoestas.gif')"+","
            +"('adiós', 'adios', 'adios.gif')"+","
            +"('buenos dias', 'buenosdias', 'buenosdias.gif')"+","
            +"('buenas tardes', 'buenastardes', 'buenastardes.gif')"+","
            +"('buenas noches', 'buenasnoches', 'buenasnoches.gif')"+","
            +"('ayer', 'ayer', 'ayer.gif')"+","
            +"('ayudar', 'ayudar', 'ayudar.gif')"+","
            +"('congusto', 'congusto', 'congusto.gif')"+","
            +"('deletrear', 'deletrear', 'deletrear.gif')"+","
            +"('despacio', 'despacio', 'despacio.gif')"+","
            +"('lento', 'lento', 'lento.gif')"+","
            +"('entiendo', 'entiendo', 'entiendo.gif')"+","
            +"('esperar', 'esperar', 'esperar.gif')"+","
            +"('examen', 'examen', 'examen.gif')"+","
            +"('grupos', 'grupos', 'grupos.gif')"+","
            +"('hoy', 'hoy', 'hoy.gif')"+","
            +"('individual', 'individual', 'individual.gif')"+","
            +"('losiento', 'losiento', 'losiento.gif')"+","
            +"('mañana', 'mañana', 'mañana.gif')"+","
            +"('no', 'no', 'no.gif')"+","
            +"('porfavor', 'porfavor', 'porfavor.gif')"+","
            +"('prestar', 'prestar', 'prestar.gif')"+","
            +"('profesor', 'profesor', 'profesor.gif')"+","
            +"('repetir', 'repetir', 'repetir.gif')"+","
            +"('si', 'si', 'si.gif')"+","
            +"('taller', 'taller', 'taller.gif')"+","
            +"('baño', 'baño', 'baño.gif')"+","
            +"('baño hombres', 'bañohombres', 'bañohombres.gif')"+","
            +"('baño mujeres', 'bañomujeres', 'bañomujeres.gif')"+","
            +"('bien', 'bien', 'bien.gif')"+","
            +"('bienvenido', 'bienvenido', 'bienvenido.gif')"+","
            +"('bloques', 'bloques', 'bloques.gif')"+","
            +"('estudiar', 'estudiar', 'estudiar.gif')"+","
            +"('mal', 'mal', 'mal.gif')"+","
            +"('permiso', 'permiso', 'permiso.gif')"+","
            +"('politecnico', 'politecnico', 'politecnico.gif')"+","
            +"('repasar', 'repasar', 'repasar.gif')"+","
            +"('restaurantes', 'restaurantes', 'restaurantes.gif')";

}