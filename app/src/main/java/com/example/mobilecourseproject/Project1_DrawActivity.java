package com.example.mobilecourseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Project1_DrawActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series1;
    LineGraphSeries<DataPoint> series2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project1_draw);

        Intent intent = getIntent();
        ArrayList<Double> First;
        ArrayList<Double> Second;


        First = (ArrayList<Double>) intent.getSerializableExtra(Project1_MainActivity.FIRST_LINE_COEFFICIENTS);
        Second = (ArrayList<Double>) intent.getSerializableExtra(Project1_MainActivity.SECOND_LINE_COEFFICIENTS);

        double x, yf, ys;
        x = 0;

        GraphView graph = findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();
        series2 = new LineGraphSeries<>();

        int numDataPoints = 500;
        for(int i = 0; i < numDataPoints; i++)
        {
            x = x + 0.1;
            yf = getY(x, First.get(0), First.get(1), First.get(2));
            ys = getY(x, Second.get(0), Second.get(1), Second.get(2));
            series1.appendData(new DataPoint(x, yf), false, 500);
            series2.appendData(new DataPoint(x, ys), false, 500);
        }
        graph.addSeries(series1);
        graph.addSeries(series2);
    }

    private double getY(double x, Double a1, Double a2, Double b) {
        return ((-a1/a2)*x + b/a2);
    }

    private float getX (float y, float a1, float a2, float b)
    {
        return (b/a1 - (a2/a1)*y);
    }
}