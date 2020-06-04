package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class GoalActivity extends AppCompatActivity
{
    String user;

    String day;
    float calories;
    float proteins;
    float carbons;
    float fats;
    int fluids;

    EditText goal_calories;
    EditText goal_proteins;
    EditText goal_carbons;
    EditText goal_fats;
    EditText goal_fluids;

    Spinner spinner;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(GoalActivity.this);

        getUI();
        initSpinner();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0)
        {
            if(resultCode == 0) { finish(); }
            if(resultCode == 1) { setValues(data.getStringExtra("id")); }
        }
    }

    public void addGoal(View view)
    {
        getValues();
        db.addGoal(user, day, calories, proteins, carbons, fats, fluids);

        editGoal(view);
    }

    public void editGoal(View view)
    {
        Intent intent = new Intent(this, EditGoalsActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, 0);
    }

    public void getUI()
    {
        spinner = (Spinner) findViewById(R.id.goal_day_select);
        goal_calories = findViewById(R.id.goal_calories);
        goal_proteins = findViewById(R.id.goal_proteins);
        goal_carbons = findViewById(R.id.goal_carbons);
        goal_fats = findViewById(R.id.goal_fats);
        goal_fluids = findViewById(R.id.goal_fluids);
    }

    public void getValues()
    {
        day = spinner.getSelectedItem().toString();

        if(!goal_calories.getText().toString().equals("")) { calories = Float.parseFloat(goal_calories.getText().toString()); }
        else { calories = 0; }
        if(!goal_proteins.getText().toString().equals("")) { proteins = Float.parseFloat(goal_proteins.getText().toString()); }
        else { proteins = 0; }
        if(!goal_carbons.getText().toString().equals("")) { carbons = Float.parseFloat(goal_carbons.getText().toString()); }
        else { carbons = 0; }
        if(!goal_fats.getText().toString().equals("")) { fats = Float.parseFloat(goal_fats.getText().toString()); }
        else { fats = 0; }
        if(!goal_fluids.getText().toString().equals("")) { fluids = Integer.parseInt(goal_fluids.getText().toString()); }
        else { fluids = 0; }
    }

    public void setValues(String id)
    {
        Cursor cursor = db.getGoal(Integer.parseInt(id));

        cursor.moveToFirst();

        spinner.setSelection(cursor.getInt(cursor.getColumnIndex("dzien")));
        goal_calories.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("kalorie"))));
        goal_proteins.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("bialko"))));
        goal_carbons.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("weglowodany"))));
        goal_fats.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex("tluszcze"))));
        goal_fluids.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("nawodnienie"))));
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
