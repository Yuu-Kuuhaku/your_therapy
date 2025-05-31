package com.example.yourtherapy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Objetivo extends AppCompatActivity {

    private Button iniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objetivo);

        // Iniciar o botão que leva até a tela de login novamente
        iniciar = findViewById(R.id.idBtnIniciar);

        // Ação do botão
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abrir a tela em questão
                Intent intent = new Intent(Objetivo.this, MainActivity.class);

                // Inicia a nova Activity
                startActivity(intent);
            }
        });
    }
}