package com.example.yourtherapy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button; // Importar Button
import android.widget.ImageButton;
import android.widget.TextView; // Importar TextView

import com.example.yourtherapy.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; // Importar Random

public class Frases extends AppCompatActivity {

    private ImageButton btnVoltarFrases;
    private Button idBtnFrases; // Novo: Referência ao botão para gerar frases
    private TextView idTextFrases; // Novo: Referência ao TextView para exibir a frase
    private List<String> motivationalPhrases; // Novo: Lista para armazenar as frases motivacionais
    private Random random; // Novo: Objeto Random para selecionar frases aleatoriamente

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frases);

        // Iniciar o botão de voltar
        btnVoltarFrases = findViewById(R.id.idImgBtnVoltarFrases);

        // Ação do botão de voltar
        btnVoltarFrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir a tela em questão
                Intent intent = new Intent(Frases.this, TelaInicio.class);
                // Inicia a nova Activity
                startActivity(intent);
            }
        });

        // NOVO: Inicializa os componentes da UI para as frases
        idBtnFrases = findViewById(R.id.idBtnFrases); // Encontra o botão pelo ID
        idTextFrases = findViewById(R.id.idTextFrases); // Encontra o TextView pelo ID

        // NOVO: Inicializa a lista de frases motivacionais
        motivationalPhrases = new ArrayList<>();
        motivationalPhrases.add("Sua saúde mental é uma prioridade. Cuide-se com carinho.");
        motivationalPhrases.add("Pequenos passos todos os dias levam a grandes mudanças na sua mente.");
        motivationalPhrases.add("Permita-se sentir. Todas as emoções são válidas.");
        motivationalPhrases.add("Você não está sozinho(a). Buscar ajuda é um sinal de força.");
        motivationalPhrases.add("Respire fundo. Um momento de calma pode mudar tudo.");
        motivationalPhrases.add("Seja gentil consigo mesmo(a). Você está fazendo o seu melhor.");
        motivationalPhrases.add("Sua jornada é única. Celebre cada progresso, por menor que seja.");
        motivationalPhrases.add("Desconecte-se para se reconectar com você mesmo(a).");
        motivationalPhrases.add("A felicidade não é a ausência de problemas, mas a capacidade de lidar com eles.");
        motivationalPhrases.add("Invista em sua paz interior. Ela é o seu maior tesouro.");

        // NOVO: Inicializa o objeto Random
        random = new Random();

        // NOVO: Define o listener de clique para o botão 'idBtnFrases'
        idBtnFrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gera um índice aleatório dentro do tamanho da lista de frases
                int randomIndex = random.nextInt(motivationalPhrases.size());
                // Obtém a frase correspondente ao índice aleatório
                String randomPhrase = motivationalPhrases.get(randomIndex);
                // Define o texto do TextView 'idTextFrases' com a frase aleatória
                idTextFrases.setText(randomPhrase);
            }
        });

        // Configuração da Bottom Navigation View (existente no seu código)
        BottomNavigationView bottomNav = findViewById(R.id.idBottomNavFrases);
        bottomNav.setSelectedItemId(R.id.nav_article); // marca como selecionado

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
                    return true; // já está nesta tela
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
