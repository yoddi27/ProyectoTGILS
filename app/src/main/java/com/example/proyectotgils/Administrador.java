package com.example.proyectotgils;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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

import java.text.Normalizer;

public class Administrador extends Fragment {

    private View view;
    private Button btnlogin;
    private EditText txtuser;
    private EditText txtcont;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_administrador, container, false);
        btnlogin = view.findViewById(R.id.btn_login);
        txtuser = view.findViewById(R.id.txtusuario);
        txtcont = view.findViewById(R.id.txtcontrasenia);
       // txtuser.setText("admin");
       // txtcont.setText("admin");

        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Login();
            }
        });
        return view;
    }

    private void Login(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        SQLiteDatabase db=conn.getWritableDatabase();
        try {
            Cursor cursor = conn.VerificarLogin(txtuser.getText().toString().trim(), txtcont.getText().toString().trim());
            if (cursor.getCount()>0){
                Intent mintent= new Intent(getActivity(), Menuactivity.class);
                startActivity(mintent);
            }
            else{
                Toast.makeText(getContext(), "Usuario y/o Contraseña Incorrectos", Toast.LENGTH_LONG).show();
            }
            txtuser.setText("");
            txtcont.setText("");
            txtuser.findFocus();

        }catch (SQLException e){
            e.printStackTrace();
        }

        db.close();
    }

   /* public static String palabraSinAcento(String palabra){

        String palabra_buscar = Normalizer.normalize(palabra, Normalizer.Form.NFD);
        String cadenaSinAcentos = palabra_buscar.replaceAll("[^\\p{ASCII}]", "");

        return cadenaSinAcentos;
    }*/

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    public static String stripAccents(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }
}