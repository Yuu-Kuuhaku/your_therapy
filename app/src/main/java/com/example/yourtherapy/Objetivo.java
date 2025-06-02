package com.example.yourtherapy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Objetivo extends AppCompatActivity {

    private ImageButton btnVoltarObjetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);

        // Iniciar o botão que leva até a tela de login novamente
        btnVoltarObjetivo = findViewById(R.id.idImgBtnVoltarObjetivo);

        // Ação do botão
        btnVoltarObjetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}