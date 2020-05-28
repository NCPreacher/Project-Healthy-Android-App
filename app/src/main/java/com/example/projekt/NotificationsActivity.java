package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Time;

public class NotificationsActivity extends AppCompatActivity
{
    String user;

    String day;
    String time;
    String text;
    boolean repetition;

    EditText notifications_time;
    EditText notifications_text;
    CheckBox notifications_repeat;
    TextView notifications_warning;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        getUI();
        initSpinner();
    }

    public void addNotification(View view)
    {
        DataBaseHelper db = new DataBaseHelper(NotificationsActivity.this);

        if(getValues() == true)
        {
            db.addNotification(user, repetition, day, time, text);
        }
        else { notifications_warning.setVisibility(View.VISIBLE); }
    }

    public void editNotifications(View view)
    {
        Intent intent = new Intent(this, EditNotificationsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void getUI()
    {
        spinner = (Spinner) findViewById(R.id.notifications_day_select);
        notifications_time = findViewById(R.id.notifications_time);
        notifications_text = findViewById(R.id.notifications_text);
        notifications_repeat = findViewById(R.id.notifications_repeat);
        notifications_warning = findViewById(R.id.notifications_warning);
    }

    public boolean getValues()
    {
        if(!notifications_time.getText().toString().equals("") &&
           !notifications_text.getText().toString().equals(""))
        {
            day = spinner.getSelectedItem().toString();
            time = notifications_time.getText().toString();
            text = notifications_text.getText().toString();
            repetition = notifications_repeat.isChecked();

            return true;
        }

        return false;
    }

    private void initSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (
                        this,
                        R.array.days_of_week,
                        R.layout.spinner
                );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}
