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

public class NotificationsActivity extends AppCompatActivity
{
    String user;

    String day;
    String time;
    String text;
    boolean repetition;

    EditText notification_time;
    EditText notification_text;
    CheckBox notification_repeat;
    TextView notification_warning;

    Spinner spinner;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(NotificationsActivity.this);

        getUI();
        initSpinner();
    }

    public void addNotification(View view)
    {
        if(getValues() == true)
        {
            db.addNotification(user, repetition, day, time, text);
            editNotification(view);
            finish();
        }
        else { notification_warning.setVisibility(View.VISIBLE); }
    }

    public void editNotification(View view)
    {
        Intent intent = new Intent(this, EditNotificationsActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    public void getUI()
    {
        spinner = (Spinner) findViewById(R.id.notification_day_select);
        notification_time = findViewById(R.id.notification_time);
        notification_text = findViewById(R.id.notification_text);
        notification_repeat = findViewById(R.id.notification_repeat);
        notification_warning = findViewById(R.id.notification_warning);
    }

    public boolean getValues()
    {
        if(!notification_time.getText().toString().equals("") &&
           !notification_text.getText().toString().equals(""))
        {
            day = spinner.getSelectedItem().toString();
            text = notification_text.getText().toString();
            repetition = notification_repeat.isChecked();

            time = notification_time.getText().toString();

            int char0 = time.charAt(0) - '0';
            int char1 = time.charAt(1) - '0';
            char char2 = time.charAt(2);
            int char3 = time.charAt(3) - '0';
            int char4 = time.charAt(4) - '0';

            if(char2 != ':') { return false; } //00000
            if(char0 > 2) { return false; } // 30:00
            if(char0 == 2 && char1 > 4) { return false; } // 25:00
            if(char3 > 5) { return false; } // 00:69

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
