package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class StatisticsActivity extends AppCompatActivity
{
    String user;

    float current_calories, goal_calories = 1;
    float current_proteins, goal_proteins = 1;
    float current_carbons, goal_carbons = 1;
    float current_fats, goal_fats = 1;
    float current_fluids, goal_fluids = 1;

    TextView calories_percentage, calories_progress;
    TextView proteins_percentage, proteins_progress;
    TextView carbons_percentage, carbons_progress;
    TextView fats_percentage, fats_progress;
    TextView fluids_progress;

    ProgressBar calories, proteins, carbons, fats, fluids;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(StatisticsActivity.this);

        getUI();
        readDatabase();
        setValues();
    }

    public void getUI()
    {
        calories_percentage = findViewById(R.id.calories_percentage);
        calories_progress = findViewById(R.id.calories_progress);
        proteins_percentage = findViewById(R.id.proteins_percentage);
        proteins_progress = findViewById(R.id.proteins_progress);
        carbons_percentage = findViewById(R.id.carbons_percentage);
        carbons_progress = findViewById(R.id.carbons_progress);
        fats_percentage = findViewById(R.id.fats_percentage);
        fats_progress = findViewById(R.id.fats_progress);
        fluids_progress = findViewById(R.id.fluids_progress);

        calories = findViewById(R.id.calories_progressBar);
        proteins = findViewById(R.id.proteins_progressBar);
        carbons = findViewById(R.id.carbons_progressBar);
        fats = findViewById(R.id.fats_progressBar);
        fluids = findViewById(R.id.fluids_progressBar);
    }

    public void setValues()
    {
        int calories_p = Math.round((current_calories/goal_calories) * 100);
        int proteins_p = Math.round((current_proteins/goal_proteins) * 100);
        int carbons_p = Math.round((current_carbons/goal_carbons) * 100);
        int fats_p = Math.round((current_fats/goal_fats) * 100);
        int fluids_p = Math.round((current_fluids/goal_fluids) * 100);


        calories_percentage.setText(calories_p + "%");
        calories_progress.setText(current_calories + "/" + goal_calories);
        calories.setProgress(calories_p);
        proteins_percentage.setText(proteins_p + "%");
        proteins_progress.setText(current_proteins + "/" + goal_proteins);
        proteins.setProgress(proteins_p);
        carbons_percentage.setText(carbons_p + "%");
        carbons_progress.setText(current_carbons + "/" + goal_carbons);
        carbons.setProgress(carbons_p);
        fats_percentage.setText(fats_p + "%");
        fats_progress.setText(current_fats + "/" + goal_fats);
        fats.setProgress(fats_p);
        fluids_progress.setText(current_fluids + "/" + goal_fluids);
        fluids.setProgress(fluids_p);
    }

    public void readDatabase()
    {
        getGoal();
        getProgress();
    }

    public void getGoal()
    {
        Cursor cursor = db.getGoal(user, db.getDayNumber());

        if(cursor.moveToFirst() == true)
        {
            goal_calories = cursor.getFloat(cursor.getColumnIndex("kalorie"));
            goal_proteins = cursor.getFloat(cursor.getColumnIndex("bialko"));
            goal_carbons = cursor.getFloat(cursor.getColumnIndex("weglowodany"));
            goal_fats = cursor.getFloat(cursor.getColumnIndex("tluszcze"));
            goal_fluids = cursor.getFloat(cursor.getColumnIndex("nawodnienie"));
        }
    }

    public void getProgress()
    {
        Cursor history = db.getHistory(user, db.getDateTime());

        if(history.moveToFirst() == true)
        {
            do
            {
                Cursor meal = db.getMeal(history.getString(history.getColumnIndex("nazwa")));
                float amount = history.getFloat(history.getColumnIndex("ilosc")) / 100;

                if(meal.moveToFirst() == true)
                {
                    current_calories += meal.getFloat(meal.getColumnIndex("kalorie")) * amount;
                    current_proteins += meal.getFloat(meal.getColumnIndex("bialko")) * amount;
                    current_carbons += meal.getFloat(meal.getColumnIndex("weglowodany")) * amount;
                    current_fats += meal.getFloat(meal.getColumnIndex("tluszcze")) * amount;
                    current_fluids += meal.getFloat(meal.getColumnIndex("plyny")) * amount;
                }

                meal.close();
            } while(history.moveToNext() == true);
        }

        history.close();
    }
}
