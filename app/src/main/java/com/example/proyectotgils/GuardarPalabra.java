package com.example.proyectotgils;

import android.Manifest;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.utilidades;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class GuardarPalabra extends Fragment {

    private static final int SELECCIONAR_IMAGEN = 100;
    private View view;
    private GifImageView imagenGif;
    private Button btnCargar, btnGuardar;
    private EditText palabra;
    private InputStream entradaStreamGif;
    private Uri uri;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_guardar_palabra, container, false);
        imagenGif =  view.findViewById(R.id.image_view);
        palabra = view.findViewById(R.id.txtpalabra_gif);
        btnCargar =  view.findViewById(R.id.btn_cargar);
        btnGuardar = view.findViewById(R.id.btn_guardar);

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarImagenGif();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECCIONAR_IMAGEN && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            imagenGif.setImageURI(uri);
            try {
                entradaStreamGif = getContext().getContentResolver().openInputStream(uri);
                InputStream entradaAnimacionGif = getContext().getContentResolver().openInputStream(uri);
                byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                imagenGif.setBytes(bytes);
                imagenGif.startAnimation();

            }catch(IOException ex){
                ex.printStackTrace();
            }
            btnGuardar.setEnabled(true);
        }
    }

    private void copiarArchivo(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int read;

        while((read = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, read);
        }
    }

    private void guardarImagenGif(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        SQLiteDatabase db=conn.getWritableDatabase();

        String nombreArchivoEquipo;
        String nombreOriginalArchivo;
        String datoPalabra;

        datoPalabra = conn.validarPalabra(palabra.getText().toString().trim().toLowerCase());

        if (datoPalabra != null) {
            Toast.makeText( getContext(), "La palabra "+datoPalabra+" ya existe, no se puede guardar!", Toast.LENGTH_LONG ).show();
        }
        else {
            if (!TextUtils.isEmpty(palabra.getText().toString().trim())) {
                nombreArchivoEquipo = UUID.randomUUID().toString()+".gif";
                nombreOriginalArchivo = FilenameUtils.getName(uri.getPath());

                try {
                    //Se almacena en base de datos
                    conn.InsertarDatos(palabra.getText().toString().trim().toLowerCase(), nombreOriginalArchivo, nombreArchivoEquipo);
                    //se asegura que no se guarden imagenes con el mismo nombre...
                    if(almacenarArchivo(nombreArchivoEquipo)){
                        Toast.makeText( getContext(), "La palabra e imagen guardados con éxito!", Toast.LENGTH_LONG ).show();
                    }else{
                        Toast.makeText( getContext(), "Se presento un problema con el almacenamiento de la imagen!", Toast.LENGTH_LONG ).show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Toast.makeText( getContext(), "Palabra "+palabra.getText().toString().trim()+" guardada con éxito!", Toast.LENGTH_LONG ).show();
            }
            else {
                Toast.makeText( getContext(), "Campo Vacío, debe ingresar la palabra de la imagen!", Toast.LENGTH_LONG ).show();
            }
            db.close();
        }
    }

    private boolean almacenarArchivo(String nombreArchivo) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        File salidaArchivo = null;
        boolean guarda = false;

        try {
            inputStream = entradaStreamGif;
            salidaArchivo = new File( getContext().getFilesDir().getAbsolutePath(), nombreArchivo);
            outputStream = new FileOutputStream(salidaArchivo);

            copiarArchivo(inputStream, outputStream);
            guarda = true;
        } catch (IOException e) {
            Log.e( "tag", "Error al copiar el archivo: " + nombreArchivo, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return guarda;
    }

    private void cargarImagen(){

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, SELECCIONAR_IMAGEN);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, SELECCIONAR_IMAGEN);
    }
}




