package com.example.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class MyDbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "word_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_WORD = "users";
    private static final String TABLE_DEFINITION = "users_Definition";
    private static final String TABLE_SYN = "users_Syn";
    private static final String KEY_ID = "id";
    private static final String KEY_WORD = "word";
    private static final String KEY_DEFINITION = "Definition";
    private static final String KEY_SYN = "Syn";

    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, phone_number TEXT......);*/

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE "
            + TABLE_WORD + "(" + KEY_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_WORD + " TEXT );";

    private static final String CREATE_TABLE_USER_Definition = "CREATE TABLE "
            + TABLE_DEFINITION + "(" + KEY_ID + " INTEGER,"+ KEY_DEFINITION + " TEXT );";

    private static final String CREATE_TABLE_USER_Syn = "CREATE TABLE "
            + TABLE_SYN + "(" + KEY_ID + " INTEGER,"+ KEY_SYN + " TEXT );";

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_USER_Definition);
        db.execSQL(CREATE_TABLE_USER_Syn);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_WORD + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DEFINITION + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SYN + "'");
        onCreate(db);
    }

    public void addWord(String word, String Definition, String Syn) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);
        //db.insert(TABLE_WORD, null, values);
        long id = db.insertWithOnConflict(TABLE_WORD, null, values, SQLiteDatabase.CONFLICT_IGNORE);

       
        ContentValues valuesDefinition = new ContentValues();
        valuesDefinition.put(KEY_ID, id);
        valuesDefinition.put(KEY_DEFINITION, Definition);
        db.insert(TABLE_DEFINITION, null, valuesDefinition);

        
        ContentValues valuesSyn = new ContentValues();
        valuesSyn.put(KEY_ID, id);
        valuesSyn.put(KEY_SYN, Syn);
        db.insert(TABLE_SYN, null, valuesSyn);
    }

    public ArrayList<WordModel> getAllWords() {
        ArrayList<WordModel> wordModelArrayList = new ArrayList<WordModel>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORD;
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor c = db.rawQuery(selectQuery, null);
        
        
        if (c.moveToFirst()) {
            do {
                WordModel wordModel = new WordModel();
                wordModel.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                wordModel.setWord(c.getString(c.getColumnIndex(KEY_WORD)));

                
                String selectDefinitionQuery = "SELECT  * FROM " + TABLE_DEFINITION +" WHERE "+KEY_ID+" = "+ wordModel.getId();
                Log.d("aaaaa",selectDefinitionQuery);
                
                //SQLiteDatabase dbDefinition = this.getReadableDatabase();
                Cursor cDefinition = db.rawQuery(selectDefinitionQuery, null);

                if (cDefinition.moveToFirst()) {
                    do {
                        wordModel.setdefinition(cDefinition.getString(cDefinition.getColumnIndex(KEY_DEFINITION)));
                    } while (cDefinition.moveToNext());
                }

                
                String selectSynQuery = "SELECT  * FROM " + TABLE_SYN+" WHERE "+KEY_ID+" = "+ wordModel.getId();;
                //SQLiteDatabase dbSyn = this.getReadableDatabase();
                Cursor cSyn = db.rawQuery(selectSynQuery, null);

                if (cSyn.moveToFirst()) {
                    do {
                        wordModel.setSyns(cSyn.getString(cSyn.getColumnIndex(KEY_SYN)));
                    } while (cSyn.moveToNext());
                }


                wordModelArrayList.add(wordModel);
            } while (c.moveToNext());
        }
        return wordModelArrayList;
    }

    public void updateWord(int id, String word, String Definition, String Syn) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(KEY_WORD, word);
        db.update(TABLE_WORD, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});


        ContentValues valuesDefinition = new ContentValues();
        valuesDefinition.put(KEY_DEFINITION, Definition);
        db.update(TABLE_DEFINITION, valuesDefinition, KEY_ID + " = ?", new String[]{String.valueOf(id)});


        ContentValues valuesSyn = new ContentValues();
        valuesSyn.put(KEY_SYN, Syn);
        db.update(TABLE_SYN, valuesSyn, KEY_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public void deleteWord(int id) {


        SQLiteDatabase db = this.getWritableDatabase();


        db.delete(TABLE_WORD, KEY_ID + " = ?",new String[]{String.valueOf(id)});


        db.delete(TABLE_DEFINITION, KEY_ID + " = ?", new String[]{String.valueOf(id)});


        db.delete(TABLE_SYN, KEY_ID + " = ?",new String[]{String.valueOf(id)});
    }

}