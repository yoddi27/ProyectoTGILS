package com.example.proyectotgils;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectotgils.Utilidades.ConvertirBitmap;
import com.example.proyectotgils.Utilidades.utilidades;
import com.example.proyectotgils.dao.Palabra;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.view.View.GONE;

public class ConsultarPalabra extends Fragment {

    View view;
    private EditText editarPalabra;
    private Button btnConsultar;
    private GifImageView imgView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_consultar_palabra, container, false);

        editarPalabra = view.findViewById(R.id.edit_palabra);
        btnConsultar = view.findViewById(R.id.btn_consultar);
        imgView = view.findViewById(R.id.img_view);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
                Palabra data = conn.ObtenerDatos(editarPalabra.getText().toString().trim().toLowerCase());
                SQLiteDatabase db=conn.getWritableDatabase();

                if (data != null) {

                    Bitmap bitmap = null;

                    try{
                        FileInputStream entradaAnimacionGif = new FileInputStream(getContext().getFilesDir().getPath()+ "/" + data.getNombreArchivoEquipo());
                        byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                        imgView.setBytes(bytes);
                        imgView.startAnimation();
                        //bitmap = BitmapFactory.decodeStream(fileInputStream);

                        //byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                        //imgView.setBytes(bytes);
                        //imgView.startAnimation();


                    }catch (IOException io){
                        io.printStackTrace();
                    }

                    //imgView.setImageBitmap(bitmap);

                   // imageView.setImageBitmap(bitmap);
                  //  Bitmap bitmap = ConvertirBitmap.ObtenerImagen(data);
                   /* try {
                        InputStream inputStream = getActivity().getAssets().open(data);
                        byte[] bytes = IOUtils.toByteArray(inputStream);
                        imgView.setBytes(bytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                   // imgView.setImageBitmap(bitmap);
                   // imgView.startAnimation();
                    //Bitmap img = new Bitmap( "gracias.gif" );
                    //byte[] imagen = ConvertirBitmap.getBytes(bitmap);
                    //imgView.setBytes(imagen);

                    //imgView.setImageBitmap("");
                    //imgView.startAnimation();
                    //InputStream inputStream = getAssets().open("cargando.gif");
                   // Toast.makeText( getContext(), "data: "+data, Toast.LENGTH_LONG ).show();
                    //imgView.setBytes(data);
                    //imgView.startAnimation();

                }

                else if(editarPalabra.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Debe ingresar una palabra!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "La palabra "+editarPalabra.getText().toString().trim()+" No existe!", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
        return view;
    }
}
