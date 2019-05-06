package com.example.alison.autprime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomepageActivity extends AppCompatActivity{
    Button courseBtn, gradeBtn, mapBtn, noteBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        courseBtn = findViewById(R.id.courseBtnId);
        gradeBtn = findViewById(R.id.gradeBtnId);
        mapBtn = findViewById(R.id.mapBtnId);
        noteBtn = findViewById(R.id.noteBtnId);

        //retrieve data (ie:username) from login page and display it on the homepage
        Intent intent = getIntent();
        String text = intent.getStringExtra("Username");

        TextView viewText = findViewById(R.id.placeHolderUsername);
        viewText.setText(text);

        //redirecting functions for the buttons/features of the app
        courseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to redirect user to courses when button is clicked
                Toast.makeText(getApplicationContext(),"Redirecting to courses",Toast.LENGTH_SHORT).show();
            }
        });

        gradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to redirect user to grades when button is clicked
                Toast.makeText(getApplicationContext(),"Redirecting to grades",Toast.LENGTH_SHORT).show();
            }
        });

        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to redirect user to maps when button is clicked
                Toast.makeText(getApplicationContext(),"Redirecting to maps",Toast.LENGTH_SHORT).show();
            }
        });

        noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to redirect user to listNotes when button is clicked
                Toast.makeText(getApplicationContext(),"Redirecting to listNotes",Toast.LENGTH_SHORT).show();
                Intent noteIntent = new Intent(HomepageActivity.this, Notepad.class);
                startActivity(noteIntent);
            }
        });
    }
}