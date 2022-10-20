package com.example.mobilecourseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Project1_MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String FIRST_LINE_COEFFICIENTS = "com.example.mobilecourseproject.FIRST_LINE_COEFFICIENTS";
    public static final String SECOND_LINE_COEFFICIENTS = "com.example.mobilecourseproject.SECOND_LINE_COEFFICIENTS";

    RadioGroup systemSize;
    private int n;

    ArrayList<EditText> systemEditTexts;
    Button saveBtn, loadBtn, calcBtn, graphBtn;
    TextView txtMatrix, txtGauss, txtVector;
    EditText editFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project1_main);

        systemEditTexts = new ArrayList<>();
        systemSize = findViewById(R.id.rgSystemSize);
        saveBtn = findViewById(R.id.saveBtn);
        loadBtn = findViewById(R.id.loadBtn);
        calcBtn = findViewById(R.id.calcBtn);
        graphBtn = findViewById(R.id.showGraphics);
        txtMatrix = findViewById(R.id.txtMatrix);
        txtGauss = findViewById(R.id.txtGauss);
        txtVector = findViewById(R.id.txtVector);
        editFileName = findViewById(R.id.editFileName);

        saveBtn.setOnClickListener(this);
        loadBtn.setOnClickListener(this);
        calcBtn.setOnClickListener(this);
        graphBtn.setOnClickListener(this);

        systemSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbTwoVarSystem:
                        findViewById(R.id.inputInclude3).setVisibility(View.GONE);
                        findViewById(R.id.inputInclude2).setVisibility(View.VISIBLE);
                        findViewById(R.id.showGraphics).setVisibility(View.VISIBLE);
                        txtGauss.setText("");
                        txtMatrix.setText("");
                        txtVector.setText("");
                        n = 2;
                        initListEditText();
                        break;
                    case R.id.rbThreeVarSystem:
                        findViewById(R.id.inputInclude2).setVisibility(View.GONE);
                        findViewById(R.id.showGraphics).setVisibility(View.GONE);
                        findViewById(R.id.inputInclude3).setVisibility(View.VISIBLE);
                        txtGauss.setText("");
                        txtMatrix.setText("");
                        txtVector.setText("");
                        n = 3;
                        initListEditText();
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.mymenu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.home:
                openHomeActivity();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    public void openHomeActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.saveBtn:
                if(validateEditTexts())
                {
                    String filename = editFileName.getText().toString();

                    if(filename.isEmpty())
                    {
                        editFileName.setError("Choose file to save/load linear system coefficient");
                        break;
                    }

                    saveCoeffInternalStorage(filename, getEditMatrixA(), getEditVectorB());

                    Toast.makeText(this, "Coefficient saved to file " + filename, Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.loadBtn:
                    String filename = editFileName.getText().toString();
                    if(filename.isEmpty())
                    {
                        editFileName.setError("Choose file to save/load linear system coefficient");
                        break;
                    }

                    ArrayList<ArrayList<Double>> A = loadCoeffInternalStorage(filename);
                    ArrayList<Double> b = A.get(n);
                    A.remove(n);
                    switch (n)
                    {
                        case 2:
                            systemSize.check(R.id.rbTwoVarSystem);
                            break;
                        case 3:
                            systemSize.check(R.id.rbThreeVarSystem);
                            break;
                    }
                    setEditTexts(A, b);

                    Toast.makeText(this, "Coefficient downloaded from file: " + filename, Toast.LENGTH_SHORT).show();
                break;

            case R.id.calcBtn:
                if(validateEditTexts()) {

                    long startTime = System.nanoTime();
                    ArrayList<Double> gaus = MatrixMath.gaussianElimination(getEditMatrixA(), getEditVectorB());
                    long endTime = System.nanoTime();

                    txtGauss.setText("Gauss Time: " + (endTime - startTime) + " ns.");

                    startTime = System.nanoTime();
                    gaus = MatrixMath.gaussianElimination(getEditMatrixA(), getEditVectorB());
                    endTime = System.nanoTime();

                    txtMatrix.setText("Matrix Time: " + (endTime - startTime) + " ns.");

                    String resultCalc = "";
                    for (int i = 0; i < n; i++) {
                        resultCalc += "x" + i + ": " + gaus.get(i) + "\n";
                    }

                    txtVector.setText(resultCalc);
                }
                break;
            case R.id.showGraphics:
                if(validateEditTexts())
                {
                    openDrawActivity(getEditMatrixA(), getEditVectorB());
                }
                break;
        }

    }


    public void openDrawActivity(ArrayList<ArrayList<Double>> A, ArrayList<Double> b)
    {
        Intent intent = new Intent(this, Project1_DrawActivity.class);

        A.get(0).add(b.get(0));
        A.get(1).add(b.get(1));
        intent.putExtra(FIRST_LINE_COEFFICIENTS, A.get(0));
        intent.putExtra(SECOND_LINE_COEFFICIENTS, A.get(1));
        startActivity(intent);
    }


    private ArrayList<ArrayList<Double>> getEditMatrixA()
    {
        ArrayList<ArrayList<Double>> res = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            res.add(new ArrayList<>());
        }

        int index = 0;
        for(int i = 0; i < n*n; i++ )
        {
            index = i/n;
            res.get(index).add(Double.parseDouble(systemEditTexts.get(i).getText().toString()));
        }

        return res;
    }

    private ArrayList<Double> getEditVectorB()
    {
        ArrayList<Double> res = new ArrayList<>();

        for(int i = n*n; i < n*n + n; i++ ) {
            res.add(Double.parseDouble(systemEditTexts.get(i).getText().toString()));
        }
        return res;
    }

    private void saveCoeffInternalStorage(String filename, ArrayList<ArrayList<Double>> A, ArrayList<Double> b)
    {
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            String toWrite = n + "\n";
            outputStream.write(toWrite.getBytes());

            for(int i = 0; i < A.size(); i++)
            {
                for(int j = 0; j < A.size(); j++)
                {
                    toWrite = A.get(i).get(j).toString() + "\n";
                    outputStream.write(toWrite.getBytes());
                }
            }

            for(int i = 0; i < b.size(); i++)
            {
                toWrite = b.get(i).toString() + "\n";
                outputStream.write(toWrite.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ArrayList<ArrayList<Double>> loadCoeffInternalStorage(String filename)
    {
        FileInputStream inputStream;
        ArrayList<ArrayList<Double>> res = new ArrayList<>();

        try {
            inputStream = this.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);

            int n = Integer.parseInt(reader.readLine());
            this.n = n;

            for(int i = 0; i < n+1; i++)
            {
                res.add(new ArrayList<>());
            }

            for(int i = 0; i < n*(n+1); i++)
            {
                res.get(i/n).add(Double.parseDouble(reader.readLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    private void initListEditText()
    {
        systemEditTexts.clear();
        if(n == 2)
        {
            systemEditTexts.add(findViewById(R.id.a11two));
            systemEditTexts.add(findViewById(R.id.a12two));
            systemEditTexts.add(findViewById(R.id.a21two));
            systemEditTexts.add(findViewById(R.id.a22two));
            systemEditTexts.add(findViewById(R.id.b1two));
            systemEditTexts.add(findViewById(R.id.b2two));
        }
        else if(n == 3)
        {
            systemEditTexts.add(findViewById(R.id.a11));
            systemEditTexts.add(findViewById(R.id.a12));
            systemEditTexts.add(findViewById(R.id.a13));
            systemEditTexts.add(findViewById(R.id.a21));
            systemEditTexts.add(findViewById(R.id.a22));
            systemEditTexts.add(findViewById(R.id.a23));
            systemEditTexts.add(findViewById(R.id.a31));
            systemEditTexts.add(findViewById(R.id.a32));
            systemEditTexts.add(findViewById(R.id.a33));
            systemEditTexts.add(findViewById(R.id.b1));
            systemEditTexts.add(findViewById(R.id.b2));
            systemEditTexts.add(findViewById(R.id.b3));
        }
    }

    private boolean validateEditTexts()
    {
        boolean res = true;
        for(EditText edit : systemEditTexts){
            if(TextUtils.isEmpty(edit.getText()))
            {
                edit.setError("Item can not be empty");
                res = false;
            }
            else if(!edit.getText().toString().matches("[-]?\\d+(?:\\.\\d+)?"))
            {
                edit.setError("Input only integer or real number");
                res = false;
            }
        }

        if(!res) return res;

        if(MatrixMath.isSingular(getEditMatrixA()))
        {
            systemEditTexts.get(0).setError("Your matrix is singular");
            res = false;
        }

        return res;
    }

    private void setEditTexts(ArrayList<ArrayList<Double>> A, ArrayList<Double> b)
    {

        for(int i = 0; i < n*n; i++)
        {
            systemEditTexts.get(i).setText(A.get(i/n).get(i%n).toString());
        }

        for(int i = n*n; i < n*(n+1); i++)
        {
            systemEditTexts.get(i).setText(b.get(i%n).toString());
        }
    }

}