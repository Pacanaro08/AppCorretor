package com.example.appcorretor;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

public class ImgData {

    private int idDono;
    private int idImagem;
    private String imagem;
    private Context context;
    private boolean excluir;
    private ArrayList<Integer> idImagens;
    private ArrayList<String> imagens;

    public ImgData(Context context){
        this.context = context;
    }

    public int getIdDono() {
        return idDono;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public int getIdImagem() {
        return idImagem;
    }

    public void setIdDono(int idDono) {
        this.idDono = idDono;
    }

    public ArrayList<Integer> getIdImagens(){return idImagens;}

    public void setIdImagens(ArrayList<Integer> idImagens) {this.idImagens = idImagens;}

    public ArrayList<String> getImagens() {return imagens;}

    public void setImagens(ArrayList<String> imagens) {this.imagens = imagens;}

    public boolean excluir(){
        DataBase dataBase = null;
        SQLiteDatabase sqLiteDatabase = null;
        try {
            dataBase = new DataBase(context);
            sqLiteDatabase = dataBase.getWritableDatabase();
            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.delete("imagens", "idImagem = ?", new String[]{String.valueOf(idImagem)});

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
//            if (idImagem == -1){
                sql = "INSERT INTO imagens (idDono, imagem) VALUES (?, ?)";
//            }else{
//                sql = "UPDATE imagens SET idDono = ?, imagem = ? WHERE idImagem = ?";
//            }
            sqLiteDatabase.beginTransaction();
            SQLiteStatement sqLiteStatement = sqLiteDatabase.compileStatement(sql);
            sqLiteStatement.clearBindings();
            sqLiteStatement.bindString(1, String.valueOf(idDono));
            sqLiteStatement.bindString(2, String.valueOf(imagem));
//            if (idImagem != -1){
//                sqLiteStatement.bindString(3, String.valueOf(idImagem));
//            }
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
//-------------------------------------------------------------

    public void carregaDados(int idDono) {
        DataBase dataBase = null;
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        ArrayList<Integer> idImagens = new ArrayList<Integer>();
        ArrayList<String> imagens = new ArrayList<String>();

        try {
            dataBase = new DataBase(context);
            sqLiteDatabase = dataBase.getReadableDatabase();
            cursor = sqLiteDatabase.query("imagens", null, "idDono = ?", new String[]{String.valueOf(idDono)}, null, null, "idDono");
            excluir = true;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    this.idDono = cursor.getInt(cursor.getColumnIndex("idDono"));
                    idImagem = cursor.getInt(cursor.getColumnIndex("idImagem"));
                    System.out.println("xj6 --- " + idImagem);
                    idImagens.add(idImagem);
                    System.out.println("cudiguin ---- " + idImagens);
                    imagem = cursor.getString(cursor.getColumnIndex("imagem"));
                    System.out.println("hornetao --- " + imagem);
                    imagens.add(imagem);
                    System.out.println("cudiguin2 ---- " + imagens);
                    excluir = false;
                }
            this.setImagens(imagens);
            this.setIdImagens(idImagens);
            }else{
                System.out.println("xj6");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ((cursor != null) && (!cursor.isClosed()))
                cursor.close();
            if (dataBase != null)
                dataBase.close();
            if (sqLiteDatabase != null)
                sqLiteDatabase.close();
        }
        //return String.valueOf(idDono); //"" ID IMAGEM = " + idImagem + " ID DONO = " + idDono + " " + txt + " IMAGEM = " + imagem;
    }
}
