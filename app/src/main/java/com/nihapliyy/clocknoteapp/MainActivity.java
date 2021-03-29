package com.nihapliyy.clocknoteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    //Inisiasi Variabel
    private TextView DateNow,contentNote,ClockDigital,buttonTambahCatatan;
    private Button bBatalNote, bSelesaiNote;
    private Dialog dialogNote;
    private EditText buatNote;

    //Coding For Menu Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menuAction)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_action_bar,menuAction);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem itemAction){
        switch (itemAction.getItemId()){
            case R.id.aboutApp:
                Intent gotoAbout = new Intent(this, AboutApp.class);
                startActivity(gotoAbout);
                Animatoo.animateWindmill(MainActivity.this);
                return true;

            case R.id.exitApp :
                getExitDialog();
                return true;
        }

        return false;
    }

    // Coding For Dialog
    private void getExitDialog() {
        AlertDialog.Builder alertDialogBuild = new AlertDialog.Builder(
                this
        );

        // Set Title
        alertDialogBuild.setTitle(" Ingin Keluar dari App ?");

        //Set Configuration Dialog
        alertDialogBuild
                .setCancelable(true)
                .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }

                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        // Build Alert Dialog From Builder
        AlertDialog alertDialog = alertDialogBuild.create();

        // Show Dialog
        alertDialog.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Connect ke Coding XML
        DateNow = findViewById(R.id.dateNow);
        ClockDigital = findViewById(R.id.clockDigital);
        contentNote = findViewById(R.id.content_note);

        //Coding Untuk Kalender Tanggal Hari Ini
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat formatKalender = new SimpleDateFormat("d MMMM yyyy", Locale.US);
        String dateNow = formatKalender.format(calender.getTime());
        DateNow.setText(dateNow);
        //Coding Untuk Kalender Tanggal Hari Ini

        //Update Jam
        final Handler updateClock = new Handler();
        updateClock.post(new Runnable() {
            @Override
            public void run() {
                ClockToday();
                updateClock.postDelayed(this, 0);
            }
        });

        ShowDialogNote();
    }

    //Coding Untuk Jam
    private void ClockToday() {
        Calendar ClockToday = Calendar.getInstance();
        SimpleDateFormat formatDigitalAnalog = new SimpleDateFormat("HH:mm", Locale.US);
        String Clock = formatDigitalAnalog.format(ClockToday.getTime());
        ClockDigital.setText(Clock);
    }

    //Coding For Dialog Note
    private void ShowDialogNote(){
        CallDialog();
        CallComponentsDialog();

    }

    private void CallDialog() {
        dialogNote = new Dialog(MainActivity.this);
        dialogNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNote.setContentView(R.layout.activity_dialog_note);
        dialogNote.setCancelable(false);
        buatNote = dialogNote.findViewById(R.id.createNote);
        bBatalNote = dialogNote.findViewById(R.id.bBatal);
        bSelesaiNote = dialogNote.findViewById(R.id.bSelesai);
        bSelesaiNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catatan = buatNote.getText().toString();
                contentNote.setText(catatan);
                dialogNote.dismiss();
            }
        });
        bBatalNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNote.dismiss();
            }
        });
    }

    private void CallComponentsDialog(){
        contentNote = findViewById(R.id.content_note);
        buttonTambahCatatan = findViewById(R.id.buttonAddCatatan);
        buttonTambahCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogNote.show();
            }
        });
    }

    //Setting SharedPreferences/Simpan Catatan
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences getCatatan = getSharedPreferences("Catatan", MODE_PRIVATE);
        String isiCatatan = getCatatan.getString("get_catatan", "Tidak Ada Catatan");
        contentNote.setText(isiCatatan);
    }

    @Override
    protected void onPause() {
        // Bagian Tampil Catatan Terakhir
        super.onPause();
        SharedPreferences getShared = getSharedPreferences("Catatan", MODE_PRIVATE);
        SharedPreferences.Editor editCatatan = getShared.edit();

        editCatatan.putString("get_catatan", contentNote.getText().toString());
        editCatatan.apply();
    }

    //Setting Button Back Tidak Aktif
    @Override
    public void onBackPressed() {

    }
}