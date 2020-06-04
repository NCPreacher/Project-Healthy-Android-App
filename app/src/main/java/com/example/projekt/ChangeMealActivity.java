package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeMealActivity extends AppCompatActivity
{
    String name;

    float calories;
    float proteins;
    float carbons;
    float fats;
    int fluids;

    TextView change_meal_name;
    EditText change_meal_calories;
    EditText change_meal_proteins;
    EditText change_meal_carbons;
    EditText change_meal_fats;
    EditText change_meal_fluids;

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_meal);

        Intent intent = getIntent();
        name = intent.getStringExtra("productName");

        db = new DataBaseHelper(ChangeMealActivity.this);

        getUI();
        setValues();
    }

    public void change(View view)
    {
        getValues();

        db.updateMeal(name, calories, proteins, carbons, fats, fluids);

        Intent returnedIntent = new Intent();
        setResult(RESULT_OK, returnedIntent);

        finish();
    }

    public void getUI()
    {
        change_meal_name = findViewById(R.id.change_meal_name);
        change_meal_calories = findViewById(R.id.change_meal_calories);
        change_meal_proteins = findViewById(R.id.change_meal_proteins);
        change_meal_carbons = findViewById(R.id.change_meal_carbons);
        change_meal_fats = findViewById(R.id.change_meal_fats);
        change_meal_fluids = findViewById(R.id.change_meal_fluids);
    }

    public void setValues()
    {
        Cursor temp = db.getMeal(name);

        temp.moveToFirst();

        change_meal_name.setText(name);
        change_meal_calories.setText(String.valueOf(temp.getFloat(temp.getColumnIndex("kalorie"))));
        change_meal_proteins.setText(String.valueOf(temp.getFloat(temp.getColumnIndex("bialko"))));
        change_meal_carbons.setText(String.valueOf(temp.getFloat(temp.getColumnIndex("weglowodany"))));
        change_meal_fats.setText(String.valueOf(temp.getFloat(temp.getColumnIndex("tluszcze"))));
        change_meal_fluids.setText(String.valueOf(temp.getInt(temp.getColumnIndex("plyny"))));
    }

    public void getValues()
    {
        if(!change_meal_calories.getText().toString().equals("")) { calories = Float.parseFloat(change_meal_calories.getText().toString()); }
        else { calories = 0; }
        if(!change_meal_calories.getText().toString().equals("")) { calories = Float.parseFloat(change_meal_calories.getText().toString()); }
        else { calories = 0; }
        if(!change_meal_proteins.getText().toString().equals("")) { proteins = Float.parseFloat(change_meal_proteins.getText().toString()); }
        else { proteins = 0; }
        if(!change_meal_carbons.getText().toString().equals("")) { carbons = Float.parseFloat(change_meal_carbons.getText().toString()); }
        else { carbons = 0; }
        if(!change_meal_fats.getText().toString().equals("")) { fats = Float.parseFloat(change_meal_fats.getText().toString()); }
        else { fats = 0; }
        if(!change_meal_fluids.getText().toString().equals("")) { fluids = Integer.parseInt(change_meal_fluids.getText().toString()); }
        else { fluids = 0; }
    }
}
