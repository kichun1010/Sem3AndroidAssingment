package com.example.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class GameLogActivity extends AppCompatActivity {
    private String[] columns = {"gameDate", "gameTime", "opponentName", "result"};
    private TableLayout tbData;
    private Result result;
    SQLiteDatabase db;
    private static final int REQUEST_CODE = 3434;
    MediaPlayer bgm,buttonClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamelog);

        result = new com.example.assignment.Result(this,(TableLayout)findViewById(R.id.tbData));
        initialDB();
        bgm = MediaPlayer.create(GameLogActivity.this,R.raw.bgm);
        buttonClick = MediaPlayer.create(GameLogActivity.this,R.raw.buttonclick);
        bgm.start();
    }

    public void initialDB() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.assignment/gameLogDB", null, SQLiteDatabase.OPEN_READONLY);
            if (db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'GameLog'",null).getCount() <= 0) {
                db.execSQL("CREATE TABLE GameLog(" + "gameDate date ," + "gameTime time," + "opponentName text," + "result text);");
            }
            result.fillTable(db.rawQuery("SELECT * FROM GameLog", null));
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void back(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }

}
