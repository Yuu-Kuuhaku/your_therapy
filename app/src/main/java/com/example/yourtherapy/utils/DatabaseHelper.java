package com.example.yourtherapy.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper  extends SQLiteOpenHelper {

    // Nome e versão do banco
    private static final String DATABASE_NAME = "yourTherapy.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    public static final String TABLE_NAME = "sentimentos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXTO = "texto";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_IMAGE_ID = "image_id";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_CREATED_AT = "created_at";



    // Construtor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Cria a tabela quando o banco é inicializado
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TEXTO + " TEXT,"
                + COLUMN_IMAGE_ID + " INTEGER,"
                + COLUMN_TITLE + " TEXT ,"
                + COLUMN_USER + " TEXT ,"
                + COLUMN_CREATED_AT + " TEXT DEFAULT (datetime('now', 'localtime'))"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Atualiza a tabela em caso de nova versão do banco
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insere um texto no banco
    public void inserirSentimento(String texto, String title, Integer imageId, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEXTO, texto);
        values.put(COLUMN_IMAGE_ID, imageId);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_USER, userName);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    // Retorna todos os textos do banco
    public List<MoodEntry> obterTodosSentimentosPorUsuario(String userNameFilter) {
        List<MoodEntry> listaSentimentos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_IMAGE_ID, COLUMN_TITLE, COLUMN_TEXTO, COLUMN_CREATED_AT, COLUMN_USER },
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int imageId = cursor.getInt(0);  // tipo_humor
                String titulo = cursor.getString(1);      // titulo
                String texto = cursor.getString(2);       // texto
                String created_at = cursor.getString(3);   // data e hora que foi salvo
                String userName = cursor.getString(4);   // data e hora que foi salvo
                String dataFormatada = "";
                try {
                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat formatoSaida = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
                    Date data = formatoEntrada.parse(created_at);
                    dataFormatada = formatoSaida.format(data);

                } catch (ParseException e) {
                    dataFormatada = "Data inválida";
                }

                MoodEntry entry = new MoodEntry(imageId, titulo, texto, dataFormatada, userName);

                if(entry.getUser().equals(userNameFilter)) {
                    listaSentimentos.add(entry);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaSentimentos;
    }
}