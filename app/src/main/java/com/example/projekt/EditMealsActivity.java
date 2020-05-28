package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class EditMealsActivity extends AppCompatActivity
{
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meals);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }
}
