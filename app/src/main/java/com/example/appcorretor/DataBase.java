package com.example.appcorretor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    private static int VERSAO_BANCO = 1;
    private static String BANCO_CORRETOR = "AppCorretor";

    public DataBase(Context context) {
        super(context, BANCO_CORRETOR, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE [cadastro] (\n" +
            "[idDono] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "[nomeDono] VARCHAR(80)  NOT NULL,\n" +
            "[telefoneDono] VARCHAR(11)  NOT NULL,\n" +
            "[enderecoCasa] VARCHAR(80)  NOT NULL,\n" +
            "[valorCasa] VARCHAR(20)  NOT NULL\n" +
            ")"
        );
        db.execSQL(
            "CREATE TABLE [imagens] (\n"+
            "[idImagem] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
            "[idDono] INTEGER NOT NULL, \n" +
            "[imagem] VARCHAR, \n" +
            "FOREIGN KEY ([idDono]) \n" +
            "REFERENCES [cadastro] ([idDono]) \n" +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(
                "DELETE FROM [imagens]"
        );
        db.execSQL(
                "DELETE FROM [cadastro]"
        );
    }
}
