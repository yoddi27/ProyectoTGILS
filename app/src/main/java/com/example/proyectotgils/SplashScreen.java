package com.example.proyectotgils;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    private ImageView logoImage;
    private ProgressBar progress_Bar;
    private final int TIEMPO_SPLASH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Ocultar statusBar: Barra del celular
        View ventana = getWindow().getDecorView();
        int UiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        ventana.setSystemUiVisibility(UiOptions);

        logoImage = findViewById( R.id.logo);
        progress_Bar = findViewById(R.id.progressBar);
        progress_Bar.setVisibility(progress_Bar.VISIBLE);

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, MainActivity.class));
                SplashScreen.this.finish();
            }
        }, TIEMPO_SPLASH);

    }
}
