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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectotgils.Utilidades.ConvertirBitmap;
import com.example.proyectotgils.Utilidades.utilidades;

import java.util.ArrayList;
import java.util.Locale;

public class InterpreteVoz extends Fragment {

    private ImageButton btnMicrofono;
    private ImageView imgInterpretada;
    private TextToSpeech toSpeech;
    private EditText palabra;
    private TextView textpalabra;
    String mostrarPälabra = null;
    int resultado;
    private View view;
    protected ConexionSQLiteHelper conn1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        conn1=new ConexionSQLiteHelper(getContext(), utilidades.NOMBRE_DB,null, utilidades.VERSION_DB);
        view = inflater.inflate(R.layout.fragment_interprete_voz, container, false);
        btnMicrofono = view.findViewById(R.id.btn_microfono);
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
                    SQLiteDatabase db=conn1.getWritableDatabase();
                    palabra.setText(resultado.get(0));
                    textpalabra.setText(resultado.get((0)));
                    //String img_interp = conn1.ObtenerDatos(palabra.getText().toString().trim().toLowerCase());
                    //mostrarPälabra = palabra.getText().toString().trim();
                    //textpalabra.setText(mostrarPälabra);

                    /*if (img_interp != null){
                       // Bitmap bitmap = ConvertirBitmap.ObtenerImagen();
                       // imgInterpretada.setImageBitmap(bitmap);
                        toSpeech.speak("Usted ha dicho "+resultado.get(0),TextToSpeech.QUEUE_FLUSH, null);
                    }

                     else if(palabra.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Debe ingresar una palabra!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        toSpeech.speak("Esta palabra no existe actualmente",TextToSpeech.QUEUE_FLUSH, null);
                        //Toast.makeText(getContext(), "La "+palabra.getText().toString().trim()+" No existe!", Toast.LENGTH_SHORT).show();
                    }
                    db.close();*/

                }
                break;


        }
    }



    public void TTS(View view){
        switch (view.getId()){
            case  R.id.btn_microfono:
                if (resultado == TextToSpeech.LANG_MISSING_DATA || resultado == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(view.getContext(),"Aqui no es soportado este componente", Toast.LENGTH_SHORT).show();
                }else{
                    String text = palabra.getText().toString();
                    toSpeech.speak(text,TextToSpeech.QUEUE_FLUSH, null);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (toSpeech != null){
            toSpeech.stop();
            toSpeech.shutdown();
        }
        if(conn1 != null)
            conn1.close();
    }


}
