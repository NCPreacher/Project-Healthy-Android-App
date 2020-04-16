package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void editGoal(View view)
    {
        Intent intent = new Intent(this, GoalActivity.class);
        startActivity(intent);
    }

    public void editMeals(View view)
    {
        Intent intent = new Intent(this, GoalActivity.class);
        //startActivity(intent);
    }

    public void editReminder(View view)
    {
        //Intent intent = new Intent(this, SetGoalActivity.class);
        //startActivity(intent);
    }

    public void editNotifications(View view)
    {
        //Intent intent = new Intent(this, SetGoalActivity.class);
        //startActivity(intent);
    }
}
