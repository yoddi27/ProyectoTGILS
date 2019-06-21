package com.example.proyectotgils.Utilidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ConvertirBitmap {

    public static byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Bitmap ObtenerImagen (byte[] Imagen){
        return BitmapFactory.decodeByteArray(Imagen, 0, Imagen.length);
    }


}
