package com.example.projekt.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt.DataBaseHelper;
import com.example.projekt.R;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity
{
    String user;

    String name;
    float amount = 0;
    float calories = 0;
    float proteins = 0;
    float carbons = 0;
    float fats = 0;
    int fluids = 0;

    AutoCompleteTextView meal_name;
    ArrayList<String> meals = new ArrayList<>();

    TextView meal_warning;
    EditText meal_amount;
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
        getMeals();
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
            try { db.addMeal(name, calories, proteins, carbons, fats, fluids); }
            catch (Exception e) {}

            if(!meal_amount.getText().toString().equals("") &&
               Float.valueOf(meal_amount.getText().toString()) != 0.0)
            {
                amount = Float.parseFloat(meal_amount.getText().toString());
                db.addHistory(user, name, amount);
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

        meal_name.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String selected_meal[] = meal_name.getText().toString().split("  ");
                meal_name.setText(selected_meal[1].trim());
                setValues(selected_meal[1].trim());
            }
        });
    }

    public void setValues(String name)
    {
        Cursor cursor = db.getMeal(name);

        cursor.moveToFirst();

        meal_name.setText(name);
        meal_calories.setText(Float.toString(cursor.getFloat(cursor.getColumnIndex("kalorie"))));
        meal_proteins.setText(Float.toString(cursor.getFloat(cursor.getColumnIndex("bialko"))));
        meal_carbons.setText(Float.toString(cursor.getFloat(cursor.getColumnIndex("weglowodany"))));
        meal_fats.setText(Float.toString(cursor.getFloat(cursor.getColumnIndex("tluszcze"))));
        meal_fluids.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("plyny"))));
    }

    public void getValues()
    {
        if(!meal_calories.getText().toString().equals("")) { calories = Float.parseFloat(meal_calories.getText().toString()); }
        if(!meal_proteins.getText().toString().equals("")) { proteins = Float.parseFloat(meal_proteins.getText().toString()); }
        if(!meal_carbons.getText().toString().equals("")) { carbons = Float.parseFloat(meal_carbons.getText().toString()); }
        if(!meal_fats.getText().toString().equals("")) { fats = Float.parseFloat(meal_fats.getText().toString()); }
        if(!meal_fluids.getText().toString().equals("")) { fluids = Integer.parseInt(meal_fluids.getText().toString()); }
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

    public void getMeals()
    {
        Cursor cursor = db.getMeal();

        while(cursor.moveToNext())
        {
            meals.add(
                    "  " +
                    cursor.getString(cursor.getColumnIndex("nazwa")) + "\n  K:" +
                    cursor.getFloat(cursor.getColumnIndex("kalorie")) + "  B:" +
                    cursor.getFloat(cursor.getColumnIndex("bialko")) + "  W:" +
                    cursor.getFloat(cursor.getColumnIndex("weglowodany")) + "  T:" +
                    cursor.getFloat(cursor.getColumnIndex("tluszcze")) + "  N:" +
                    cursor.getInt(cursor.getColumnIndex("plyny"))
            );
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MealActivity.this, R.layout.search, meals);
        meal_name.setAdapter(arrayAdapter);
    }
}
