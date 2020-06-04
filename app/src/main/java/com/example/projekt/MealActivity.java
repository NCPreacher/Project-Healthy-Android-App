package com.example.projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MealActivity extends AppCompatActivity
{
    String user;
    Boolean is_called_from_edit = false;

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

    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Intent intent = getIntent();
        user = intent.getStringExtra("user");

        db = new DataBaseHelper(MealActivity.this);

        getUI();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0)
        {
            if(resultCode == 0) { this.finish(); }
            if(resultCode == 1) { resetActivity(); }
        }
    }

    public void addMeal(View view)
    {
        getValues();

        if(!meal_name.getText().toString().equals(""))
        {
            name = meal_name.getText().toString();
            db.addMeal(name, calories, proteins, carbons, fats, fluids);

            if(!meal_amount.getText().toString().equals(""))
            {
                amount = Float.parseFloat(meal_amount.getText().toString());
                db.addHistory(user, name, amount);
            }

            if(is_called_from_edit == true)
            {
                Intent returnedIntent = new Intent();
                setResult(RESULT_OK, returnedIntent);
            }

            finish();
        }
        else { meal_warning.setVisibility(View.VISIBLE); }
    }

    public void editMeal(View view)
    {
        Intent intent = new Intent(this, EditMealsActivity.class);
        intent.putExtra("user", user);
        startActivityForResult(intent, 0);
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

    public void resetActivity()
    {
        if(meal_warning.getVisibility() == View.VISIBLE) { meal_warning.setVisibility(View.INVISIBLE); }

        meal_amount.setText("");
        meal_name.setText("");
        meal_calories.setText("");
        meal_proteins.setText("");
        meal_carbons.setText("");
        meal_fats.setText("");
        meal_fluids.setText("");
    }
}
