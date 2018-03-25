package com.example.android.WiradhikaDwiPutra_1202151228_Modul5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;



public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_praktikum";

    private static final String TABLE_NAME = "tb_todo";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "desc";
    private static final String KEY_PRIORITY = "priority";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //membuat database
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_DESC + " TEXT, " +KEY_PRIORITY + " TEXT)";
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    //upgrade database
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

//fungsi ini sebagai cara untuk menuliskan kedalam databasenya
    public void save(ToDo todo){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME, todo.getName());
        values.put(KEY_DESC, todo.getDesc());
        values.put(KEY_PRIORITY, todo.getPriority());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
//mencari database
    public LinkedList<ToDo> findAll(){
        LinkedList<ToDo> listBuku = new LinkedList<ToDo>();
        String query="SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                ToDo todo = new ToDo();
                todo.setId(Integer.valueOf(cursor.getString(0)));
                todo.setName(cursor.getString(1));
                todo.setDesc(cursor.getString(2));
                todo.setPriority(cursor.getString(3));
                listBuku.add(todo);
            }while(cursor.moveToNext());
        }

        return listBuku;
    }
//menghapus database
    public boolean delete(ToDo todo){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id =" + todo.getId(), null) > 0;
    }
}
