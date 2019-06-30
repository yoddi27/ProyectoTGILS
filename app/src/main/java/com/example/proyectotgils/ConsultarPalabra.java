package com.example.proyectotgils;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.utilidades;
import com.example.proyectotgils.dao.Palabra;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class ConsultarPalabra extends Fragment {

    View view;
    private EditText editarPalabra;
    private Button btnConsultar;
    private GifImageView imgView;
    protected ConexionSQLiteHelper conn;
    protected SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_consultar_palabra, container, false);
        editarPalabra = view.findViewById(R.id.edit_palabra);
        btnConsultar = view.findViewById(R.id.btn_consultar);
        imgView = view.findViewById(R.id.img_view);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarImgXPalabra();
            }
        });
        return view;
    }

    public void consultarImgXPalabra(){
        conn = new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        Palabra datos = conn.ObtenerDatos(editarPalabra.getText().toString().trim().toLowerCase());
        db=conn.getWritableDatabase();

        if (datos != null) {
            try{
                FileInputStream entradaAnimacionGif = new FileInputStream(getContext().getFilesDir().getPath()+ "/" + datos.getNombreArchivoEquipo());
                byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                imgView.setBytes(bytes);
                imgView.startAnimation();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
        else if(editarPalabra.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Debe ingresar una palabra!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "La palabra "+"'"+editarPalabra.getText().toString().trim()+"'"+" No existe!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
