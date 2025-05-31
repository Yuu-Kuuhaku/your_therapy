package com.example.yourtherapy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourtherapy.utils.Constants;
import com.example.yourtherapy.utils.DatabaseHelper;

public class TelaInicio extends AppCompatActivity {
    private String[] sentimentos= {"Animado", "Feliz", "Normal", "Triste", "Ansioso", "Raiva/Estressado"};

    private Button btnSalvar;
    private ImageButton btnImage;
    private Spinner spinner;
    private TextView txtSentimento, txtBemVindo;
    private DatabaseHelper dbHelper;  // Instância do banco de dados
    private EditText editTextDesabafe;
    private int image_id = 0 ;

    private String userName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicio);



        // Iniciar o botão de voltar
        //voltar = findViewById(R.id.idBtnVoltar);
        // Iniciar o spinner ( pop-up com opções de sentimento/humor da pessoa no dia)
        spinner = findViewById(R.id.idSpinnerSentindo);
        // Iniciar ImageButton do sentimento/humor
        btnImage = findViewById(R.id.idBtnImgSentimento);
        // Iniciar TextView do sentimento/humor
        txtSentimento = findViewById(R.id.idTxtSentimento);
        // Iniciar o botão de salvar sentimento
        btnSalvar = findViewById(R.id.idBtnSalvarSentimento);
        // Iniciar o botão de salvar sentimento
        editTextDesabafe = findViewById(R.id.idEditTextDesabafe);

        txtBemVindo = findViewById(R.id.idTxtBemVindo);

        SharedPreferences preferences =  getSharedPreferences(Constants.ARQUIVO_PREFERENCIA, 0);

        if(preferences.contains(Constants.KEY_USUARIO)){

            userName = preferences.getString(Constants.KEY_USUARIO, "Sem nome");

            txtBemVindo.setText("Bem vindo, "+ userName);
        }

        dbHelper = new DatabaseHelper(this); // Inicializa o banco de dados

        // Inicializa o componente de navegação inferior (BottomNavigationView)
        // Esse é o menu que aparece na parte de baixo da tela com ícones para navegar entre as telas do app
        BottomNavigationView bottomNav = findViewById(R.id.idBottomNavHome);

        // Define que o item "home" estará selecionado como padrão ao abrir essa tela
        bottomNav.setSelectedItemId(R.id.nav_home);

        // Define o que deve acontecer quando o usuário clica em um dos itens do menu inferior
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    // Já estamos na tela "Home", então não é necessário fazer nada
                    return true;
                case R.id.nav_gym:
                    // Cria uma intenção (Intent) para abrir a tela de exercícios (TelaExercicios)
                    startActivity(new Intent(this, TelaExercicios.class));
                    // Remove a animação padrão de transição entre telas, tornando a troca mais suave
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_article:
                    // Cria uma intenção (Intent) para abrir a tela de frases (Frases)
                    startActivity(new Intent(this, Frases.class));
                    // Remove a animação padrão de transição entre telas, tornando a troca mais suave
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_historic:
                    // Cria uma intenção (Intent) para abrir a tela de histórico (Historico)
                    startActivity(new Intent(this, Historico.class));
                    // Remove a animação padrão de transição entre telas, tornando a troca mais suave
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.nav_sign_out:
                    // Quando o usuário escolhe sair (logout), os dados salvos são removidos

                    // Acessa o arquivo de preferências onde os dados do usuário estão armazenados
                    SharedPreferences.Editor editor = preferences.edit();

                    // Remove a informação do usuário salvo (usado para login automático, por exemplo)
                    editor.remove(Constants.KEY_USUARIO);
                    editor.commit();  // Salva as mudanças

                    // Cria uma intenção para voltar à tela de login (MainActivity)
                    Intent intent = new Intent(this, MainActivity.class);

                    // Define que essa nova tela será iniciada como uma nova tarefa,
                    // e limpa todas as telas anteriores do histórico (para o usuário não poder voltar com o botão "voltar")
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent); // Inicia a nova tela
                    finish(); // Encerra a tela atual
                    return true;
            }
            return false;
        });

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                sentimentos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sentimentoSelecionado = sentimentos[i];

                if(i == 0) btnImage.setImageResource(R.drawable.emoji);
                if(i == 1) btnImage.setImageResource(R.drawable.emoji_1);
                if(i == 2) btnImage.setImageResource(R.drawable.emoji_2);
                if(i == 3) btnImage.setImageResource(R.drawable.emoji_3);
                if(i == 4) btnImage.setImageResource(R.drawable.emoji_4);
                if(i == 5) btnImage.setImageResource(R.drawable.emoji_5);

                image_id = i;

                txtSentimento.setText(sentimentoSelecionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){
                Toast.makeText(TelaInicio.this, "Selecione um sentimento " , Toast.LENGTH_SHORT).show();
            }
        });

        // Ação ao clicar no botão "Salvar"
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = editTextDesabafe.getText().toString(); // Pega o texto digitado
                String title = txtSentimento.getText().toString();
                if (!texto.isEmpty() && !title.isEmpty() ) {
                    dbHelper.inserirSentimento(texto, title , image_id, userName); // Salva no banco
                    editTextDesabafe.setText("");        // Limpa o campo de texto
                    btnImage.setImageResource(R.drawable.emoji);
                    txtSentimento.setText("Animado");
                    image_id = 0;
                    Toast.makeText(TelaInicio.this, "Texto salvo!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TelaInicio.this, "Digite um texto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}