package com.app.linio_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        launchSplashScreen();
    }
    private void launchSplashScreen() {
        Thread timer = new Thread() {
            @Override
            public  void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {
                    Toast.makeText(SplashScreen.this,"Unable to load splash screen",Toast.LENGTH_LONG).show();
                }
                Intent main = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(main);
            }
        };
        timer.start();
    }
}
