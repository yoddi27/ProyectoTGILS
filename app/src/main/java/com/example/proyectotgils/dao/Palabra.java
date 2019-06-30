package com.example.proyectotgils.dao;

public class Palabra {

    private int ID;
    private String nombrePalabra;
    private String nombreArchivo;
    private String nombreArchivoEquipo;

    public Palabra() {
    }

    public Palabra(int ID, String nombrePalabra, String nombreArchivo, String nombreArchivoEquipo) {
        this.ID = ID;
        this.nombrePalabra = nombrePalabra;
        this.nombreArchivo = nombreArchivo;
        this.nombreArchivoEquipo = nombreArchivoEquipo;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombrePalabra() {
        return nombrePalabra;
    }

    public void setNombrePalabra(String nombrePalabra) {
        this.nombrePalabra = nombrePalabra;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getNombreArchivoEquipo() {
        return nombreArchivoEquipo;
    }

    public void setNombreArchivoEquipo(String nombreArchivoEquipo) {
        this.nombreArchivoEquipo = nombreArchivoEquipo;
    }
}
