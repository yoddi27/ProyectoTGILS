package com.example.proyectotgils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.ConvertirBitmap;
import com.example.proyectotgils.Utilidades.utilidades;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ForkJoinPool;

import static android.app.Activity.RESULT_OK;

public class GuardarPalabra extends Fragment {

    private static final int SELECCIONAR_IMAGEN = 100;
    private View view;
    private ImageView imagenGif;
    private Button btnCargar, btnGuardar;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_guardar_palabra, container, false);

        imagenGif =  view.findViewById(R.id.image_view);
        btnCargar =  view.findViewById(R.id.btn_cargar);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarImagen();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvertirSrcImgaBitmap();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECCIONAR_IMAGEN && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            imagenGif.setImageURI(path);
            btnGuardar.setEnabled(true);
        }
    }

    private void ConvertirSrcImgaBitmap(){
        //Convertir src de la imagen a mapa de bits (Bitmap)
        View mostrar;
        final Bitmap bitmap = ((BitmapDrawable)imagenGif.getDrawable()).getBitmap();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        mostrar = inflater.inflate(R.layout.dialogo_guardar, null);
        final EditText editText = mostrar.findViewById(R.id.txt_palabra);
        builder.setView(mostrar);

        //Set button
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
            SQLiteDatabase db=conn.getWritableDatabase();

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String dato_palabra = conn.validarPalabra(editText.getText().toString().trim());

                if (dato_palabra.equals(editText.getText().toString().trim())) {
                    Toast.makeText( getContext(), "La palabra "+dato_palabra+" ya existe, no se puede guardar!", Toast.LENGTH_LONG ).show();
                } else {
                    if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                        conn.InsertarDatos(editText.getText().toString().trim(), ConvertirBitmap.getBytes( bitmap ) );
                        Toast.makeText( getContext(), dato_palabra+ editText.getText().toString().trim()+"Palabra "+editText.getText().toString().trim()+" guardada con éxito!", Toast.LENGTH_LONG ).show();
                    } else {
                        Toast.makeText( getContext(), dato_palabra+"Campo Vacío, Debe ingresar la palabra de la imagen!", Toast.LENGTH_LONG ).show();
                    }
                    db.close();
                }
            }
        });

        builder.show();
    }

    private void CargarImagen(){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECCIONAR_IMAGEN);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECCIONAR_IMAGEN);
    }

}
