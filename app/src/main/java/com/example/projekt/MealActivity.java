package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MealActivity extends AppCompatActivity
{
    String user;

    String name;
    float amount;
    float calories;
    float proteins;
    float carbons;
    float fats;
    int fluids;

    TextView meal_warning;
    EditText meal_amount;
    EditText meal_name;
    EditText meal_calories;
    EditText meal_proteins;
    EditText meal_carbons;
    EditText meal_fats;
    EditText meal_fluids;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        getUI();
    }

    public void addMeal(View view)
    {
        DataBaseHelper db = new DataBaseHelper(MealActivity.this);

        getValues();

        if(!meal_name.getText().toString().equals(""))
        {
            name = meal_name.getText().toString();
            db.addMeal(name, calories, proteins, carbons, fats, fluids);

            if(!meal_amount.getText().toString().equals(""))
            {
                db.addHistory(user, name, amount);
            }
        }
        else { meal_warning.setVisibility(View.VISIBLE); }
    }

    public void getUI()
    {
        meal_warning = findViewById(R.id.meal_warning_text);
        meal_amount = findViewById(R.id.meal_amount);
        meal_name = findViewById(R.id.meal_name);
        meal_calories = findViewById(R.id.meal_calories);
        meal_proteins = findViewById(R.id.meal_proteins);
        meal_carbons = findViewById(R.id.meal_carbons);
        meal_fats = findViewById(R.id.meal_fats);
        meal_fluids = findViewById(R.id.meal_fluids);
    }

    public void getValues()
    {
        if(!meal_calories.getText().toString().equals("")) { calories = Float.parseFloat(meal_calories.getText().toString()); }
        else { calories = 0; }
        if(!meal_proteins.getText().toString().equals("")) { proteins = Float.parseFloat(meal_proteins.getText().toString()); }
        else { proteins = 0; }
        if(!meal_carbons.getText().toString().equals("")) { carbons = Float.parseFloat(meal_carbons.getText().toString()); }
        else { carbons = 0; }
        if(!meal_fats.getText().toString().equals("")) { fats = Float.parseFloat(meal_fats.getText().toString()); }
        else { fats = 0; }
        if(!meal_fluids.getText().toString().equals("")) { fluids = Integer.parseInt(meal_fluids.getText().toString()); }
        else { fluids = 0; }
    }
}
