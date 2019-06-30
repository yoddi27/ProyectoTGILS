package com.example.proyectotgils;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.ConvertirBitmap;
import com.example.proyectotgils.Utilidades.utilidades;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.ForkJoinPool;

import static android.app.Activity.RESULT_OK;

public class GuardarPalabra extends Fragment {

    private static final int SELECCIONAR_IMAGEN = 100;
    private View view;
    private GifImageView imagenGif;
    private Button btnCargar, btnGuardar;
    private EditText palabra;
    private InputStream entradaStreamGif;
    private Bitmap bitmap;
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
                CargarImagen();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarImagenGif();
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
                //se captura el archivo .gif
                InputStream entradaAnimacionGif = getContext().getContentResolver().openInputStream(uri);
                byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                imagenGif.setBytes(bytes);
                imagenGif.startAnimation();

                //File archivoGIF = new File( String.valueOf( uri ) );
         //       FileOutputStream fos = new FileOutputStream( archivoGIF );

                //File archivoGIFDes = getContext().getDir( "gracias.gif",Context.MODE_PRIVATE);
               // fos.write(  );

                //OutputStream fileOutputStream = getContext().openFileOutput("gracias.gif", Context.MODE_PRIVATE);


                // copyFile(archivoGIF,  archivoGIFDes);
               // Files.copy(archivoGIF.toPath(), fileOutputStream);




            }catch(IOException ex){
                ex.printStackTrace();
            }
            btnGuardar.setEnabled(true);
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void GuardarImagenGif(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        SQLiteDatabase db=conn.getWritableDatabase();
        String nombreArchivoEquipo;
        String nombreOriginalArchivo;
        ByteArrayOutputStream stream;
        byte[] byteArray;

        String dato_palabra = conn.validarPalabra(palabra.getText().toString().trim().toLowerCase());

        if (dato_palabra != null) {
            Toast.makeText( getContext(), "La palabra "+dato_palabra+" ya existe, no se puede guardar!", Toast.LENGTH_LONG ).show();
        }
        else {
            if (!TextUtils.isEmpty(palabra.getText().toString().trim())) {
                nombreArchivoEquipo = UUID.randomUUID().toString()+".gif";
                nombreOriginalArchivo = FilenameUtils.getName(uri.getPath());
                System.out.println(FilenameUtils.getFullPath( uri.getPath() ));
                //Toast.makeText(getContext(), "Nombre original archivo: "+nombreOriginalArchivo, Toast.LENGTH_SHORT).show();
                try {
                    //Se almacena en base de datos
                    conn.InsertarDatos(palabra.getText().toString().trim().toLowerCase(), nombreOriginalArchivo, nombreArchivoEquipo);

                    //se asegura que no se guarden imagenes con el mismo nombre...
                    if(almacenarArchivo(nombreArchivoEquipo)){
                        Toast.makeText( getContext(), "La palabra fue almacenada exitosamente!", Toast.LENGTH_LONG ).show();
                    }else{
                        Toast.makeText( getContext(), "Se presento un problema con el almacenamiento de la imagen!", Toast.LENGTH_LONG ).show();
                    }
                  /*  bitmap = BitmapFactory.decodeStream(entradaStreamGif);
                    stream = new ByteArrayOutputStream();
                    bitmap.compress( Bitmap.CompressFormat.PNG, 100, stream);
                    byteArray = stream.toByteArray();

                    //salida del archivo
                    FileOutputStream fileOutputStream = getContext().openFileOutput(nombreArchivoEquipo, Context.MODE_PRIVATE);
                    fileOutputStream.write(byteArray);
                    fileOutputStream.close();*/

                } catch (SQLException e) {
                    e.printStackTrace();
                }


                Toast.makeText( getContext(), "Palabra "+palabra.getText().toString().trim()+" guardada con éxito!", Toast.LENGTH_LONG ).show();
            }
            else {
                Toast.makeText( getContext(), dato_palabra+"Campo Vacío, Debe ingresar la palabra de la imagen!", Toast.LENGTH_LONG ).show();
            }
            db.close();
        }

    }

    private boolean almacenarArchivo(String fileName) {
        //String fileName = "emp.gif";
        InputStream in = null;
        OutputStream out = null;
        File outFile = null;
        boolean guardo = false;
        try {
            //in = assetManager.open(fileName);
            in = entradaStreamGif;
            outFile = new File( getContext().getFilesDir().getAbsolutePath(), fileName );
            out = new FileOutputStream( outFile );
            copyFile( in, out );
            guardo = true;
        } catch (IOException e) {
            Log.e( "tag", "Failed to copy asset file: " + fileName, e );
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // NOOP
                }
            }
        }
        return guardo;
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
                String dato_palabra = conn.validarPalabra(editText.getText().toString().trim().toLowerCase());

                if (dato_palabra != null) {
                    Toast.makeText( getContext(), "La palabra "+dato_palabra+" ya existe, no se puede guardar!", Toast.LENGTH_LONG ).show();
                }
                else {
                    if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                        //conn.InsertarDatos(editText.getText().toString().trim().toLowerCase(), ConvertirBitmap.getBytes( bitmap ) );
                        Toast.makeText( getContext(), "Palabra "+editText.getText().toString().trim()+" guardada con éxito!", Toast.LENGTH_LONG ).show();
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
       // File picture = getContext().getDir(Environment.DIRECTORY_PICTURES, Context.MODE_PRIVATE);
                //Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
       // String picName = "yodi.gif";
       // File img = new File( picture, picName );
       // Uri uri = Uri.fromFile( img );
       // intent.putExtra( MediaStore.EXTRA_OUTPUT, uri );
        startActivityForResult(intent, SELECCIONAR_IMAGEN);
    }

}
