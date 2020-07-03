package com.example.assignment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class BarChartActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 3434;
    MediaPlayer bgm,buttonClick;
    int panelHeight,panelWidth;
    String title = "Statistic Result";
    String w = "Win";
    String l = "Loss";
    int wColor = 0xFFCCEDFD;
    int lColor = 0xFFD9FFE3;
    SQLiteDatabase db;
    double wPercentageHeight,lPercentageHeight,win,lose,wPercentage,lPercentage;

    class Panel extends View {
        public Panel(Context context) {
            super(context);
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            panelHeight = h;
            panelWidth = w;
        }

        @Override
        public void onDraw(Canvas c) {
            super.onDraw(c);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);

            // Make the entire canvas in image
            Drawable d = getResources().getDrawable(R.drawable.barblur, null);
            d.setBounds(0, 0, panelWidth, panelHeight);
            d.draw(c);

            // Draw the x-axis and y-axis
            float x1 = (panelWidth / 2) + (panelWidth / 3);
            float x2 = (panelWidth / 2) - (panelWidth / 3);
            float y1 = (panelHeight / 2) + (panelHeight / 3);
            float y2 = (panelHeight / 2) - (panelHeight / 3);
            paint.setColor(Color.LTGRAY);
            paint.setStrokeWidth(10);
            paint.setShadowLayer(10,10,10,Color.DKGRAY);
            c.drawLine(x1, y1, x2, y1, paint);
            c.drawLine(x2, y1, x2, y2, paint);

            //set the axis name
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(60);
            paint.setTypeface(Typeface.SERIF);
            paint.setShadowLayer(10,10,10,Color.DKGRAY);
            c.drawText("Probability (%)",x2/4,y2-(y2/8),paint);

            //set the x-axis item
            float x3 = x1/3;
            float x4 = x1/4*3;
            paint.setColor(Color.WHITE);
            paint.setTextSize(60);
            paint.setShadowLayer(10,10,10,Color.DKGRAY);
            c.drawText(w,x3,y1+((panelHeight-y1)/5),paint);
            c.drawText(l,x4,y1+((panelHeight-y1)/5),paint);

            //draw the label
            initialDB();
            double total = win+lose;
            if(win==0&&lose==0) {
                lPercentage=0;
                wPercentage=0;
            }else {
                wPercentage = (win / total) ;
                lPercentage = (lose / total);
            }
            wPercentageHeight = (((y1-5)-y2)*wPercentage);
            lPercentageHeight = (((y1-5)-y2)*lPercentage);
            double wy = (y1-5)- wPercentageHeight;
            double ly = (y1-5)-lPercentageHeight;





            paint.setColor(wColor);
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, paint);
            paint.setShadowLayer(15,10,0,Color.DKGRAY);
            c.drawRect(x3-10,(float)wy,(x3+(x3-x2)),y1-5,paint);
            paint.setColor(lColor);
            c.drawRect(x4-10,(float)ly,(x4+(x3-x2)),y1-5,paint);

            //draw the title
            float pw = (float)(wy+((y1-5)-wy)/2);
            float pl = (float)(ly+((y1-5)-ly)/2);
            paint.setColor(Color.WHITE);
            paint.setTypeface(Typeface.SERIF);
            paint.setTextSize(60);
            paint.setShadowLayer(10,10,10,Color.DKGRAY);
            c.drawText((int)(wPercentage*100)+"%",x3-10,pw,paint);
            c.drawText((int)(lPercentage*100)+"%",x4-10,pl,paint);
            paint.setTextSize(100);
            c.drawText(title,panelWidth/4,panelHeight/20,paint);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Panel(this));

        bgm = MediaPlayer.create(BarChartActivity.this,R.raw.bgm);
        buttonClick = MediaPlayer.create(BarChartActivity.this,R.raw.buttonclick);
        bgm.start();
    }
    public void back(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
        buttonClick.start();
        bgm.release();
    }
    public void initialDB() {
        try {
            db = SQLiteDatabase.openDatabase("/data/data/com.example.assignment/gameLogDB", null, SQLiteDatabase.OPEN_READONLY);
            if (db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = 'GameLog'",null).getCount() <= 0) {
                db.execSQL("CREATE TABLE GameLog(" + "gameDate date ," + "gameTime time ," + "opponentName text," + "result text);");
            }
            Cursor cursor = (db.rawQuery("SELECT COUNT(*) FROM GameLog WHERE result = 'Win'", null));
            cursor.moveToFirst();
            win = (float)cursor.getInt(0);
            Cursor lcursor = (db.rawQuery("SELECT COUNT(*) FROM GameLog WHERE result = 'Lose'", null));
            lcursor.moveToFirst();
            lose = (float)lcursor.getInt(0);
        } catch (SQLiteException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
