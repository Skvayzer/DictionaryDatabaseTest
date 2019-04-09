package com.example.databasetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WordsMainActivity extends AppCompatActivity {
    private Button btnStore, btnGetall;
    private EditText etword, etdefinition, etsyns;
    private MyDbHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_main);
        databaseHelper = new MyDbHelper(this);

        btnStore = (Button) findViewById(R.id.btnstore);
        btnGetall = (Button) findViewById(R.id.btnget);
        etword = (EditText) findViewById(R.id.etword);
        etdefinition = (EditText) findViewById(R.id.etdefinition);
        etsyns = (EditText) findViewById(R.id.etsyns);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.addWord(etword.getText().toString(), etdefinition.getText().toString(), etsyns.getText().toString());
                etword.setText("");
                etdefinition.setText("");
                etsyns.setText("");
                Toast.makeText(WordsMainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnGetall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsMainActivity.this, GetAllWordsActivity.class);
                startActivity(intent);
            }
        });

    }
}
