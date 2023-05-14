package com.firstcode.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Setting the splash Screen v need holder to hold that
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent i = new Intent(Splash_Screen.this , MainActivity.class);
            startActivity(i);
            finish();
        },3000);
    }
}