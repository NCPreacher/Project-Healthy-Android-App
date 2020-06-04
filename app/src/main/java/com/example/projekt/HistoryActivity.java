package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity
{
    String user;
    LinearLayout layout;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(HistoryActivity.this);

        getUI();
        readDatabase();
    }

    public void getUI()
    {
        layout = findViewById(R.id.history_layout);
    }

    public void readDatabase()
    {
        Cursor cursor = db.getHistory(user);

        int i = 0;

        while(cursor.moveToNext())
        {
            LinearLayout temp = (LinearLayout)getLayoutInflater().inflate(R.layout.history_element, (LinearLayout)findViewById(R.id.history_layout));

            LinearLayout temp_child = (LinearLayout)layout.getChildAt(i); // Element ze schematu (glowny layout schematu)

            Button temp_child_button = (Button)temp_child.getChildAt(0); // Przycisk
            temp_child_button.setId(cursor.getInt(cursor.getColumnIndex("h_id")));

            LinearLayout temp_child_layout = (LinearLayout)temp_child.getChildAt(1); // Layout

            TextView text_0 = (TextView) temp_child_layout.getChildAt(0); // Text0
            text_0.setText(cursor.getString(cursor.getColumnIndex("data")));
            TextView text_1 = (TextView) temp_child_layout.getChildAt(1); // Text1
            text_1.setText(cursor.getString(cursor.getColumnIndex("nazwa")));
            TextView text_2 = (TextView) temp_child_layout.getChildAt(2); // Text2
            text_2.setText(cursor.getString(cursor.getColumnIndex("ilosc")) + "g");

            i++;
        }
    }

    public void remove(View view)
    {
        db.deleteHistory(String.valueOf(view.getId()));
        layout.removeAllViews();
        readDatabase();
    }
}
