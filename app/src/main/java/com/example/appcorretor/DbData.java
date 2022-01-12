package com.example.appcorretor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class DbData {

    private int idDono;
    private String nome;
    private String telefone;
    private String endereco;
    private String valor;
    private Context context;
    private boolean excluir;

    public DbData(Context context){
        this.context = context;
        idDono = -1;
    }
//--------------------------------------------------------
// Getter e Setter

    public int getIdDono() {
        return idDono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean excluir(){
        DataBase dataBase = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            dataBase = new DataBase(context);
            sqLiteDatabase = dataBase.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("cadastro", "idDono = ?", new String[]{String.valueOf(idDono)});

            excluir = true;

            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (dataBase != null)
                dataBase.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
        }
    }

    public boolean salvar(){
        DataBase dataBase = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            dataBase = new DataBase(context);
            sqLiteDatabase = dataBase.getWritableDatabase();
            String sql = "";
            if (idDono == -1){
                sql = "INSERT INTO cadastro (nomeDono, telefoneDono, enderecoCasa, valorCasa) VALUES (?, ?, ?, ?)";
            }else{
                sql = "UPDATE cadastro SET nomeDono = ?, telefoneDono = ?, enderecoCasa = ?, valorCasa = ? WHERE idDono = ?";
            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1, nome);
            sqLiteStatement.bindString(2, telefone);
            sqLiteStatement.bindString(3, endereco);
            sqLiteStatement.bindString(4, valor);
            if (idDono != -1){
                sqLiteStatement.bindString(5, String.valueOf(idDono));
            }
            sqLiteStatement.executeInsert();
            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            sqLiteDatabase.endTransaction();
            return false;
        }finally {
            if (dataBase != null)
                dataBase.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
        }
    }

    public ArrayList<DbData> getDbData(){
       DataBase dataBase = null;
       SQLiteDatabase sqLiteDatabase = null;
       Cursor cursor = null;
       ArrayList<DbData> dbData = new ArrayList<>();
       try {
           dataBase = new DataBase(context);
           sqLiteDatabase = dataBase.getReadableDatabase();
           cursor = sqLiteDatabase.query("cadastro", null, null, null, null, null, null);
           while(cursor.moveToNext()){
               DbData dbData1 = new DbData(context);
               dbData1.idDono = cursor.getInt(cursor.getColumnIndex("idDono"));
               dbData1.nome = cursor.getString(cursor.getColumnIndex("nomeDono"));
               dbData1.telefone = cursor.getString(cursor.getColumnIndex("telefoneDono"));
               dbData1.endereco = cursor.getString(cursor.getColumnIndex("enderecoCasa"));
               dbData1.valor = cursor.getString(cursor.getColumnIndex("valorCasa"));
               dbData.add(dbData1);
           }
       }catch (Exception e){
           e.printStackTrace();
       }finally {
            if((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if(dataBase != null)
                dataBase.close();
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
            }
       return dbData;
    }
//-----------------------------------------------------------

    public void carregaDados(int idDono){
        DataBase dataBase = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        //dbHelper é DataBase
        //DbData é usuario

        try {
            dataBase = new DataBase(context);
            sqLiteDatabase = dataBase.getReadableDatabase();
            cursor = sqLiteDatabase.query("cadastro", null, "idDono = ?", new String[]{String.valueOf(idDono)},null,null,null);
            excluir = true;
            while(cursor.moveToNext()){
                this.idDono = cursor.getInt(cursor.getColumnIndex("idDono"));
                nome = cursor.getString(cursor.getColumnIndex("nomeDono"));
                telefone = cursor.getString(cursor.getColumnIndex("telefoneDono"));
                endereco = cursor.getString(cursor.getColumnIndex("enderecoCasa"));
                valor = cursor.getString(cursor.getColumnIndex("valorCasa"));
                excluir = false;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if(dataBase != null)
                dataBase.close();
            if(sqLiteDatabase != null)
                sqLiteDatabase.close();
        }
    }
}
