package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addMeal(View view)
    {
        Intent intent = new Intent(this, MealActivity.class);
        startActivity(intent);
    }

    public void showStatistics(View view)
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void openOptions(View view)
    {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void quitApplication(View view)
    {
        finish();
        System.exit(0);
    }

}
