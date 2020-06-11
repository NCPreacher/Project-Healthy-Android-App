package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

public class ChangeNotificationActivity extends AppCompatActivity
{
    String id;

    String day;
    String time;
    String text;
    boolean repetition;

    EditText change_notification_time;
    EditText change_notification_text;
    CheckBox change_notification_repeat;
    TextView change_notification_warning;

    Spinner spinner;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_notification);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DataBaseHelper(ChangeNotificationActivity.this);

        getUI();
        initSpinner();
        setValues();
    }

    public void getUI()
    {
        spinner = (Spinner) findViewById(R.id.change_notification_day_select);
        change_notification_time = findViewById(R.id.change_notification_time);
        change_notification_text = findViewById(R.id.change_notification_text);
        change_notification_repeat = findViewById(R.id.change_notification_repeat);
        change_notification_warning = findViewById(R.id.change_notification_warning);
    }

    public void setValues()
    {
        Cursor cursor = db.getNotification(Integer.parseInt(id));

        cursor.moveToFirst();

        spinner.setSelection(cursor.getInt(cursor.getColumnIndex("dzien")));
        change_notification_time.setText(cursor.getString(cursor.getColumnIndex("godzina")));
        change_notification_text.setText(cursor.getString(cursor.getColumnIndex("tresc")));
        change_notification_repeat.setChecked(cursor.getInt(cursor.getColumnIndex("powtarzaj")) > 0);

        cursor.close();
    }

    public boolean getValues()
    {
        if(!change_notification_time.getText().toString().equals("") &&
           !change_notification_text.getText().toString().equals(""))
        {
            day = spinner.getSelectedItem().toString();
            time = change_notification_time.getText().toString();
            text = change_notification_text.getText().toString();
            repetition = change_notification_repeat.isChecked();

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

    public void change(View view)
    {
        getValues();

        if(getValues() == true)
        {
            db.updateNotification(id, repetition, day, time, text);
            Intent returnedIntent = new Intent();
            setResult(RESULT_OK, returnedIntent);
            finish();
        }
        else { change_notification_warning.setVisibility(View.VISIBLE); }
    }
}
