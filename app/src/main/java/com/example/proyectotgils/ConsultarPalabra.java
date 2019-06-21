package com.example.proyectotgils;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.ConvertirBitmap;
import com.example.proyectotgils.Utilidades.utilidades;

public class ConsultarPalabra extends Fragment {

    View view;
    private EditText editarPalabra;
    private Button btnConsultar;
    private ImageView imgView;



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
                byte[] data = conn.ObtenerDatos(editarPalabra.getText().toString().trim());
                SQLiteDatabase db=conn.getWritableDatabase();

                if (data != null){
                    Bitmap bitmap = ConvertirBitmap.ObtenerImagen(data);
                    imgView.setImageBitmap(bitmap);
                }
                else if(editarPalabra.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), editarPalabra.getText().toString().trim()+"Debe ingresar una palabra!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), editarPalabra.getText().toString().trim()+" No existe!", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });


        return view;
    }

}
