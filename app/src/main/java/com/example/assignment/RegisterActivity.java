package com.example.assignment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 3434;
    private EditText etFname,etLname,etDob,etPhone,etEmail;
    private CheckBox cbRead;
    MediaPlayer bgm,buttonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFname = findViewById(R.id.etFname);
        etLname = findViewById(R.id.etLname);
        etDob = findViewById(R.id.etDob);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        cbRead = findViewById(R.id.cbRead);

        SharedPreferences settings = getSharedPreferences("assignment", 0);
        boolean save = settings.getBoolean("save",false);
        cbRead.setChecked(save);
        if(cbRead.isChecked()==true) {
            etFname.setText(settings.getString("fname", ""));
            etEmail.setText(settings.getString("email",""));
            etPhone.setText(settings.getString("phone",""));
            etDob.setText(settings.getString("dob",""));
            etLname.setText(settings.getString("lname",""));

            bgm = MediaPlayer.create(RegisterActivity.this,R.raw.bgm);
            bgm.start();
            buttonClick = buttonClick = MediaPlayer.create(RegisterActivity.this,R.raw.buttonclick);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences settings = getSharedPreferences("assignment", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("save", cbRead.isChecked());
        editor.putString("fname",etFname.getText().toString());
        editor.putString("lname",etLname.getText().toString());
        editor.putString("dob",etDob.getText().toString());
        editor.putString("phone",etPhone.getText().toString());
        editor.putString("email",etEmail.getText().toString());
        editor.commit();

    }

    public void check(View view){
        SharedPreferences settings = getSharedPreferences("assignment", 0);
        if(cbRead.isChecked()==true) {
            etFname.setText(settings.getString("fname", ""));
            etEmail.setText(settings.getString("email",""));
            etPhone.setText(settings.getString("phone",""));
            etDob.setText(settings.getString("dob",""));
            etLname.setText(settings.getString("lname",""));
        }
        else{etFname.setText("");
            etEmail.setText("");
            etPhone.setText("");
            etDob.setText("");
            etLname.setText("");
        }

    }

    public void back(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
//        buttonClick.start();
//        bgm.release();
    }

    public void submit(View view){
        Intent d = new Intent(this, PlayActivity.class);
        d.putExtra("name", etFname.getText().toString()+" "+etLname.getText().toString());
        startActivityForResult(d, REQUEST_CODE);
//        buttonClick.start();
//        bgm.release();
//        String filename = "";
//        File myfile =  new File(filename);
//        if (cbRead.isChecked()) {
//            filename = "AccountDetail.txt";
//            try{
//                File myFile = new File(filename);
//                FileOutputStream fOut = new FileOutputStream(myFile);
//                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
//                myOutWriter.append(etFname.getText().toString());
//                myOutWriter.append(etLname.getText().toString());
//                myOutWriter.append(etDob.getText().toString());
//                myOutWriter.append(etPhone.getText().toString());
//                myOutWriter.append(etEmail.getText().toString());
//                myOutWriter.close();
//                fOut.close();
//            }catch (Exception e){
//                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
//            }
//        } else {
//
//            filename = "AccountDetail.txt";
//
//            try {
//                OutputStreamWriter out = new OutputStreamWriter(openFileOutput("AccountDetail.txt", 0));
//                out.write(etFname.getText().toString());
//                out.write(etLname.getText().toString());
//                out.write(etDob.getText().toString());
//                out.write(etPhone.getText().toString());
//                out.write(etEmail.getText().toString());
//                out.close();
//            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG);
//            }
//        }
    }
}
