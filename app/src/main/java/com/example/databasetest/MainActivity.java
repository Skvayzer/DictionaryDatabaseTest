package com.example.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView tv;
    Button add,read;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        tv=(TextView)findViewById(R.id.tv);
//        read=(Button)findViewById(R.id.read);


        dbHelper=new DbHelper(this);
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(DbHelper.KEY_WORD,"time");
        values.put(DbHelper.KEY_MEANING,"is an illusion that helps things make sense");
        db.insert(DbHelper.TABLE_DICT,null,values);


    }


    public void onClick(View v) {

        Cursor cursor=db.query(DbHelper.TABLE_DICT,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            int idindex=cursor.getColumnIndex(DbHelper.KEY_ID);
            int wordindex=cursor.getColumnIndex(DbHelper.KEY_WORD);
            int meanindex=cursor.getColumnIndex(DbHelper.KEY_MEANING);
            tv.setText(cursor.getInt(idindex) + "  " +cursor.getString(wordindex)+"   "+ cursor.getString(meanindex));
        }
        cursor.close();


    }

}
