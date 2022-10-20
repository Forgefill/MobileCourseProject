package com.example.mobilecourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView project1, project2, info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        project1 = findViewById(R.id.project1CardView);
        project2 = findViewById(R.id.project2CardView);
        info = findViewById(R.id.infoCardView);


        project1.setOnClickListener(this);
        project2.setOnClickListener(this);
        info.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mymenu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    public void openProject1Activity()
    {
        Intent intent = new Intent(this, Project1_MainActivity.class);
        startActivity(intent);
    }

    public void openInfoActivity()
    {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.project1CardView:
                openProject1Activity();
                break;
            case R.id.project2CardView:
                break;
            case R.id.infoCardView:
                openInfoActivity();
                break;
            default:
                break;
        }
    }
}