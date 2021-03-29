package com.nihapliyy.clocknoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class AboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateWindmill(AboutApp.this);
    }
}