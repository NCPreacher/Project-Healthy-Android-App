package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class MenuActivity extends AppCompatActivity
{
    String user;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(MenuActivity.this);
        db.startService(MenuActivity.this, user);
    }

    public void addMeal(View view)
    {
        Intent intent = new Intent(this, MealActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void showStatistics(View view)
    {
        Intent intent = new Intent(this, StatisticsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void openOptions(View view)
    {
        Intent intent = new Intent(this, OptionsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
