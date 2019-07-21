package com.example.parcial1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context,
                        String name,
                        SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    public void queryData (String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //insertar data
    public void insertData(String nombre, String apellido, String numero){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="INSERT INTO RECORD VALUES (NULL,?,?,?)";

        SQLiteStatement stm = database.compileStatement(sql);
        stm.clearBindings();
        stm.bindString(1, nombre);
        stm.bindString(2, apellido);
        stm.bindString(3, numero);

        stm.executeInsert();
    }

    //editar data
    public void updateData(String nombre, String apellido, String numero, int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="UPDATE RECORD SET nombre=?, apellido=?, numero=? WHERE id=?";

        SQLiteStatement stm = database.compileStatement(sql);
        stm.clearBindings();
        stm.bindString(1, nombre);
        stm.bindString(2, apellido);
        stm.bindString(3, numero);
        stm.bindDouble(4,(double)id);

        stm.execute();
        stm.close();
    }

    //eliminar data
    public void delateData(int id){
        SQLiteDatabase database = getWritableDatabase();
        String sql ="DELETE FROM RECORD WHERE id=?";

        SQLiteStatement stm = database.compileStatement(sql);
        stm.clearBindings();
        stm.bindDouble(1,(double)id);

        stm.execute();
        stm.close();
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
