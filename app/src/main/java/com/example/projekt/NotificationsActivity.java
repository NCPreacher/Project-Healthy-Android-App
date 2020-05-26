package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NotificationsActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
    }

    public void addNotification(View view)
    {

    }

    public void editNotifications(View view)
    {
        Intent intent = new Intent(this, EditNotificationsActivity.class);
        startActivity(intent);
    }
}
