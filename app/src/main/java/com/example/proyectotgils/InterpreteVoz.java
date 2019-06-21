package com.example.proyectotgils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class InterpreteVoz extends Fragment {

    private ImageButton btnMicrofono;
    private TextToSpeech toSpeech;
    private EditText palabra;
    int resultado;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_interprete_voz, container, false);
        btnMicrofono = view.findViewById(R.id.btn_microfono);
        palabra = view.findViewById(R.id.palabra);

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
                    palabra.setText(resultado.get(0));
                    if(resultado.get(0).toLowerCase().trim().replace(" ", "").equals("comandoa")){
                        toSpeech.speak("Usted ha dicho "+resultado.get(0),TextToSpeech.QUEUE_FLUSH, null);
                        String[] x = {"A"};
                        //new EnviarMensaje().execute(x);
                    }else if(resultado.get(0).toLowerCase().trim().replace(" ", "").equals("comandob")){
                        toSpeech.speak("Usted ha dicho "+resultado.get(0),TextToSpeech.QUEUE_FLUSH, null);
                        String[] x = {"B"};
                        //new EnviarMensaje().execute(x);
                    }else{
                        toSpeech.speak("Este comando no es valido.",TextToSpeech.QUEUE_FLUSH, null);
                    }

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
    }


}
