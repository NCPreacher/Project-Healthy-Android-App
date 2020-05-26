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

    public void editGoals(View view)
    {
        Intent intent = new Intent(this, EditGoalsActivity.class);
        startActivity(intent);
    }

    public void editMeals(View view)
    {
        Intent intent = new Intent(this, EditMealsActivity.class);
        startActivity(intent);
    }

    public void editNotifications(View view)
    {
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }

    public void showHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
