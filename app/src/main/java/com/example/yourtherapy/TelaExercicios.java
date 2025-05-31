package com.example.yourtherapy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yourtherapy.utils.Constants;

public class TelaExercicios extends AppCompatActivity {

    private ImageButton btnVoltarExerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exercicios);

        // Iniciar o botão de voltar
        btnVoltarExerc = findViewById(R.id.idImgBtnVoltarExercicio);

        // Ação do botão
        btnVoltarExerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abrir a tela em questão
                Intent intent = new Intent(TelaExercicios.this, TelaInicio.class);

                // Inicia a nova Activity
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.idBottomNavExercicios);
        bottomNav.setSelectedItemId(R.id.nav_gym); // marca como selecionado

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, TelaInicio.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_gym:
                    return true; // já está nesta tela
                case R.id.nav_article:
                    startActivity(new Intent(this, Frases.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_historic:
                    startActivity(new Intent(this, Historico.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_sign_out:

                    SharedPreferences preferences = getSharedPreferences(Constants.ARQUIVO_PREFERENCIA, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(Constants.KEY_USUARIO);
                    editor.commit();

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                    return true;
            }
            return false;
        });
    }
}