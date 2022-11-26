package com.example.mobilecourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView project1, project2Database,project2Contact, info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        project1 = findViewById(R.id.project1CardView);
        project2Database = findViewById(R.id.project2DatabaseCardView);
        project2Contact = findViewById(R.id.project2ContactCardView);
        info = findViewById(R.id.infoCardView);


        project1.setOnClickListener(this);
        project2Database.setOnClickListener(this);
        project2Contact.setOnClickListener(this);
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

    public void openProject2DatabaseActivity()
    {
        Intent intent = new Intent(this, Project2_Database_MainActivity.class);
        startActivity(intent);
    }

    public void openInfoActivity()
    {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void openProject2ContactsActivity()
    {
        Intent intent = new Intent(this, Project2_Contacts_MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.project1CardView:
                openProject1Activity();
                break;
            case R.id.project2DatabaseCardView:
                openProject2DatabaseActivity();
                break;
            case R.id.project2ContactCardView:
                openProject2ContactsActivity();
                break;
            case R.id.infoCardView:
                openInfoActivity();
                break;
            default:
                break;
        }
    }
}