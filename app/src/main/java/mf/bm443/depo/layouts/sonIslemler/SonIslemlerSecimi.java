package mf.bm443.depo.layouts.sonIslemler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mf.bm443.depo.R;

public class SonIslemlerSecimi extends AppCompatActivity {

    Button btnSonIslemlerUrun, btnSonIslemlerDepo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonislemler_secimi);


        initComponents();
        sonIslemlerDepoButonIslevi();
        sonIslemlerUrunButonIslevi();

    }

    private void sonIslemlerUrunButonIslevi() {
        btnSonIslemlerUrun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SonIslemlerSecimi.this, SonIslemlerUrunler.class);
                startActivity(intent);
            }
        });
    }

    private void sonIslemlerDepoButonIslevi() {
        btnSonIslemlerDepo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SonIslemlerSecimi.this, SonIslemlerDepolar.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        btnSonIslemlerUrun = findViewById(R.id.btnSonIslemlerUrun);
        btnSonIslemlerDepo = findViewById(R.id.btnSonIslemlerDepo);
    }
}
