package com.example.yourtherapy;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.yourtherapy.utils.Constants;
import com.example.yourtherapy.utils.DatabaseHelper;
import com.example.yourtherapy.utils.MoodAdapter;
import com.example.yourtherapy.utils.MoodEntry;

import java.util.List;

public class Historico extends AppCompatActivity {

    private ImageButton btnVoltarHistorico;
    private ListView listViewTextos;           // Lista visual onde os textos aparecerão
    private DatabaseHelper dbHelper;           // Instância do banco
    private ArrayAdapter<MoodEntry> adapter;      // Adapter para conectar os dados com a lista
    private List<MoodEntry> moodList;          // Lista de Sentimentos salvos

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);


        SharedPreferences preferences =  getSharedPreferences(Constants.ARQUIVO_PREFERENCIA, 0);

        if(preferences.contains(Constants.KEY_USUARIO)){

            userName = preferences.getString(Constants.KEY_USUARIO, "Sem nome");

        }

        listViewTextos = findViewById(R.id.idListViewHistorico); // Referencia o ListView
        dbHelper = new DatabaseHelper(this);                 // Inicializa o banco
        moodList = dbHelper.obterTodosSentimentosPorUsuario(userName);           // Busca os textos no banco

        // Iniciar o botão de voltar
        btnVoltarHistorico = findViewById(R.id.idImgBtnVoltarHistorico);

        // Cria e aplica um adapter para exibir os textos no ListView
        adapter =  new MoodAdapter(this, moodList);
        listViewTextos.setAdapter(adapter);



        // Ação do botão
        btnVoltarHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Abrir a tela em questão
                Intent intent = new Intent(Historico.this, TelaInicio.class);

                // Inicia a nova Activity
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.idBottomNavHistorico);
        bottomNav.setSelectedItemId(R.id.nav_historic); // marca como selecionado

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    startActivity(new Intent(this, TelaInicio.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_gym:
                    startActivity(new Intent(this, TelaExercicios.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_article:
                    startActivity(new Intent(this, Frases.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_historic:
                    return true; // já está nesta tela
                case R.id.nav_sign_out:

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