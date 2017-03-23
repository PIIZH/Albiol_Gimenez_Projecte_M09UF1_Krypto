package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.R;

/**
 * Created by miquelgimenez on 23/03/17.
 */

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Thread splashScreen = new Thread(){
            @Override
            public  void run(){
                try{
                    sleep(3000);
                    Intent splash_intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(splash_intent);
                    finish();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splashScreen.start();
    }

}
