package com.example.yourtherapy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yourtherapy.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private Button logar;
    private Button objetivo;
    private EditText editNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences =  getSharedPreferences(Constants.ARQUIVO_PREFERENCIA, 0);
        // Iniciar o botão que leva a tela objetivo
        objetivo = findViewById(R.id.idBtnObjetivo);

        // Ação do botão
        objetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a tela em questão
                Intent intent = new Intent(MainActivity.this, Objetivo.class);
                // Inicia a nova Activity
                startActivity(intent);
            }
        });

        editNome = findViewById(R.id.idEditTextTextPersonName);
        // Iniciar o botão de logar
        logar = findViewById(R.id.idBtnLogin);

        // Ação do botão
        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = preferences.edit();

                if(editNome.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "O campo usuario é um campo obrigatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                String usuario = editNome.getText().toString();

                editor.putString(Constants.KEY_USUARIO, usuario);

                editor.commit();

                // Abrir a tela em questão
                Intent intent = new Intent(MainActivity.this, TelaInicio.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Inicia a nova Activity
                startActivity(intent);
            }
        });



        if(preferences.contains(Constants.KEY_USUARIO)){

            // Abrir a tela em questão
            Intent intent = new Intent(MainActivity.this, TelaInicio.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Inicia a nova Activity
            startActivity(intent);

        }
    }
}
