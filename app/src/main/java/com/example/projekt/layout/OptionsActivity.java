package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projekt.R;

public class OptionsActivity extends AppCompatActivity
{
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");
    }

    public void editGoals(View view)
    {
        Intent intent = new Intent(this, GoalActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void editNotifications(View view)
    {
        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void showHistory(View view)
    {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void editPassword(View view)
    {
        Intent intent = new Intent(this, EditPasswordActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
}
