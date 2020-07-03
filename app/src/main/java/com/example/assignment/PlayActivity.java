package com.example.assignment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlayActivity extends AppCompatActivity {
    String[] columns = {"gameDate", "gameTime", "opponentName", "result"};
    SQLiteDatabase db;
    TextView playerName, opponentName, endMsg, pScore, oScore, oCountry, playerG, npcG;
    String oName, oLeft, oRight, oGuess, id, country;
    LinearLayout rightChoice, leftChoice, guess, result, information, computerLeftLayout, end;
    boolean visible;
    RelativeLayout playerLeft, playerRight, computerLeft, computerRight;
    ImageView playerRightPaper, playerLeftPaper, computerLeftPaper, computerRightPaper, playerRightStone, playerLeftStone, computerLeftStone, computerRightStone;
    private static final int REQUEST_CODE = 3434;
    MediaPlayer bgm, buttonClick,lose,win;
    int calculate, playerGuess, playerScore, npcScore, turn,npcLeft,npcRight,npcGuess;
//    npcLeft,npcRight,npcGuess
    int lhand, rhand;
    String logResult, time, date;
    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        buttonClick = MediaPlayer.create(PlayActivity.this, R.raw.buttonclick);
        win = MediaPlayer.create(PlayActivity.this,R.raw.win);
        lose = MediaPlayer.create(PlayActivity.this,R.raw.lose);
        new FetchPageTask1().execute();
        new FetchPageTask().execute();
        bgm = MediaPlayer.create(PlayActivity.this, R.raw.bgm);
        bgm.start();
        playerLeft = findViewById(R.id.playerLeft);
        playerRight = findViewById(R.id.playerRight);
        computerLeft = findViewById(R.id.computerLeft);
        computerRight = findViewById(R.id.computerRight);
        rightChoice = findViewById(R.id.rightChoice);
        leftChoice = findViewById(R.id.leftChoice);
        guess = findViewById(R.id.guess);
        playerRightPaper = findViewById(R.id.playerRightPaper);
        playerLeftPaper = findViewById(R.id.playerLeftPaper);
        computerLeftPaper = findViewById(R.id.computerLeftPaper);
        computerRightPaper = findViewById(R.id.computerRightPaper);
        playerRightStone = findViewById(R.id.playerRightStone);
        playerLeftStone = findViewById(R.id.playerLeftStone);
        computerLeftStone = findViewById(R.id.computerLeftStone);
        computerRightStone = findViewById(R.id.computerRightStone);
        playerName = findViewById(R.id.playerName);
        endMsg = findViewById(R.id.endMsg);
        pScore = findViewById(R.id.playerScore);
        oScore = findViewById(R.id.npcScore);
        oCountry = findViewById(R.id.npcCountry);
        playerG = findViewById(R.id.playerG);
        npcG = findViewById(R.id.npcG);
        result = findViewById(R.id.result);
        information = findViewById(R.id.information);
        computerLeftLayout = findViewById(R.id.computerLeftLayout);
        end = findViewById(R.id.end);

        Intent intent = getIntent();
        playerName.setText(intent.getStringExtra("name"));
        playerScore = 0;
        npcScore = 0;
        calculate = 0;
        pScore.setText("Score : " + playerScore);
        oScore.setText("Score : " + npcScore);
        turn = 0;
        lhand = 0;
        rhand = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd");
        Date currentDate = new Date(System.currentTimeMillis());
        date = dateFormat.format(currentDate);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(currentDate);

    }

    private class FetchPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... values) {
            InputStream inputStream = null;
            String result = "";
            URL url = null;
            String link = "https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/" + id;

            try {
                url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;
                inputStream.close();
                Log.i("result", "Complete");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create JSON object
            try {
                JSONObject jObj = new JSONObject(result);
                // Parse JSON array
                oName = jObj.getString("name");
                oRight = jObj.getString("right");
                oLeft = jObj.getString("left");
                oGuess = jObj.getString("guess");

                npcLeft = Integer.parseInt(oLeft);
                npcRight = Integer.parseInt(oRight);
                npcGuess = Integer.parseInt(oGuess);

            } catch (Exception e) {
            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            opponentName = (TextView) findViewById(R.id.opponentName);
            opponentName.setText(oName);
            oCountry.setText(country);
        }
    }

    private class FetchPageTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... values) {
            InputStream inputStream = null;
            String result = "";
            URL url = null;


            try {
                url = new URL("https://4qm49vppc3.execute-api.us-east-1.amazonaws.com/Prod/itp4501_api/opponent/0");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null)
                    result += line;
                inputStream.close();
                Log.i("result", "Complete");
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create JSON object
            try {
                JSONObject jObj = new JSONObject(result);
                // Parse JSON array
                id = jObj.getString("id");
                country = jObj.getString("country");

            } catch (Exception e) {
            }
            return result;
        }


        @Override
        protected void onPostExecute(String result) {

        }
    }


    public void right0(View view) {
        buttonClick.start();
        rhand = 0;
        TransitionManager.beginDelayedTransition(playerRight);
        showRightHand(0);
        rightChoice.setVisibility(View.GONE);
        calculate += 0;

        //take the hand of the opponent
        showNPChand(npcLeft, npcRight);

        if (turn == 1)
            npcG.setText("Guess Number : " + npcGuess);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (turn == 0)
                    playerTurn();
                else
                    npcTurn();
            }
        }, 3000);


        //check whether the guess number = calculate number if Yes play guess one more round else turn to opponent turn


    }


    public void right5(View view) {
        buttonClick.start();
        npcG.setText("Guess Number : " + npcGuess);
        rhand = 5;
        TransitionManager.beginDelayedTransition(playerRight);
        showRightHand(rhand);
        rightChoice.setVisibility(View.GONE);
        calculate += 5;


        //take the hand of the opponent
        showNPChand(npcLeft, npcRight);

        if (turn == 1)
            npcG.setText("Guess Number : " + npcGuess);

        //check whether the guess number = calculate number if Yes play guess one more round else turn to opponent turn
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (turn == 0)
                    playerTurn();
                else
                    npcTurn();
            }
        }, 3000);


    }

    public void left0(View view) {
        buttonClick.start();
        lhand = 0;
        TransitionManager.beginDelayedTransition(playerLeft);
        showLeftHand(lhand);
        leftChoice.setVisibility(View.GONE);
        rightChoice.setVisibility(View.VISIBLE);
        calculate += 0;

    }

    public void left5(View view) {
        buttonClick.start();
        lhand = 5;
        TransitionManager.beginDelayedTransition(playerLeft);
        showLeftHand(lhand);
        leftChoice.setVisibility(View.GONE);
        rightChoice.setVisibility(View.VISIBLE);
        calculate += 5;

    }

    //When the guess button press
    public void guess0(View view) {
        playerGuess = 0;
        TransitionManager.beginDelayedTransition(guess);
        leftChoice.setVisibility(View.VISIBLE);
//        rightChoice.setVisibility(visible?View.VISIBLE: View.GONE);
        hideGuess();
        buttonClick.start();
        calculate = 0;
    }

    public void guess5(View view) {
        playerGuess = 5;
        TransitionManager.beginDelayedTransition(guess);
        leftChoice.setVisibility(View.VISIBLE);
//        rightChoice.setVisibility(visible?View.VISIBLE: View.GONE);
        hideGuess();
        buttonClick.start();
        calculate = 0;
    }

    public void guess10(View view) {
        playerGuess = 10;
        TransitionManager.beginDelayedTransition(guess);
        leftChoice.setVisibility(View.VISIBLE);
//        rightChoice.setVisibility(visible?View.VISIBLE: View.GONE);
        hideGuess();
        buttonClick.start();
        calculate = 0;
    }

    public void guess15(View view) {
        playerGuess = 15;
        TransitionManager.beginDelayedTransition(guess);
        leftChoice.setVisibility(View.VISIBLE);
//        rightChoice.setVisibility(visible?View.VISIBLE: View.GONE);
        hideGuess();
        buttonClick.start();
        calculate = 0;
    }

    public void guess20(View view) {
        playerGuess = 20;
        TransitionManager.beginDelayedTransition(guess);
        leftChoice.setVisibility(View.VISIBLE);
//        rightChoice.setVisibility(visible?View.VISIBLE: View.GONE);
        hideGuess();
        buttonClick.start();
        calculate = 0;
    }

    public boolean endGame() {


        //When player score 2marks then he will win the game and ask whether play with another npc or back to the start page
        if (playerScore == 2) {
            win.start();
            hideAll();
            logResult = "Win";
            insertdata();
            //display the message you win
            endMsg.setText("You Win!!");
            animation();


            //hide all the item and show the end choice
        }
        //When NPC score 2marks then player will lose the game and ask whether play with the same npc or back to the start page
        else if (npcScore == 2) {
            lose.start();
            hideAll();
            logResult = "Lose";
            insertdata();
            //display the message you lose
            endMsg.setText("You Lose!!");
            animation();


            //hide all the item and show the end choice
        }
        if (npcScore == 2 || playerScore == 2)
            return true;
        else
            return false;
    }


    public void showGuess() {
        guess.setVisibility(View.VISIBLE);
    }

    public void hideGuess() {
        guess.setVisibility(View.GONE);
        if (turn == 0)
            playerG.setText("Guess Number : " + playerGuess);
    }

    public void showLeftChoice() {
        leftChoice.setVisibility(View.VISIBLE);
    }

    public void setTurn(int turn) {
        endGame();

        playerG.setText("Guess Number : ");
        npcG.setText("Guess Number : ");
        //player turn
        if (turn == 0) {
            showGuess();
            new FetchPageTask().execute();
        } else {
            showLeftChoice();
            new FetchPageTask().execute();
        }
        if (endGame())
            hideGuess();
    }

    public void showNPChand(int npcLeft, int npcRight) {
        if (npcLeft == 0) {
            TransitionManager.beginDelayedTransition(computerLeft);
            computerLeftStone.setVisibility(View.VISIBLE);
            calculate += 0;
        } else {
            TransitionManager.beginDelayedTransition(computerLeft);
            computerLeftPaper.setVisibility(View.VISIBLE);
            calculate += 5;
        }

        //take the right hand of the opponent
        if (npcRight == 0) {
            TransitionManager.beginDelayedTransition(computerRight);
            computerRightStone.setVisibility(View.VISIBLE);
            calculate += 0;
        } else {
            TransitionManager.beginDelayedTransition(computerRight);
            computerRightPaper.setVisibility(View.VISIBLE);
            calculate += 5;
        }
    }

    public void playerTurn() {
        if (playerGuess == calculate) {
            //set the calculate number back to 0
            calculate = 0;

            //player will get one score;
            playerScore++;
            pScore.setText("Score : " + playerScore);

            //keep player turn
            turn = 0;

            removeNPChand(npcLeft, npcRight);
            removePlayerHand(lhand, rhand);
            setTurn(turn);
        } else {
            //player score will be count again
            playerScore = 0;
            pScore.setText("Score : " + playerScore);

            //set the calculate number back to 0
            calculate = 0;

            //set the turn to npc turn
            turn = 1;

            removeNPChand(npcLeft, npcRight);
            removePlayerHand(lhand, rhand);
            setTurn(turn);
        }
    }

    public void npcTurn() {
        if (npcGuess == calculate) {
            //set the calculate number back to 0
            calculate = 0;

            //npc will get score
            npcScore++;
            oScore.setText("Score : " + npcScore);

            //keep npc turn
            turn = 1;

            removeNPChand(npcLeft, npcRight);
            removePlayerHand(lhand, rhand);
            setTurn(turn);
        } else {
            //set the calculate number back to 0
            calculate = 0;

            //set the npc score back to 0
            npcScore = 0;
            oScore.setText("Score : " + npcScore);

            //set the turn to player turn
            turn = 0;

            removeNPChand(npcLeft, npcRight);
            removePlayerHand(lhand, rhand);
            setTurn(turn);
        }
    }

    public void showLeftHand(int i) {
        if (i == 0)
            playerLeftStone.setVisibility(View.VISIBLE);
        else
            playerLeftPaper.setVisibility(View.VISIBLE);
    }

    public void showRightHand(int i) {
        if (i == 0)
            playerRightStone.setVisibility(View.VISIBLE);
        else
            playerRightPaper.setVisibility(View.VISIBLE);
    }

    public void removePlayerHand(int lhand, int rhand) {
        if (lhand == 0) {
            playerLeftStone.setVisibility(View.GONE);
        } else if (lhand == 5) {
            playerLeftPaper.setVisibility(View.GONE);
        }

        if (rhand == 0)
            playerRightStone.setVisibility(View.GONE);
        else if (rhand == 5)
            playerRightPaper.setVisibility(View.GONE);

    }

    public void removeNPChand(int npcLeft, int npcRight) {
        if (npcLeft == 0) {
            TransitionManager.beginDelayedTransition(computerLeft);
            computerLeftStone.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(computerLeft);
            computerLeftPaper.setVisibility(View.GONE);
        }

        //take the right hand of the opponent
        if (npcRight == 0) {
            TransitionManager.beginDelayedTransition(computerRight);
            computerRightStone.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(computerRight);
            computerRightPaper.setVisibility(View.GONE);
        }
    }

    public void con(View view) {
        buttonClick.start();
        bgm.release();
        Intent a = getIntent();
        finish();
        startActivity(a);


    }

    public void back(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }

    public void hideAll() {
        rightChoice.setVisibility(View.GONE);
        leftChoice.setVisibility(View.GONE);
        guess.setVisibility(View.GONE);
        result.setVisibility(View.GONE);
        information.setVisibility(View.GONE);
        computerLeftLayout.setVisibility(View.GONE);
        end.setVisibility(View.VISIBLE);
    }

    public void insertdata() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.assignment/gameLogDB", null, SQLiteDatabase.CREATE_IF_NECESSARY);
            if (db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'GameLog'",null).getCount() <= 0) {
                db.execSQL("CREATE TABLE GameLog(" + "gameDate date ," + "gameTime time PRIMARY KEY," + "opponentName text," + "result text);");
            }
//            db.execSQL("DROP TABLE IF EXISTS GameLog;");
            db.execSQL("INSERT INTO GameLog(gameDate,gameTime,opponentName,result) values ('"+date+"','"+time+"','"+oName+"','"+logResult+"')");
//            Toast.makeText(this, "Table GameLog is created and initialised.",
//                    Toast.LENGTH_SHORT).show();
        } catch (SQLiteException e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void animation(){
        ObjectAnimator animatorZoomX = ObjectAnimator.ofFloat(endMsg,"scaleX",1,2,1);
        ObjectAnimator animatorZoomY = ObjectAnimator.ofFloat(endMsg,"scaleY",1,2,1);
        animatorZoomX.setDuration(2000);
        animatorZoomY.setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorZoomX,animatorZoomY);
        animatorSet.start();
    }
}