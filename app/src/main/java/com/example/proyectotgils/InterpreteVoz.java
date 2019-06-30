package com.example.proyectotgils;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyectotgils.Utilidades.utilidades;
import com.example.proyectotgils.dao.Palabra;
import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class InterpreteVoz extends Fragment {

    private View view;
    private ImageButton btnMicrofono;
    private Button btnOk;
    private GifImageView imgInterpretada;
    private TextToSpeech toSpeech;
    private EditText palabra;
    private TextView textpalabra;

    int resultado;

    protected ConexionSQLiteHelper conn;
    protected SQLiteDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        conn=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        view = inflater.inflate(R.layout.fragment_interprete_voz, container, false);
        btnMicrofono = view.findViewById(R.id.btn_microfono);
        btnOk = view.findViewById(R.id.btn_ok);
        imgInterpretada = view.findViewById(R.id.img_interpretada);
        palabra = view.findViewById(R.id.palabra);
        textpalabra = view.findViewById(R.id.text_palabra);

        toSpeech = new TextToSpeech(view.getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    resultado = toSpeech.setLanguage(new Locale("spa", "MEX"));
                }else{
                    Toast.makeText(view.getContext(),"Aqui no es soportado este componente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMicrofono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerEntradaPalabra();
            }
        });

        btnOk.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarImgXPalabra();
            }
        } );
        return view;
    }

    public void obtenerEntradaPalabra(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("spa", "MEX"));

        if (resultado == TextToSpeech.LANG_MISSING_DATA || resultado == TextToSpeech.LANG_NOT_SUPPORTED) {
            Toast.makeText(view.getContext(), "Aqui no es soportado este componente", Toast.LENGTH_SHORT).show();
        }else {
            startActivityForResult(intent, 10);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 10:
                if(resultCode == Activity.RESULT_OK && data != null){
                    ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    db=conn.getWritableDatabase();
                    palabra.setText(resultado.get(0).trim().toLowerCase());
                    textpalabra.setText(resultado.get(0).trim().toLowerCase());
                    Palabra datos = conn.ObtenerDatos(palabra.getText().toString().trim().toLowerCase());

                    if (datos != null){
                        try{
                            FileInputStream entradaAnimacionGif = new FileInputStream(getContext().getFilesDir().getPath()+ "/" + datos.getNombreArchivoEquipo());
                            byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                            imgInterpretada.setBytes(bytes);
                            imgInterpretada.startAnimation();
                        }catch (IOException io){
                            io.printStackTrace();
                        }
                        toSpeech.speak("Usted ha dicho "+resultado.get(0),TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else if(palabra.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Debe ingresar una palabra", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        toSpeech.speak("La palabra "+ palabra.getText().toString()+ " no existe actualmente",TextToSpeech.QUEUE_FLUSH, null);
                    }
                    db.close();
                }
                break;
        }
    }

    public void consultarImgXPalabra(){
        conn = new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        Palabra datos = conn.ObtenerDatos(palabra.getText().toString().trim().toLowerCase());
        db=conn.getWritableDatabase();

        if (datos != null) {
            try{
                FileInputStream entradaAnimacionGif = new FileInputStream(getContext().getFilesDir().getPath()+ "/" + datos.getNombreArchivoEquipo());
                byte[] bytes = IOUtils.toByteArray(entradaAnimacionGif);
                imgInterpretada.setBytes(bytes);
                imgInterpretada.startAnimation();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
        else if(palabra.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Debe ingresar una palabra!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "La palabra "+"'"+palabra.getText().toString().trim()+"'"+" No existe!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toSpeech != null){
            toSpeech.stop();
            toSpeech.shutdown();
        }
        if(conn != null)
            conn.close();
    }
}
