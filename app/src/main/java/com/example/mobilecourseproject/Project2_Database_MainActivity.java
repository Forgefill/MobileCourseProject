package com.example.mobilecourseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Models.Chicken;

public class Project2_Database_MainActivity extends AppCompatActivity {

    private RecyclerView chickensRecView;
    private EditText chickenBreed, chickenProduction;
    private Spinner chickenType;
    private TextView txtAverageProduction;
    private Button btn_add, btn_viewMore300, btn_average, btn_viewAll;
    ChickensRecViewAdapter chickensRecViewAdapter;
    DBHelper databaseHelper;
    String[] types = { "Egg", "Egg-Meat", "Meat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project2_database_main);

        initViews();

        chickensRecViewAdapter = new ChickensRecViewAdapter(this);
        chickensRecView.setAdapter(chickensRecViewAdapter);
        chickensRecView.setLayoutManager(new GridLayoutManager(this, 1));

        setChickensRecViewAdapter(databaseHelper.getAll());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Chicken model;
                String breed;
                String type;
                int production;
                if(!isEmptyInput())
                {
                    breed = chickenBreed.getText().toString();
                    type = chickenType.getSelectedItem().toString();
                    production = Integer.parseInt(chickenProduction.getText().toString());
                    model = new Chicken(0, breed, type, production );
                }
                else
                {
                    //model = new Chicken(-1, "Error_Breed", "Error_type", -1 );
                    Toast.makeText(Project2_Database_MainActivity.this, "Error_Cast", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = databaseHelper.addOne(model);

                setChickensRecViewAdapter(databaseHelper.getAll());
            }
        });

        btn_viewMore300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Chicken> all = databaseHelper.getMoreThan300();

                setChickensRecViewAdapter(all);
                Toast.makeText(Project2_Database_MainActivity.this, "View chickens that have more than 300 production", Toast.LENGTH_SHORT).show();
            }
        });

        btn_average.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtAverageProduction.setText(Integer.toString(databaseHelper.getAverageProduction()));
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChickensRecViewAdapter(databaseHelper.getAll());
            }
        });

    }


    private void initViews()
    {
        txtAverageProduction = findViewById(R.id.txtAverageProduction);
        btn_add = findViewById(R.id.btn_add);
        btn_average = findViewById(R.id.btn_average);
        btn_viewMore300 = findViewById(R.id.btn_viewMore300);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        chickensRecView = findViewById(R.id.chickensRecView);
        chickenBreed = findViewById(R.id.editChickenBreed);
        chickenProduction = findViewById(R.id.editChickenProduction);

        chickenType = findViewById(R.id.editChickenType);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chickenType.setAdapter(typeAdapter);

        databaseHelper = new DBHelper(Project2_Database_MainActivity.this);
    }

    public void setChickensRecViewAdapter(ArrayList<Chicken> toSet)
    {
        chickensRecViewAdapter.setChickens(toSet);
    }

    private boolean isEmptyInput()
    {
        if(TextUtils.isEmpty(chickenBreed.getText()))
        {
            chickenBreed.setError("Item can not be empty");
            return true;
        }
        else if(TextUtils.isEmpty(chickenProduction.getText().toString()))
        {
            chickenProduction.setError("Item can not be empty");
            return true;
        }


        return false;
    }
}